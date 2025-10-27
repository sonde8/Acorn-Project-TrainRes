<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*, customer.Customer, reservation.Reservation" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css">


</head>
<body>
<div class="container">
  <jsp:include page="sidebar.jsp" />

  <div class="main">
    <div class="section-title">나의 기본정보</div>
    <%
      Customer c = (Customer) request.getAttribute("customer");
      List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
    %>
    <table class="info-table">
      <tr><th>아이디</th><td><%= c.getCustId() %></td></tr>
      <tr><th>이름</th><td><%= c.getName() %></td></tr>
      <tr><th>생년월일</th><td><%= c.getBirth() %></td></tr>
    </table>

    <div class="section-title">예약 내역</div>
    <table class="res-table">
      <tr>
        <th>예약번호</th><th>열차번호</th><th>출발역</th><th>도착역</th>
        <th>출발시각</th><th>도착시각</th><th>예약일시</th>
      </tr>
      <% if (reservations != null && !reservations.isEmpty()) {
           for (Reservation r : reservations) { %>
             <tr>
               <td><%= r.getResId() %></td>
               <td><%= r.getTrainNo() %></td>
               <td><%= r.getDeptStation() %></td>
               <td><%= r.getArriStation() %></td>
               <td><%= r.getDeptTime() %></td>
               <td><%= r.getArriTime() %></td>
               <td><%= r.getResDate() %></td>
             </tr>
      <% } } else { %>
        <tr><td colspan="7" class="no-data">예약 내역이 없습니다.</td></tr>
      <% } %>
    </table>
  </div>
</div>
</body>
</html>
