<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="customer.Customer" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>운행 정보 조회</title>

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
    
    h1 {
    	margin-top: 0.5rem;
		margin-bottom: 0.5rem;
    }

	h2 {
		margin-top: 1rem;
		margin-bottom: 1rem;
	}

	/* 열차 조회 리스트 */
	.train-row {
		border : 1px solid #ccc;
		margin-bottom : 10px;
		padding : 15px;
		display : flex;
		cursor : pointer;
		transition: background-color 0.1s ease;
	}
	
	/* 더 보기 버튼 */
	.moreBtn {
		border : 1px solid #ccc;
		background-color : white;
		border-radius : 1rem;
		width : 8rem;
		font-size : 1rem;
		font-family: 'Pretendard', 'Malgun Gothic', sans-serif;
	}
	
	
	/* 마우스 오버 시 스타일 */
	.train-row:hover {
	    background-color: #f7f7f7;
	}
	/* 선택된 행 스타일 */
	.train-row.selected {
	    background-color: #e6f7ff; /* 연한 파랑색 배경 */
	    border: 2px solid #007bff; /* 파란색 테두리 */
	    padding: 14px; /* 테두리 두께로 인한 패딩 조정 */
	}
	
	/* 하단 고정 푸터 스타일 */
	.fixed-footer {
	    position: fixed;
	    bottom: 0;
	    left: 0;
	    width: 100%;
	    background-color: #e6f7ff; /* 어두운 배경 */
	    color: white;
	    padding: 15px 0;
	    text-align: center;
	    /* box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.2); */
	    display: none; /* 초기에는 숨김 */
	    z-index: 100;
	}
	.footer-buttons button {
	    padding: 10px 20px;
	    margin: 0 10px;
	    font-size: 16px;
	    cursor: pointer;
	    border: none;
	    border-radius: 5px;
	}
	.footer-buttons #selectSeatBtn {
	    background-color: #007bff; /* 좌석선택 버튼 색상 */
	    color: white;
	}
	.footer-buttons #reserveBtn {
	    background-color: #28a745; /* 예매 버튼 색상 */
	    color: white;
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
                <a href="#">마이페이지</a> <%-- 마이페이지 @websevlet 값 넣으시면 돼욧 --%>
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

	<h1 style="text-align: center;">승차권 예매</h1>

    <h2 style="text-align: center;">${dept} → ${arri} 운행 정보</h2>
    
    <div class="train-list-container">
        <%-- 초기 10개(또는 조회된 개수) 데이터 출력 (JSTL은 그대로 유지) --%>
        <c:forEach var="drive" items="${list}">
            <div class="train-row" style="border: 1px solid #ccc; margin-bottom: 10px; padding: 15px; display: flex;"
            	data-drive-id="<c:out value='${drive.driveId}'/>"
            	data-price="<c:out value='${drive.price}'/>"
		        data-train-no="<c:out value='${drive.trainNo}'/>"
		        data-dept-station="<c:out value='${drive.deptStation}'/>"
		        data-arri-station="<c:out value='${drive.arriStation}'/>"
		        data-formatted-dept-time="<c:out value='${drive.formattedDeptTime}'/>"
		        data-formatted-arri-time="<c:out value='${drive.formattedArriTime}'/>"
            	onclick="handleRowClick(this)"
            >
                <div class="col-train-info" style="width: 15%; text-align: center;">
    				<img src="${pageContext.request.contextPath}/images/ktx.png" 
         				alt="KTX 로고" style="height: 20px;">
    				<p style="font-weight: bold;">${drive.trainNo}</p>
				</div>

                <div class="col-route-time" style="width: 50%;">
                    <span style="font-size: 1.1em; font-weight: bold;">
                        ${drive.deptStation} → ${drive.arriStation}
                        (<span style="color: #007bff;">${drive.formattedDeptTime} ~ ${drive.formattedArriTime}</span>)
                    </span>
                    <span style="color: gray; margin-left: 10px;">소요시간: ${drive.durationStr}</span>
                </div>

                <div class="col-price" style="width: 35%; text-align: right;">
                    <div style="font-weight: bold; font-size: 1.2em; color: #d9534f;">
                        <fmt:formatNumber value="${drive.price}" pattern="#,###원"/>
                    </div>
                    <div style="color: green; font-size: 0.9em;">5%적립</div>
                    <div style="font-size: 0.9em; color: gray;">일반실</div>
                </div>

            </div>
        </c:forEach>
    </div>
    
    <div id="loadingSpinner" style="display: none; text-align: center; margin: 20px; font-weight: bold;">
        데이터를 로딩 중입니다...
    </div>

    <div style="text-align: center; margin-top: 20px;">
        <button id="moreBtn" class="moreBtn" onclick="loadMore()" 
            style="padding: 10px 20px; cursor: pointer;">
            더 보기
        </button>
    </div>
    
    <!-- 하단 고정 버튼 영역 -->
    <div id="fixedFooter" class="fixed-footer">
    	<div class="footer-buttons">
    		<button id="selectSeatBtn" onclick="selectSeat()">좌석 선택</button>
    		<button id="reserveBtn" onclick="reserve()">예매</button>
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
    
    
<script>
    // 현재 로드된 데이터 개수를 저장하는 변수 (더보기))
    let currentOffset = ${list.size()};
    const pageSize = ${pageSize}; // 서블릿에서 설정한 10개
    
    //
	const contextPath = '${pageContext.request.contextPath}';
    
    
    // 현재 선택된 운행정보의 ID를 저장하는 변수 (운행편 클릭 이벤트)
    let selectedDriveId = null;
    let fixedFooter = null;
    
    console.log("Fixed Footer Element:", fixedFooter);
  
    // 페이지 로드 후, 초기 데이터가 pageSize 미만이면 '더 보기' 버튼 숨김 (더보기)
    document.addEventListener('DOMContentLoaded', function() {
    	
    	fixedFooter = document.getElementById('fixedFooter');
    	console.log("Fixed Footer Element (inside DOMContentLoaded) :", fixedFooter);
    	
        if (currentOffset < pageSize) {
            const moreBtn = document.getElementById('moreBtn');
            if (moreBtn) moreBtn.style.display = 'none';
        }
        
    });
    
    // 클릭 이벤트 핸들러 행을 선택/선택 해제하고 스타일을 변경 (운행편 클릭 이벤트)
    
    // 선택된 열차의 모든 정보를 저장할 객체 변수 선언
    let selectedDriveInfo = null;
    
    function handleRowClick(clickedElement) {
    	const driveId = Number(clickedElement.getAttribute('data-drive-id'));
    	
    	// 1. 이전에 선택된 행의 스타일을 초기화
    	const previouslySelected = document.querySelector('.train-row.selected');
    	if (previouslySelected) {
    		previouslySelected.classList.remove('selected');
    	}
    	
    	// 2. 선택/선택 해제 로직
    	if (selectedDriveId === driveId) {
    		// 이미 선택된 행을 다시 클릭하게 되면 선택 해제
    		selectedDriveId = null;
    		selectedDriveInfo = null;	// 정보 객체 초기화
    		if(fixedFooter) fixedFooter.style.display = 'none'; // 푸터를 숨김
    	} else {
    		// 새로운 행을 클릭하면 선택이 됨
    		selectedDriveId = driveId;
    		clickedElement.classList.add('selected'); // 새 행에 스타일 추가
    		if(fixedFooter) fixedFooter.style.display = 'block'; // 푸터가 표시됨
    		
    		// 선택된 열차의 모든 데이터 수집 및 저장
    		selectedDriveInfo = {
    			driveId: driveId,
    	        price: Number(clickedElement.getAttribute('data-price')),
    	        trainNo: clickedElement.getAttribute('data-train-no'),
    	        deptStation: clickedElement.getAttribute('data-dept-station'),
    	        arriStation: clickedElement.getAttribute('data-arri-station'),
    	        formattedDeptTime: clickedElement.getAttribute('data-formatted-dept-time'),
    	        formattedArriTime: clickedElement.getAttribute('data-formatted-arri-time')	
    		};
    }
    	
    	console.log("선택된 운행 정보 객체:", selectedDriveInfo);
    	console.log("선택된 운행 아이디:", selectedDriveId);
    }
    
    
    // 모든 train-rwo에 handleRowClick 이벤트를 연결
    function applyClickEvents() {
    	// 더 보기 버튼으로 추기되거나 초기에 로드된 모든 행을 선택
    	const rows = document.querySelectorAll('.train-list-container .train-row');
    	rows.forEach(row => {
    		// 이벤트가 연결이 되어있는지 확인하여 중복 연결을 방지
    		if (!row.getAttribute('onclick')) {
    			
    		}
    	})
    }

    // '더 보기' 버튼 클릭 이벤트 처리
    function loadMore() {
        // 1. 필요한 데이터 수집
        const deptName = '${dept}';
        const arriName = '${arri}';
        const timeFilter = '${timeFilter}'; 
        
        const moreBtn = document.getElementById('moreBtn');
        const spinner = document.getElementById('loadingSpinner');
        
        // 로딩 중 표시 및 버튼 비활성화
        if(spinner) spinner.style.display = 'block';
        if(moreBtn) moreBtn.disabled = true;

        // 2. Ajax 요청
        fetch('DriveInfoList?action=more&deptName=' + deptName + 
              '&arriName=' + arriName + 
              '&startTime=' + timeFilter + 
              '&offset=' + currentOffset, {
            method: 'GET'
        })
        .then(response => response.json())
        .then(data => {
            // 3. 응답 데이터 처리
            const listContainer = document.querySelector('.train-list-container');
            
            data.forEach(drive => {
                const newRow = createDriveInfoRow(drive);
                listContainer.appendChild(newRow);
            });
            
            // 4. offset 업데이트
            currentOffset += data.length;

            // 5. '더 보기' 버튼 숨김 처리
            if (data.length < pageSize) {
                if(moreBtn) moreBtn.style.display = 'none';
            }
            
        })
        .catch(error => {
            console.error('Error loading more data:', error);
            alert('데이터 로딩에 실패했습니다.');
        })
        .finally(() => {
            // 로딩 스피너 숨김 및 버튼 활성화
            if(spinner) spinner.style.display = 'none';
            if(moreBtn) moreBtn.disabled = false;
        });
    }

    // JSON 객체 하나를 받아 HTML 문자열을 반환하는 함수 (자바스크립트 기반)
    function createDriveInfoRow(drive) {
        const row = document.createElement('div');
        row.className = 'train-row';
        // 데이터 속성 및 클릭 이벤트를 추가 (운행편 클릭)
        row.setAttribute('data-drive-id', drive.driveId);
        row.setAttribute('data-price', drive.price); 
        row.setAttribute('data-train-no', drive.trainNo); 
        row.setAttribute('data-dept-station', drive.deptStation); 
        row.setAttribute('data-arri-station', drive.arriStation); 
        row.setAttribute('data-formatted-dept-time', drive.formattedDeptTime); 
        row.setAttribute('data-formatted-arri-time', drive.formattedArriTime); 
        row.setAttribute('onclick' , 'handleRowClick(this)');
        row.style = "border: 1px solid #ccc; margin-bottom: 10px; padding: 15px; display: flex;";

        // 가격을 한국 통화 형식으로 포맷하는 함수
        const formatPrice = (price) => {
            return new Intl.NumberFormat('ko-KR', {
                style: 'currency',
                currency: 'KRW',
                minimumFractionDigits: 0
            }).format(price);
        };
        
        // 템플릿 리터럴 내 EL 충돌 방지 (\${...} 사용)
        
        const imageFileName = 'ktx.png';
        
        row.innerHTML = `
            <div class="col-train-info" style="width: 15%; text-align: center;">
                <img src="\${contextPath}/images/\${imageFileName}" 
                     alt="\${drive.trainType} 로고" style="height: 20px;">
                <p style="font-weight: bold;">\${drive.trainNo}</p>
            </div>

            <div class="col-route-time" style="width: 50%;">
                <span style="font-size: 1.1em; font-weight: bold;">
                    \${drive.deptStation} → \${drive.arriStation}
                    (<span style="color: #007bff;">\${drive.formattedDeptTime} ~ \${drive.formattedArriTime}</span>)
                </span>
                <span style="color: gray; margin-left: 10px;">소요시간: \${drive.durationStr}</span>
            </div>

            <div class="col-price" style="width: 35%; text-align: right;">
                <div style="font-weight: bold; font-size: 1.2em; color: #d9534f;">
                    \${formatPrice(drive.price)} 
                </div>
                <div style="color: green; font-size: 0.9em;">5%적립</div>
                <div style="font-size: 0.9em; color: gray;">일반실</div>
            </div>
        `;
        return row;
    }
    
    // 좌석선택 버튼 클릭 시 동작 (예시)
    function selectSeat() {
    	if (!selectedDriveInfo) {
    		alert('열차를 선택해주세요.');
    		return;
    	}
    	
    	const form = document.createElement('form');
    	form.method = 'POST';
    	// 좌석선택 서블릿의 url을 입력하기
    	form.action = 'SetSelectedTicket';
    	
    	// 수집된 selectedDriveInfo 객체의 모든 속성을 hidden input으로 추가
        for (const key in selectedDriveInfo) {
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = key; // 서블릿에서 req.getParameter(key)로 받을 이름
            input.value = selectedDriveInfo[key];
            form.appendChild(input);
        }
        
        document.body.appendChild(form);
        form.submit(); // 서블릿으로 데이터 전송 시작
    }
    
    // 예매 버튼 클릭 시 동작 (예시)
    function reserve() {
    	if (selectedDriveId) {
    		alert(selectedDriveId + '번 열차를 예매합니다.');
    	} else {
    		alert('열차를 선택해주세요.');
    	}
    }    
</script>
	
</body>
</html>
