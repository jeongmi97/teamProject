<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="cpath">${pageContext.request.contextPath }</c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HOME</title>
</head>
<script>
	function logout(){
		var con = confirm('로그아웃 하시겠습니까?');
		if(con == true){
			location.href = '${cpath}/logout/';
		}
	}
</script>
<body>
<p>테스트 김정미 때지하뚜 어허 커밋좀</p>
<h1><a href="${cpath }/">HOME</a></h1>
<hr>
<c:if test="${not empty login }">	<%-- login 세션 있을 경우(로그인 되어있는 경우) 로그인 중인 유저 이름 나오게 하기 --%>
	<p>${login.name }님 로그인중</p>
	<a href="" onclick="logout()">로그아웃</a>
	<%-- 2020.07.31 내정보 수정 (mychange.jsp 작성)--%>
	<a href="${cpath }/mychange/">내정보</a>		
</c:if>
<ul>
	<c:if test="${empty login }">	<%-- login 세션 없을 경우(로그인 안돼있는 경우) 회원가입, 로그인 링크 나오게 하기 --%>
		<li><a href="${cpath }/join/">회원가입</a></li>	
		<li><a href="${cpath }/login/">로그인</a></li>
	</c:if>
</ul>
</body>
</html>