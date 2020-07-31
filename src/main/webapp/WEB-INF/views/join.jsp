<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="cpath">${pageContext.request.contextPath }</c:set>
<%@ page import="java.io.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
input.reborder { border: 1px solid red }		/* 입력조건에 따라 테두리 색 바꾸는 클래스  / submit 할 시 사용됨 */
pre{
 	overflow: scroll; 
 	white-space: pre-wrap; 
	width: 700px;
	height: 200px;
	border: 1px solid;
}
article.on{ display: block; }
article{ display: none; }
</style>
</head>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<body>
<h1>JOIN</h1>
<hr>
<!-- js파일에 들어갈 cpath경로 선언 -->
<script type="text/javascript"> cpath = '${cpath}'; </script>
<script type="text/javascript" src="${cpath }/js/joinFunction.js"></script>

<!-- 이용약관 표시 -->
<article class="on" id="terms">${str }</article>	

<article id="joinform">
<form id="joinForm" method="POST">
	<p><input type="email" id="email" name="email" placeholder="ex@naver.com" required></p>
	<p id="idmsg"></p>
	<p><input id="Password" type="Password" name="Password" placeholder="대소문자, 특수문자, 숫자 포함 6자리 이상" required></p>
	<p id="pwmsg"></p>
	<p><input id="confirmPassword" type="password" name="confirmPassword" placeholder="비밀번호 확인" required></p>
	<p id="cpwmsg"></p>
	<p><input id="name" name="name" placeholder="Name" required></p>
	생년월일<br>
	<input type="number" id="yy" name="yy">년<input type="number" id="mm" name="mm">월<input type="number" id="dd" name="dd">일<br>
	<p id="yymsg"></p>
	휴대폰<br>
	<input type="number" id="firstP" name="firstP">-<input type="number" id="midP" name="midP">-<input type="number" id="lastP" name="lastP"><br>
	<input type="text" id="postcode" name="postcode" placeholder="우편번호">
	<input type="button" onclick="joinPostcode()" value="우편번호"><br>
	<input type="text" id="address" name="address" placeholder="주소"><br>
	<input type="text" id="detailAddress" name="detailAddress" placeholder="상세주소">
	<p><input type="button" onclick="join()" value="join"></p>
</form>
</article>

<!-- 주소 api & input 클릭 이벤트  -->
<script type="text/javascript" src="${cpath }/js/joinPostcode.js"></script>		 
<script type="text/javascript" src="${cpath }/js/joinEvent.js"></script>
</body>
</html>