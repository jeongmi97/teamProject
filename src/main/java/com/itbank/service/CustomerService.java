package com.itbank.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.itbank.dao.CustomerDAO;
import com.itbank.vo.CustomerVO;

@Service
public class CustomerService {

	@Autowired private CustomerDAO dao;
	@Autowired ServletContext sc;
	
	// 회원가입 시 중복 이메일 체크
	public String emailcheck(HttpServletRequest request) {
		try {
			String email = request.getParameter("email");
			String alreadyExist = dao.emailcheck(email);	// 이메일값으로 select하여 반환되는 이메일 있는지 확인
//			System.out.println("email : " + dao.emailcheck(email));
			return alreadyExist != null ? "이미 사용중인 계정입니다" : "사용 가능한 계정입니다"; 
		}catch (Exception e){
			return "통신 실패 : " + e.getClass().getSimpleName();
		}
	}
	
	// 로그인 
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException {
		ModelAndView mav = new ModelAndView();
		CustomerVO vo = new CustomerVO();
		vo.setEmail(request.getParameter("email"));
		vo.setPassword(SecurePassword.getHashValue(vo.getEmail(), request.getParameter("password")));	// 입력받은 비밀번호 암호화 값으로 변환하여 비교
		System.out.println("login hashPassword : " + vo.getPassword());
		
		CustomerVO login = dao.login(vo);
		HttpSession session = request.getSession();
		
		Cookie emailCookie = new Cookie("emailCookie",vo.getEmail());
		if (login != null) {
			session.setAttribute("login", login);
			mav.setViewName("home");				// 로그인 성공 시 login세션 생성하고 home화면으로 이동
			if("on".equals(request.getParameter("rememberEmail"))) {	// 이메일 기억하기 체크 되어있을 때 이메일 쿠키 생성
//				System.out.println("체크 o : " + request.getParameter("rememberEmail"));
				emailCookie.setMaxAge(60 * 60 * 24 * 7);		// cookie 유효시간 7일
				emailCookie.setPath("/");		//				// 모든 경로에서 쿠키 유효
			}else {
				System.out.println("체크 x : " + request.getParameter("rememberEmail")); // 이메일 기억하기 체크 해제 되어있을 때 
				emailCookie = new Cookie("emailCookie", null);					// 이메일 쿠키가 이미 생성 되어있을 경우 쿠키 삭제
				emailCookie.setMaxAge(0); 										// 쿠키 유호시간 0으로 세팅
				emailCookie.setPath("/");
			}
			response.addCookie(emailCookie);
		} else { mav.setViewName("loginError"); }	//로그인 실패 시 loginError페이지 이동
	
		return mav;
	}
	
	// join 페이지로 이동 시 이용약관 terms.txt 불러오기
	public ModelAndView join() throws IOException {
		ModelAndView mav = new ModelAndView("join");
		
		System.out.println(sc.getRealPath("/resources/txt/terms.txt"));		// cpath + /resources/txt/terms.txt
		StringBuffer str = new StringBuffer();
		File file = new File(sc.getRealPath("/resources/txt/terms.txt"));
		Scanner scanner = new Scanner(file);
		
		while(scanner.hasNext()) {
			str.append(scanner.nextLine());
		}
		
		mav.addObject("str", str);
		return mav;
	}
	
	// 회원가입
	public ModelAndView join(HttpServletRequest request,CustomerVO vo) throws NoSuchAlgorithmException {
		ModelAndView mav = new ModelAndView("home");	// 회원가입 완료 시 home으로 이동
		System.out.println(request.getParameter("postcode"));
//		2020.07.31 폰번호, 주소 입력받아서 db에 넣는 형식 수정함
		String phone = request.getParameter("firstP") + request.getParameter("midP") + request.getParameter("lastP");
		String address = "(" + request.getParameter("postcode") + ")" + request.getParameter("address");
		String registdate = request.getParameter("yy") + request.getParameter("mm") + request.getParameter("dd");
		vo.setPhone(phone); vo.setAddress(address); vo.setDetailaddress(request.getParameter("detailAddress")); vo.setRegistdate(registdate);
		vo.setPassword(SecurePassword.getHashValue(vo.getEmail(), vo.getPassword()));	// 입력받은 비밀번호 해쉬 암호화
//		System.out.println("hashPassword : " + vo.getPassword());
		
		if(dao.insertCustomer(vo) != 1)	mav.setViewName("redirect:/");	// 회원가입 실패 시 리다이렉트
		
		return mav;
	}
	
