<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="reservation.Reservation" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>승차권 상세보기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css">

</head>
<body>
<div class="container">
  <jsp:include page="sidebar.jsp" />
  <div class="main">
    <div class="section-title">승차권 상세정보</div>
    <%
      Reservation res = (Reservation) request.getAttribute("reservation");
    %>
    <table class="info-table">
      <tr><th>예약번호</th><td><%= res.getResId() %></td></tr>
      <tr><th>열차번호</th><td><%= res.getTrainNo() %></td></tr>
      <tr><th>출발역</th><td><%= res.getDeptStation() %></td></tr>
      <tr><th>도착역</th><td><%= res.getArriStation() %></td></tr>
      <tr><th>출발시각</th><td><%= res.getDeptTime() %></td></tr>
      <tr><th>도착시각</th><td><%= res.getArriTime() %></td></tr>
      <tr><th>예약일시</th><td><%= res.getResDate() %></td></tr>
    </table>
    <button class="btn-primary" onclick="history.back()">뒤로가기</button>
  </div>
</div>
</body>
</html>
