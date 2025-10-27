<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="customer.UserDTO" %>
 
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>코레일 홈</title>
</head>
<body>

<%   
 UserDTO user = (UserDTO) session.getAttribute("cust");
	%>



<% if( user != null) {  %>

<h2>안녕하세요,
 <%=user.getName() %>님 반갑습니다👋</h2>
<p>즐거운 여행 되세요🚆 </p>
<a href="logout">로그아웃</a>

<%} %>

 코레일 메인

</body>
</html>