	// 이메일 찾기
	public ModelAndView helpEmail(CustomerVO vo, HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();
		if(dao.searchEmail(vo) != null){				// 이름과 전화번호로 이메일 찾아서 있으면
			mav.addObject("vo", dao.searchEmail(vo));	// 찾은 vo값 mav에 vo로 넣기
			mav.setViewName("confirmEmail");			
		}else {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('회원정보를 확인해주세요'); history.go(-1);</script>");		// 입력값이 일치하지 않으면 알림창 출력 후 뒤로가기
			out.flush();
		}
		return mav;
	}
	
	// 비밀번호 찾기 이용 시 임시비밀번호 발송하기
	public ModelAndView helpPw(CustomerVO vo, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String email = dao.searchPw(vo);			// 사용자 입력값으로 이메일 select 하기
		if(email != null) {							// 이메일 값 나오면 
			sendEmail(email);						// 사용자 email로 임시비밀번호 담긴 이메일 보내기
			mav.setViewName("redirect:/login/");	// login 페이지로 돌아가기
		}else {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('회원 정보를 확인해주세요'); history.go(-1);</script>");		// 입력값이 일치하지 않으면 알림창 출력 후 뒤로가기
			out.flush();
		}
		return mav;
	}
	
	
	// 임시비밀번호 메일로 발송
	public void sendEmail(String email) throws AddressException, NoSuchAlgorithmException {
		String ranPw = RandomStringUtils.random(10,33,122,false,false);		// 랜덤 비밀번호 생성
		System.out.println("=============ranPw : " + ranPw);
		String password = SecurePassword.getHashValue(email, ranPw);		// 랜덤 비밀번호 암호화
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", email); map.put("password", password);
		dao.updatePw(map);				// 임시비밀번호 값으로 수정
		
		String host = "smtp.gmail.com";
		String sender = "thisshoes4";	// 보내는 이메일 계정
		String spw = "@123thisshoes";
		int port = 465;
		
		String subject = "이런신발 임시비밀번호 발송";	// 메일 제목
		String body = email + "님 임시비밀번호 : \n" + ranPw;	// 메일 내용
		
		Properties props = new Properties(); // 정보 담기 위한 객체 생성
		// smtp 서버 정보 설정
		props.put("mail.smtp.host", host); 
		props.put("mail.smtp.port", port); 
		props.put("mail.smtp.auth", "true"); 
		props.put("mail.smtp.ssl.enable", "true"); 
		props.put("mail.smtp.ssl.trust", host);

		Session session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sender, spw);
			}
		});
		
		try {
		Message mimeMessage = new MimeMessage(session);		
		mimeMessage.setFrom(new InternetAddress(sender));	// 발신자 셋팅
		mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email)); // 수신자 셋팅
		
		mimeMessage.setSubject(subject);	// 제목셋팅
		mimeMessage.setText(body);		// 내용셋팅		
		Transport.send(mimeMessage); 	// javax.mail.Transport.send() 이용, 메일 보내기
		}catch (MessagingException e){
			e.printStackTrace();
		}
	}

//	2020.07.31 회원정보 수정
	public ModelAndView mychange(HttpSession session) {
		ModelAndView mav = new ModelAndView("mychange");
		CustomerVO vo = (CustomerVO) session.getAttribute("login");
//		01011112222
		mav.addObject("firstP", vo.getPhone().substring(0,3));
		mav.addObject("midP", vo.getPhone().substring(3,7));
		mav.addObject("lastP", vo.getPhone().substring(7));
//		(48009)
		mav.addObject("postcode", vo.getAddress().substring(1,6));
		mav.addObject("address", vo.getAddress().substring(7));
		return mav;
	}

	public ModelAndView mychange(HttpServletRequest request, CustomerVO vo) {
		ModelAndView mav = new ModelAndView();
		vo.setPassword(request.getParameter("oldPassword"));
		vo.setEmail(request.getParameter("email"));
		if(vo.getPassword() == null || dao.checkPassword(vo) != null) {
			
		}
		return null;
	}
}








