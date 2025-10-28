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

    <form action="login" method="post">
        <label>ID: <input type="text" name="id" required></label><br>
        <label>PW: <input type="password" name="pw" required></label><br>
        <button type="submit">로그인</button>
    </form>

    <hr> <a href="join"> 회원가입하기</a>

    <p style="color:red;">
        ${error}
    </p>

    <%
     String msg = request.getParameter("msg");
        if ("joined".equals(msg)) {
    %>
        <p style="color:green;">회원가입이 완료되었습니다. 로그인 해주세요!</p>
    <% } %>

</body>
</html>
