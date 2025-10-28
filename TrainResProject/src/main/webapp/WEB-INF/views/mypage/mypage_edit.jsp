<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="customer.Customer" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css">
</head>
<body>
<div class="container">
  <jsp:include page="sidebar.jsp" />
  <div class="main">
    <div class="section-title">회원정보 수정</div>
    <%
      Customer cust = (Customer) request.getAttribute("customer");
      String msg = (String) request.getAttribute("message");
      if (msg != null) {
    %>
        <p style="margin:0 0 10px;color:#0078d4;"><%= msg %></p>
    <% } %>
    <form action="${pageContext.request.contextPath}/mypage/edit/save" method="post">
      <table class="info-table">
        <tr><th>아이디</th><td><%= cust.getCustId() %></td></tr>
        <tr><th>이름</th><td><input type="text" name="name" value="<%= cust.getName() %>"></td></tr>
        <tr><th>비밀번호</th><td><input type="password" name="password" placeholder="변경 시에만 입력"></td></tr>
      </table>
      <button type="submit" class="btn-primary">수정하기</button>
    </form>
  </div>
</div>
</body>
</html>
