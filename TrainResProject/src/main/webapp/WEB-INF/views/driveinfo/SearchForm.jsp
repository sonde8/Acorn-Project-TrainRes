<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="customer.Customer" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>에이콘레일 열차 조회</title>

<link rel="stylesheet" as="style" crossorigin 
      href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.min.css" />

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

    /* 메인 컨텐츠 영역 */
    .main-content {
        position: relative;
        width: 100%;
        height: 70vh;
        min-height: 500px;
        background: 
                    url('https://info.korail.com/DATA/bbs/1017/20250613095401946_ysNc.jpg');
        background-size: cover;
        background-position: center;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .content-wrapper {
        max-width: 1400px;
        width: 100%;
        display: flex;
        gap: 3%;
        padding: 0 2%;
        align-items: center;
    }

    /* 배너 영역 */
    .banner-section {
        flex: 1;
        background: white;
        border-radius: 20px;
        overflow: hidden;
    }

    .banner-section img {
        width: 100%;
        height: auto;
        display: block;
    }

    /* 검색 폼 영역 */
    .search-section {
        width: 35%;
        min-width: 350px;
        background: white;
        border-radius: 20px;
        padding: 2rem;
    }

    .search-title {
        font-size: 1.5rem;
        font-weight: bold;
        color: #1a237e;
        margin-bottom: 1.5rem;
        text-align: center;
    }

    .form-group {
        margin-bottom: 1.5rem;
    }

    .form-group label {
        display: block;
        font-size: 1rem;
        font-weight: bold;
        color: #333;
        margin-bottom: 0.5rem;
    }

    .form-control {
        width: 100%;
        padding: 0.8rem;
        font-size: 1rem;
        border: 2px solid #ddd;
        border-radius: 8px;
        transition: all 0.3s;
        background-color: white;
    }

    .form-control:focus {
        outline: none;
        border-color: #007bff;
    }

    .submit-btn {
        width: 100%;
        padding: 1rem;
        font-size: 1.2rem;
        font-weight: bold;
        color: white;
        background: linear-gradient(135deg, #007bff 0%, #0056b3 100%);
        border: none;
        border-radius: 50px;
        cursor: pointer;
        transition: all 0.3s;
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

<%
Customer user = (Customer) session.getAttribute("cust");
%>


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
            <div class="logo">
            <img src="${pageContext.request.contextPath}/images/logo2.png" 
         				alt="KTX 로고" style="height: 80px;">
            </div>
            <ul class="main-menu">
                <li>승차권</li>
                <li>철도역·열차</li>
                <li>고객서비스</li>
                <li>코레일멤버십</li>
            </ul>
            <div class="menu-icon">☰</div>
        </div>
    </div>

    <!-- 메인 컨텐츠 -->
    <div class="main-content">
        <div class="content-wrapper">
            <!-- 배너 영역 -->
            <div class="banner-section">
                <img src="https://www.korail.com/file/cubedata/COMMON/popup/f20250701O4xS.jpg" alt="KTX 삼성카드">
            </div>

            <!-- 검색 폼 -->
            <div class="search-section">
                <h2 class="search-title">열차 조회</h2>
                <form action="DriveInfoList" method="GET">
                    <!-- 출발역 -->
                    <div class="form-group">
                        <label for="departure-station">출발역</label>
                        <select id="departure-station" name="deptName" class="form-control">
                            <option value="광주송정" selected>광주송정</option>
                            <option value="용산">용산</option>
                            <option value="서울" >서울</option>
                            <option value="부산">부산</option>
                            <option value="대전">대전</option>
                        </select>
                    </div>

                    <!-- 도착역 -->
                    <div class="form-group">
                        <label for="arrival-station">도착역</label>
                        <select id="arrival-station" name="arriName" class="form-control">
                            <option value="광주송정">광주송정</option>
                            <option value="용산" selected>용산</option>
                            <option value="서울">서울</option>
                            <option value="부산">부산</option>
                            <option value="대전">대전</option>
                        </select>
                    </div>

                    <!-- 출발 시간 -->
                    <div class="form-group">
                        <label for="departure-time">출발 시간</label>
                        <select id="departure-time" name="startTime" class="form-control">
                            <option value="00" selected>00시</option>
                            <option value="01">01시</option>
                            <option value="02">02시</option>
                            <option value="03">03시</option>
                            <option value="04">04시</option>
                            <option value="05">05시</option>
                            <option value="06">06시</option>
                            <option value="07">07시</option>
                            <option value="08">08시</option>
                            <option value="09">09시</option>
                            <option value="10">10시</option>
                            <option value="11">11시</option>
                            <option value="12">12시</option>
                            <option value="13">13시</option>
                            <option value="14">14시</option>
                            <option value="15">15시</option>
                            <option value="16">16시</option>
                            <option value="17">17시</option>
                            <option value="18">18시</option>
                            <option value="19">19시</option>
                            <option value="20">20시</option>
                            <option value="21">21시</option>
                            <option value="22">22시</option>
                            <option value="23">23시</option>
                        </select>
                    </div>

                    <!-- 조회 버튼 -->
                    <button type="submit" class="submit-btn">
                        열차 조회하기
                    </button>
                </form>
            </div>
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