<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*, payment.PaymentView" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결제 내역</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css">

</head>
<body>
<div class="container">
  <jsp:include page="sidebar.jsp" />
  <div class="main">
    <div class="section-title">결제 내역</div>
    <%
      List<PaymentView> payments = (List<PaymentView>) request.getAttribute("payments");
    %>
    <% if (payments != null && !payments.isEmpty()) { %>
      <table class="res-table">
        <tr>
          <th>운행번호</th><th>열차번호</th><th>출발 → 도착</th><th>시간</th><th>요금</th>
        </tr>
        <% for (PaymentView p : payments) { %>
          <tr>
            <td><%=p.getDriveId()%></td>
            <td><%=p.getTrainNo()%></td>
            <td><%=p.getDeptStationName()%> → <%=p.getArriStationName()%></td>
            <td><%=p.getDeptTime()%> ~ <%=p.getArriTime()%></td>
            <td><%=p.getPrice()%>원</td>
          </tr>
        <% } %>
      </table>
    <% } else { %>
      <p class="no-data">결제 내역이 없습니다.</p>
    <% } %>
  </div>
</div>
</body>
</html>
