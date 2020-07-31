<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="cpath">${pageContext.request.contextPath }</c:set>
<c:set var="emailCookie">${cookie.emailCookie.value }</c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JOIN</title>
</head>
<body>
<h1><a href="${cpath }/">PROJECT</a></h1>	<%-- home화면으로 이동 --%>
<hr>

<form id="loginForm" method="POST">
	<c:choose>
		<c:when test="${empty emailCookie }"><input id="email" name="email" placeholder="Email"></c:when>
		<c:when test="${not empty emailCookie }"><input id="email" name="email" placeholder="Email" value="${emailCookie }"></c:when>
	</c:choose>
	<input type="password" id="password" name="password" placeholder="Password">
	<label><input type="checkbox" id="rememberEmail" name="rememberEmail">이메일 기억하기</label>
	<input type="submit" value="login">
</form>
	<p><a href="${cpath }/helpEmail/">이메일 찾기</a></p>
	<p><a href="${cpath }/helpPw/">비밀번호 찾기</a></p>

</body>
</html>