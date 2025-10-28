<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
    <h2>📝 코레일 회원가입 📝</h2>

    <form action="join" method="post">
        <label>아이디 (CUST_ID): <input type="text" name="cust_id" required></label><br>
        <label>이름 (NAME): <input type="text" name="name" required></label><br>
        <label>비밀번호 (PASSWORD): <input type="password" name="password" required></label><br>
        <label>생년월일 (BIRTH): <input type="date" name="birth" required></label><br>

        <button type="submit">가입하기</button>
    </form>

    <p style="color:red;">
        ${error}
    </p>
    <hr>
    <a href="login">로그인 페이지로 돌아가기</a>
</body>
</html>
