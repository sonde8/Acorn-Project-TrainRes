<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="customer.Customer" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 수정</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css">

<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    body {
        font-family: 'Pretendard', 'Malgun Gothic', sans-serif;
        background-color: #f5f5f5;
    }

    a {
        text-decoration: none;
        color: inherit;
    }

    /* 상단 네비게이션 */
    .top-nav {
        background-color: #f8f8f8;
        border-bottom: 1px solid #ddd;
        padding: 0.5rem 0;
    }

    .top-nav-container {
        max-width: 1400px;
        margin: 0 auto;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0 2%;
    }

    .top-nav-left {
        display: flex;
        gap: 1rem;
    }

    .top-nav-right {
        display: flex;
        gap: 1rem;
        font-size: 0.9rem;
    }

    .nav-button {
        padding: 0.5rem 1rem;
        background-color: white;
        border: 1px solid #ddd;
        cursor: pointer;
        transition: all 0.3s;
    }

    /* 헤더 */
    .header {
        background: linear-gradient(135deg, #1a237e 0%, #283593 100%);
        color: white;
        padding: 1rem 0;
        
    }

    .header-container {
        max-width: 1400px;
        margin: 0 auto;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0 2%;
    }

    .logo {
        font-size: 1.8rem;
        font-weight: bold;
        display: flex;
        align-items: center;
        gap: 0.5rem;
    }

    .logo img {
        height: 2.5rem;
    }

    .main-menu {
        display: flex;
        list-style: none;
        gap: 2rem;
    }

    .main-menu li {
        padding: 0.5rem 1rem;
        font-size: 1.1rem;
        font-weight: bold;
        cursor: pointer;
        transition: all 0.3s;
        border-bottom: 3px solid transparent;
    }

    .main-menu li:hover {
        border-bottom-color: white;
    }

    .menu-icon {
        font-size: 1.5rem;
        cursor: pointer;
    }

    
    /* 푸터 */
    .footer {
        background-color: #2c3e50;
        color: white;
        padding: 2rem 0;
        margin-top: 3rem;
    }

    .footer-container {
        max-width: 1400px;
        margin: 0 auto;
        padding: 0 2%;
    }

    .footer-links {
        display: flex;
        gap: 2rem;
        list-style: none;
        margin-bottom: 1.5rem;
        flex-wrap: wrap;
    }

    .footer-links a {
        color: white;
        font-weight: bold;
        transition: color 0.3s;
    }

    .footer-info {
        font-size: 0.9rem;
        line-height: 1.6;
        color: #bdc3c7;
    }

    /* 반응형 디자인 */
    @media (max-width: 1200px) {
        .content-wrapper {
            flex-direction: column;
            padding: 2rem;
        }

        .banner-section {
            width: 100%;
            max-width: 600px;
        }

        .search-section {
            width: 100%;
            max-width: 500px;
            margin-top: 2rem;
        }
    }

    }
</style>

</head>
<body>

<% Customer user = (Customer) session.getAttribute("cust"); %>
<!-- 상단 네비게이션 -->
    <div class="top-nav">
        <div class="top-nav-container">
            <div class="top-nav-left">
                <button class="nav-button">한국철도</button>
                <button class="nav-button">승차권예매</button>
                <button class="nav-button">기차여행</button>
            </div>
            <div class="top-nav-right">
                <%
    			if (user != null) {
        		// 로그인 상태: 사용자 이름과 로그아웃 링크 표시
			%>
                <span style="font-weight: bold; color: #333;"><%= user.getName() %>님, 환영합니다!</span>
                <a href="logout">로그아웃</a> <%-- 로그아웃 서블릿으로 연결 --%>
                <a href="${pageContext.request.contextPath}/mypage">마이페이지</a> <%-- 마이페이지 @websevlet 값 넣으시면 돼욧 --%>
			<%
    			} else {
        		// 로그아웃 상태: 로그인 및 회원가입 링크 표시
			%>
                <a href="login">로그인</a>
                <a href="join">회원가입</a>
			<%
    			}
			%>
                <a href="#">고객센터</a>
            </div>
        </div>
    </div>

    <!-- 헤더 -->
    <div class="header">
        <div class="header-container">
            <a href="${pageContext.request.contextPath}/DriveInfoList" class="logo-link">
        		<div class="logo">
            		<img src="${pageContext.request.contextPath}/images/logo2.png" 
         			alt="KTX 로고" style="height: 80px;">
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

<div class="container">
  <jsp:include page="sidebar.jsp" />
  <div class="main">
    <div class="section-title">회원정보 수정</div>
    <%
      Customer cust = (Customer) request.getAttribute("customer");
    %>
    <form action="${pageContext.request.contextPath}/mypage/edit/save" method="post">

      <table class="info-table">
        <tr><th>아이디</th><td><%= cust.getCustId() %></td></tr>
        <tr><th>이름</th><td><input type="text" name="name" value="<%= cust.getName() %>"></td></tr>
        <tr><th>비밀번호</th><td><input type="password" name="password"></td></tr>
      </table>
      <button type="submit" class="btn-primary">수정하기</button>
    </form>
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
                <p style="margin-top: 1rem; font-size: 0.8rem;">Copyright © Acorn Railroad Corporation. All rights reserved.</p>
            </div>
        </div>
    </div>

</body>
</html>
