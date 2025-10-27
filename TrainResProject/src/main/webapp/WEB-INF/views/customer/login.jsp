<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>코레일 로그인</title>
</head>
<body>
	<h2>🚆 코레일 로그인 🚆</h2>
	
	<form action="${pageContext.request.contextPath}/login" method="post">
		<label>ID: <input type="text" name="id" required></label><br>
		<label>PW: <input type="password" name="pw" required></label><br>
		<button type="submit">로그인</button>
	</form>
	
	<hr> <a href="join"> 회원가입하기</a>
	
	<p style="color:red;">
		${error}
	</p>
	

</body>
</html>