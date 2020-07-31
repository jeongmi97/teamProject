<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="cpath">${pageContext.request.contextPath }</c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>이메일 확인</h1>
	${vo.name }님의 이메일은 ${vo.email } 입니다<br>
	<a href="${cpath }/helpPw/">비밀번호 찾기</a>
	<a href="${cpath }/login/">로그인</a>
	<a href="${cpath }/">홈으로</a>
</body>
</html>