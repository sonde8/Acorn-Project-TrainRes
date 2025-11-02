<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="customer.UserDTO"%>
<!DOCTYPE html>

<html lang="ko">

<head>

<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>코레일 로그인</title>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">

<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

body {
	font-family: 'Pretendard', 'Malgun Gothic', sans-serif;
	background: #f5f5f5;
	color: #333;
}

a {
	text-decoration: none;
	color: inherit;
}

/* 상단 네비 */
.top-nav {
	background: #f8f8f8;
	border-bottom: 1px solid #ddd;
	padding: .5rem 0;
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
	font-size: .9rem;
}

.nav-button {
	padding: .5rem 1rem;
	background: #fff;
	border: 1px solid #ddd;
	cursor: pointer;
	transition: .3s;
}

/* 헤더 */
.header {
	background: linear-gradient(135deg, #1a237e 0%, #283593 100%);
	color: #fff;
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
	font-weight: 700;
	display: flex;
	align-items: center;
	gap: .5rem;
}

.logo img {
	height: 80px;
}

.main-menu {
	display: flex;
	list-style: none;
	gap: 2rem;
}

.main-menu li {
	padding: .5rem 1rem;
	font-size: 1.1rem;
	font-weight: 700;
	cursor: pointer;
	transition: .3s;
	border-bottom: 3px solid transparent;
}

.main-menu li:hover {
	border-bottom-color: #fff;
}

.menu-icon {
	font-size: 1.5rem;
	cursor: pointer;
	color: #fff;
}

/* 본문 */
.container {
	max-width: 1400px;
	margin: 2rem auto;
	padding: 0 2%;
	display: flex;
	gap: 2rem;
}

.main {
	flex: 1;
	background: #fff;
	border: 1px solid #e5e5e5;
	border-radius: 8px;
	padding: 24px;
}

.section-title {
	font-size: 1.25rem;
	font-weight: 700;
	margin-bottom: 16px;
}

.info-table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 8px;
}

.info-table th, .info-table td {
	border: 1px solid #e5e5e5;
	padding: 12px;
	text-align: left;
	vertical-align: middle;
}

.info-table th {
	width: 180px;
	background: #f7f9fc;
	color: #333;
}

.btn-primary {
	display: inline-block;
	margin-top: 16px;
	padding: .6rem 1rem;
	border: none;
	border-radius: 4px;
	background: #1a43a0;
	color: #fff;
	font-weight: 700;
	cursor: pointer;
}

/* 푸터 */
.footer {
	background: #2c3e50;
	color: #fff;
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
	color: #fff;
	font-weight: 700;
}

.footer-info {
	font-size: .9rem;
	line-height: 1.6;
	color: #bdc3c7;
}

/* 반응형 */
@media ( max-width :1200px) {
	.container {
		flex-direction: column;
	}
}

.tabs {
	width: 80%;
	background-color: #fff;
	border: 1px solid #e0e0e0;
	height: 30px;
	display: flex;
	margin: 0 auto;
}

.tabs>div {
	background-color: #fff;
	border: 1px solid #252323;
	width: 25%;
}

.login-container {
	margin: 0 auto;
	margin-top: 100px;
}

.login-bar {
	height: 100px;
	background-color: #00458C;;
	color: #ffffff;
	padding: 10px;
	text-align: center;
	line-height: 100px;
	text-shadow: 0px 2px 4px rgba(0, 0, 0, 0.5), /* 아래로 약간의 검은색 그림자 */

             0px 0px 2px rgba(0, 0, 0, 0.3);
	font-size: 30px;
	font-weight: bold;
}
</style>

</head>

<body>


	<%
     UserDTO user = (UserDTO) session.getAttribute("cust");
%>

	<!-- 상단 네비 -->
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
            %>
				<span style="font-weight: bold; color: #333;"><%= user.getName() %>님,
					환영합니다!</span> <a href="logout">로그아웃</a> <a href="mypage">마이페이지</a>
				<%
                } else {
            %>
				<a href="login">로그인</a> <a href="join">회원가입</a>
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
			<a href="${pageContext.request.contextPath}/DriveInfoList"
				class="logo-link">
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



	<div class="login-container">
		<div class="login-form-area">
			<h3>
				코레일멤버십<br>회원번호로 로그인하세요.
			</h3>

			<form class="login-form" action="<%=request.getContextPath()%>/login"
				method="post">
				<div class="input-group">
					<input type="text" name="id" placeholder="회원번호를 입력하세요"
						class="input-field" required>
				</div>

				<div class="input-group password-group">
					<input type="password" name="pw" placeholder="비밀번호를 입력하세요"
						class="input-field" required> <span class="mouse-input">🖱️
						마우스 입력</span>
				</div>
				<div class="options">
					<label class="checkbox-container"> <input type="checkbox">
						회원번호 저장
					</label> <span class="login-info">로그인 5회 실패 시 로그인이 제한될 수 있습니다.</span>
				</div>
				<button type="submit" class="login-button">로그인</button>
			</form>
			<div class="footer-links">
				<a href="#">회원번호 찾기</a> <span>|</span> <a href="#">비밀번호 찾기</a> <span>|</span>
				<a href="#">회원가입</a>
			</div>
		</div>
		<div class="decoration-icon"></div>
	</div>
</body>
</html>