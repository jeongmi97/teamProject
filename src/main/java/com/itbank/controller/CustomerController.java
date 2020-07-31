package com.itbank.controller;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.itbank.service.CustomerService;
import com.itbank.vo.CustomerVO;

					// @responseBody : 반환하는 값이 그대로 응답으로 전달되는 것
@RestController		// @ResponseBody를 기본적으로 처리하는 컨트롤러
public class CustomerController {
	
	@Autowired private CustomerService cs;
	
	@RequestMapping(value="checkEmail/", produces="application/text;charset=utf8")
	public String idcheck(HttpServletRequest request) {
		return cs.emailcheck(request);
	}
	
	@PostMapping(value="login/")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException {
		return cs.login(request, response);
	}
	
	@RequestMapping("logout/")
	public ModelAndView logout(HttpSession session) {
		ModelAndView mav = new ModelAndView("home");
//		session.invalidate(); 	// invalidate : session안에 저장 된 모든 값 제거
		session.removeAttribute("login");
		return mav;
	}
	
	@RequestMapping("join/")	// join 페이지로 이동할 때
	public ModelAndView join() throws IOException {
		return cs.join();
	}
	
	@PostMapping(value="join/")
	public ModelAndView join(HttpServletRequest request,CustomerVO vo) throws NoSuchAlgorithmException {
		return cs.join(request,vo);
	}
	
	@PostMapping(value="helpEmail/")
	public ModelAndView helpEmail(CustomerVO vo, HttpServletResponse response) throws IOException {
		return cs.helpEmail(vo, response);
	}
	
	@PostMapping(value="helpPw/")
	public ModelAndView helpPw(CustomerVO vo, HttpServletResponse response) throws Exception {
		return cs.helpPw(vo, response);
	}
	
//	2020.07.31
	@RequestMapping("mychange/")
	public ModelAndView mychange(HttpSession session) {
		return cs.mychange(session);
	}
	
	@PostMapping("mychange/")
	public ModelAndView mychange(HttpServletRequest request, HttpServletResponse response, CustomerVO vo) throws NoSuchAlgorithmException, IOException {
		return cs.mychange(request, response, vo);
	}
}
