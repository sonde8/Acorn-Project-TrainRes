<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>결제 완료</title>
<style>
 body { font-family: Arial, sans-serif; margin: 32px; }
 pre  { background:#f5f5f7; padding:14px; border-radius:8px; }
 a.btn { display:inline-block; margin-top:16px; padding:10px 14px; background:#0f69ff; color:#fff; border-radius:8px; text-decoration:none; }
</style>
</head>
<body>
  <h2>결제가 완료되었습니다 🎉</h2>
  <pre>${info}</pre>
  <a class="btn" href="${pageContext.request.contextPath}/">메인으로</a>
</body>
</html>
