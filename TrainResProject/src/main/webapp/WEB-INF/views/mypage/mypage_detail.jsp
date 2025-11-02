<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<%
    // 가능하면 스크립틀릿 없이 EL/JSTL만 쓰는 것을 권장
%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>승차권 상세보기</title>

<link rel="stylesheet" href="${ctx}/css/mypage.css" />

<style>
    * { margin:0; padding:0; box-sizing:border-box; }
    body {
        font-family: 'Pretendard','Malgun Gothic',sans-serif;
        background:#f5f5f5;
        color:#333;
    }
    a { text-decoration:none; color:inherit; }

    /* 상단 네비 */
    .top-nav { background:#f8f8f8; border-bottom:1px solid #ddd; padding:.5rem 0; }
    .top-nav-container {
        max-width:1400px; margin:0 auto; display:flex; justify-content:space-between; align-items:center; padding:0 2%;
    }
    .top-nav-left { display:flex; gap:1rem; }
    .top-nav-right { display:flex; gap:1rem; font-size:.9rem; }
    .nav-button {
        padding:.5rem 1rem; background:#fff; border:1px solid #ddd; cursor:pointer; transition:.3s;
    }

    /* 헤더 */
    .header {
        background:linear-gradient(135deg,#1a237e 0%,#283593 100%); color:#fff; padding:1rem 0;
    }
    .header-container {
        max-width:1400px; margin:0 auto; display:flex; justify-content:space-between; align-items:center; padding:0 2%;
    }
    .logo { font-size:1.8rem; font-weight:700; display:flex; align-items:center; gap:.5rem; }
    .logo img { height:80px; }
    .main-menu { display:flex; list-style:none; gap:2rem; }
    .main-menu li {
        padding:.5rem 1rem; font-size:1.1rem; font-weight:700; cursor:pointer; transition:.3s; border-bottom:3px solid transparent;
    }
    .main-menu li:hover { border-bottom-color:#fff; }
    .menu-icon { font-size:1.5rem; cursor:pointer; color:#fff; }

    /* 본문 */
    .container { max-width:1400px; margin:2rem auto; padding:0 2%; display:flex; gap:2rem; }
    .main { flex:1; background:#fff; border:1px solid #e5e5e5; border-radius:8px; padding:24px; }
    .section-title { font-size:1.25rem; font-weight:700; margin-bottom:16px; }
    .info-table { width:100%; border-collapse:collapse; margin-top:8px; }
    .info-table th, .info-table td {
        border:1px solid #e5e5e5; padding:12px; text-align:left; vertical-align:middle;
    }
    .info-table th { width:180px; background:#f7f9fc; color:#333; }

    .btn-primary {
        display:inline-block; margin-top:16px; padding:.6rem 1rem; border:none; border-radius:4px;
        background:#1a43a0; color:#fff; font-weight:700; cursor:pointer;
    }

    /* 푸터 */
    .footer { background:#2c3e50; color:#fff; padding:2rem 0; margin-top:3rem; }
    .footer-container { max-width:1400px; margin:0 auto; padding:0 2%; }
    .footer-links { display:flex; gap:2rem; list-style:none; margin-bottom:1.5rem; flex-wrap:wrap; }
    .footer-links a { color:#fff; font-weight:700; }
    .footer-info { font-size:.9rem; line-height:1.6; color:#bdc3c7; }

    /* 반응형 */
    @media (max-width:1200px) {
        .container { flex-direction:column; }
    }
</style>
</head>
<body>

<!-- 상단 바 -->
<div class="top-nav">
  <div class="top-nav-container">
    <div class="top-nav-left">
      <a class="nav-button" href="#">한국철도</a>
      <a class="nav-button" href="#">승차권예매</a>
      <a class="nav-button" href="#">기차여행</a>
    </div>
    <div class="top-nav-right">
      <c:choose>
        <c:when test="${not empty sessionScope.cust}">
          <span style="font-weight:700;color:#333;">
            ${sessionScope.cust.name}님, 환영합니다!
          </span>
          <a href="${ctx}/logout">로그아웃</a>
          <a href="${ctx}/mypage">마이페이지</a>
        </c:when>
        <c:otherwise>
          <a href="${ctx}/login">로그인</a>
          <a href="${ctx}/join">회원가입</a>
        </c:otherwise>
      </c:choose>
      <a href="#">고객센터</a>
    </div>
  </div>
</div>

<!-- 헤더 -->
<div class="header">
  <div class="header-container">
    <a href="${ctx}/DriveInfoList" class="logo-link">
      <div class="logo">
        <img src="${ctx}/images/logo2.png" alt="KTX 로고" />
      </div>
    </a>
    <ul class="main-menu">
      <li>승차권</li>
      <li>철도역·열차</li>
      <li>고객서비스</li>
      <li>코레일멤버십</li>
    </ul>
    <div class="menu-icon">☰</div>
  </div>
</div>

<!-- 본문 -->
<div class="container">
  <!-- 사이드바 include (기존 파일 유지) -->
  <jsp:include page="sidebar.jsp" />

  <div class="main">
    <div class="section-title">승차권 상세정보</div>

    <!-- 널 가드 -->
    <c:if test="${empty reservation}">
      <p style="color:#c00; margin:12px 0;">
        상세 정보를 불러올 수 없습니다. 목록에서 다시 선택해 주세요.
      </p>
      <button class="btn-primary" onclick="history.back()">뒤로가기</button>
    </c:if>

    <c:if test="${not empty reservation}">
      <table class="info-table">
        <tr>
          <th>예약번호</th>
          <td>${reservation.resId}</td>
        </tr>
        <tr>
          <th>열차번호</th>
          <td>${reservation.trainNo}</td>
        </tr>
        <tr>
          <th>출발역</th>
          <td>${reservation.deptStation}</td>
        </tr>
        <tr>
          <th>도착역</th>
          <td>${reservation.arriStation}</td>
        </tr>
        <tr>
          <th>출발시각</th>
          <td>
            <c:choose>
              <c:when test="${not empty reservation.deptTime}">
                <fmt:formatDate value="${reservation.deptTime}" pattern="yyyy-MM-dd HH:mm" />
              </c:when>
              <c:otherwise>-</c:otherwise>
            </c:choose>
          </td>
        </tr>
        <tr>
          <th>도착시각</th>
          <td>
            <c:choose>
              <c:when test="${not empty reservation.arriTime}">
                <fmt:formatDate value="${reservation.arriTime}" pattern="yyyy-MM-dd HH:mm" />
              </c:when>
              <c:otherwise>-</c:otherwise>
            </c:choose>
          </td>
        </tr>
        <tr>
          <th>예약일시</th>
          <td>
            <c:choose>
              <c:when test="${not empty reservation.resDate}">
                <fmt:formatDate value="${reservation.resDate}" pattern="yyyy-MM-dd HH:mm" />
              </c:when>
              <c:otherwise>-</c:otherwise>
            </c:choose>
          </td>
        </tr>
        <tr>
          <th>요금</th>
          <td>
            <c:choose>
              <c:when test="${not empty reservation.amount}">
                <fmt:formatNumber value="${reservation.amount}" type="number" />
                원
              </c:when>
              <c:otherwise>-</c:otherwise>
            </c:choose>
          </td>
        </tr>
      </table>

      <button class="btn-primary" onclick="history.back()">뒤로가기</button>
    </c:if>
  </div>
</div>

<!-- 푸터 -->
<div class="footer">
  <div class="footer-container">
    <ul class="footer-links">
      <li><a href="#">이용약관</a></li>
      <li><a href="#">개인정보처리방침</a></li>
      <li><a href="#">여객운송약관</a></li>
      <li><a href="#">고객센터</a></li>
    </ul>
    <div class="footer-info">
      <p><strong>상호:</strong> 에이콘철도공사 | <strong>사업자등록:</strong> 314-82-10024 | <strong>통신판매업신고:</strong> 서울 마포구-0433호</p>
      <p><strong>주소:</strong> 04038 서울 마포구 양화로 122 4층</p>
      <p><strong>대표전화:</strong> 02-2231-6412</p>
      <p style="margin-top:1rem; font-size:.8rem;">Copyright © Acorn Railroad Corporation. All rights reserved.</p>
    </div>
  </div>
</div>

</body>
</html>
