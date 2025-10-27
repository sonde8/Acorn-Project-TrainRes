<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>기차여행</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css">

</head>
<body>
<div class="container">
  <jsp:include page="sidebar.jsp" />
  <div class="main">
    <div class="section-title">기차여행 안내</div>
    <p>기차를 타고 떠나는 다양한 여행 정보를 제공합니다.</p>
    <a href="${pageContext.request.contextPath}/driveinfo/list" class="btn-primary">운행 정보 보기</a>
  </div>
</div>
</body>
</html>
