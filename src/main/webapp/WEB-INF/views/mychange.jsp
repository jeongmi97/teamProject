<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cpath">${pageContext.request.contextPath }</c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<head>
<%-- 	<jsp:include page="/WEB-INF/views/include/header.jsp" flush="false" /> --%>

<!-- 2020.07.31 joinFuction.js 파일 수정 확인해주세용 -->
<script type="text/javascript"> cpath = '${cpath}'; </script>
<script type="text/javascript" src="${cpath }/js/joinFunction.js"></script>
</head>

<body>
	<header class="site-navbar" role="banner">
			<div class="bg-light py-3">
				<div class="container">
					<div class="row">
						<div class="col-md-12 mb-0">
							<a href="${cpath }">Home</a><span class="mx-2 mb-0">/</span><strong	class="text-black">MyChange</strong>
						</div>
					</div>
				</div>
			</div>
		</header>
	
		<!-- 개인정보 변경 입력란 -->
		<!-- 2020.07.22 -->
		<!-- 2020.07.31 : 아이디부분 joinForm 과 일치화시킴 -->
		<div class="site-section">
			<div class="container">
			
			<div class="row mb-12">
				<!-- 12칸 중에서 9칸 차지 -->
				<div class="col-md-9 order-2">
					<div class="row ml-2">

						<form id="mychangeForm" method="post" class="col-md-12">

							<div class="p-3 p-lg-5 border">
								<div class="form-group row">
									<!-- 이름 -->
									<div class="col-md-12">
										<label for="name" class="text-black">이름</label>
										<input type="text" class="form-control" id="name" name="name" value="${login.name }">
									</div>
								</div>
								<!-- 이메일 -->
								<div class="form-group row">
									<div class="col-md-12">
										<label for="email" class="text-black">Email</label>
										<input type="email" class="form-control" id="email" name="email" value="${login.email }" readonly>
									</div>
								</div>
								<!-- 현재 비밀번호 -->
								<div class="form-group row">
									<div class="col-md-12">
										<label for="Password" class="text-black">현재 비밀번호<span class="text-danger">*</span></label>
										<input type="password" class="form-control" id="oldPassword" name="oldPassword" placeholder="비밀번호를 입력하세요" required>
									</div>
								</div>
								<!-- 새로운 비밀번호 -->
								<div class="form-group row">
									<div class="col-md-12">
										<label for="Password" class="text-black">새 비밀번호<span class="text-danger"></span></label>
										<input type="password" class="form-control" id="Password" name="Password" placeholder="비밀번호를 입력하세요" required>
										<p id="pwmsg"></p>
									</div>
								</div>
								<!-- 비밀번호 확인 -->
								<div class="form-group row">
									<div class="col-md-12">
										<label for="c_subject" class="text-black">새 비밀번호 확인<span class="text-danger"></span></label>
										<input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="비밀번호를 다시 입력하세요" required>
										<p id="cpwmsg"></p>
									</div>
								</div>
								<!-- 휴대폰 번호 -->
								<div class="form-group row">
									<div class="col-md-12">
									<label for="phonenum" class="text-black" style="display: block;">휴대폰 번호</label>
                    				<input type="number" class="col-md-3 form-control" style="display: inline-block;" id="firstP" name="firstP" value="${ firstP }"><output class="ml-2">ㅡ</output>                  
                    				<input type="number" class="col-md-3 ml-2 form-control" style="display: inline-block;" id="midP" name="midP" value="${ midP }"><output  class="ml-2">ㅡ</output>                  
                    				<input type="number" class="col-md-3 ml-2 form-control" style="display: inline-block;" id="lastP" name="lastP" value="${ lastP }">
									</div>
								</div>
								<!-- 주소 -->
								<!-- 나중에 도로명주소 API 가져오자 -->								
								<div class="form-group row">
									<div class="col-md-12">
			               			<label for="address" class="text-black" style="display: block;">주소 입력</label>
            			   			<input type="text" class="col-md-6 form-control" style="display: inline-block;" id="postcode" name="postcode" placeholder="우편번호" value="${postcode }">
               						<input type="button" class="col-md-3 ml-3 btn btn-primary btn-lg" value="검색" style="display: inline-block;" onclick="joinPostcode()">
               						<input type="text" class="col-md-12 mt-3 form-control" id="address" name="address" placeholder="주소" value="${address }">
               						<input type="text" class="col-md-12 mt-3 form-control" id="detailAddress" name="detailAddress" placeholder="상세주소" value="${login.detailaddress }">
									</div>
								</div>
								
								<div class="form-group row">
									<div class="col-lg-12">
										<input type="submit" class="btn btn-primary btn-lg btn-block"
											value="회원정보 수정">
									</div>
								</div>
							</div>
						</form>

					</div>
				</div>
			
			
				
				<!-- 왼쪽 사이드바 1 -->
					<!-- 2020.07.20 -->
					<div class="col-md-3 order-1 mb-5 mb-md-0">
						<div class="border p-4 rounded mb-4">
							<h3 class="mb-3 h6 text-uppercase text-black d-block">Categories</h3>
							<ul class="list-unstyled mb-0">
								<li class="mb-1">
									<a href="${cpath }/mypage" class="d-flex">
										<span>마이 페이지</span>
									</a>
								</li>
								<li class="mb-1">
									<a href="${cpath }/mychange" class="d-flex">
										<span>개인정보 변경</span>
									</a>
								</li>
								<li class="mb-1">
									<a href="#" class="d-flex">
										<span>장바구니</span>
									</a>
								</li>
								<li class="mb-1">
									<a href="#" class="d-flex">
										<span>구매내역</span>
									</a>
								</li>
								
							</ul>
						</div>
						
					
					</div>			
			</div>
			</div>	
		</div>
    
<%--     <jsp:include page="/WEB-INF/views/include/footer.jsp" flush="false" /> --%>
	
<!-- 	2020.07.31 api, input 클릭 js 파일 추가 -->
	<script type="text/javascript" src="${cpath }/js/mychangeEvent.js"></script>
	<script type="text/javascript" src="${cpath }/js/joinPostcode.js"></script>	
	
	<script src="../js/jquery-3.3.1.min.js"></script>
	<script src="../js/jquery-ui.js"></script>
	<script src="../js/popper.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/owl.carousel.min.js"></script>
	<script src="../js/jquery.magnific-popup.min.js"></script>
	<script src="../js/aos.js"></script>
	<script src="../js/main.js"></script>

</body>
</html>