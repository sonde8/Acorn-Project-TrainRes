<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="customer.Customer" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>승차권 예매</title>

<link rel="stylesheet" as="style" crossorigin 
      href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.min.css" />

<style>
/* ... (스타일 그대로, 생략 없이 네 코드 그대로 두면 됨) ... */
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
    .logo img { height: 2.5rem; }
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
    .main-menu li:hover { border-bottom-color: white; }
    .menu-icon {
        font-size: 1.5rem;
        cursor: pointer;
    }
    h1 {
        margin-top: 0.5rem;
        margin-bottom: 0.5rem;
        text-align: center;
    }
    h2 {
        margin-top: 1rem;
        margin-bottom: 1rem;
        text-align: center;
    }
    .train-row {
        border: 1px solid #ccc;
        margin: 10px auto;
        padding: 15px;
        display: flex;
        width: 90%;
        max-width: 1200px;
        cursor: pointer;
        transition: background-color 0.1s ease;
        background: #fff;
        border-radius: 4px;
    }
    .train-row:hover {
        background-color: #f7f7f7;
    }
    .train-row.selected {
        background-color: #e6f7ff;
        border: 2px solid #007bff;
        padding: 14px;
    }
    .fixed-footer {
        position: fixed;
        bottom: 0;
        left: 0;
        width: 100%;
        background-color: #e6f7ff;
        border-top: 1px solid #99c8ff;
        padding: 15px 0;
        text-align: center;
        display: none;
        z-index: 100;
    }
    .footer-buttons {
        display: inline-flex;
        gap: 12px;
    }
    .footer-buttons button {
        padding: 10px 20px;
        font-size: 16px;
        font-family: 'Pretendard','Malgun Gothic',sans-serif;
        cursor: pointer;
        border: none;
        border-radius: 5px;
        font-weight: 600;
        min-width: 100px;
    }
    #selectSeatBtn {
        background-color: #007bff;
        color: white;
    }
    #reserveBtn {
        background-color: #28a745;
        color: white;
    }
    #loadingSpinner {
        display: none;
        text-align: center;
        margin: 20px;
        font-weight: bold;
    }
    #moreBtn {
        border: 1px solid #ccc;
        background-color: #fff;
        border-radius: 20px;
        min-width: 100px;
        font-size: 1rem;
        padding: .6rem 1rem;
        cursor: pointer;
        font-family:'Pretendard','Malgun Gothic',sans-serif;
    }
    .more-area {
        text-align: center;
        margin-top: 20px;
        margin-bottom: 120px;
    }
    .footer-site {
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
    @media (max-width: 768px) {
        .train-row {
            flex-direction: column;
            text-align: center;
        }
    }
</style>
</head>
<body>

<%
    Customer user = (Customer) session.getAttribute("cust");
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
                <span style="font-weight: bold; color: #333;"><%= user.getName() %>님, 환영합니다!</span>
                <a href="logout">로그아웃</a>
                <a href="mypage">마이페이지</a>
            <%
                } else {
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
                 alt="로고" style="height: 80px;">
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

<h1>승차권 예매</h1>
<h2>${dept} → ${arri} 운행 정보</h2>

<div class="train-list-container">
    <c:forEach var="drive" items="${list}">
        <div class="train-row${drive.driveId eq sessionScope.selectedSeat_driveId ? ' selected' : ''}"
             data-drive-id="${drive.driveId}"
             data-price="${drive.price}"
             data-train-no="${drive.trainNo}"
             data-dept-station="${drive.deptStation}"
             data-arri-station="${drive.arriStation}"
             data-formatted-dept-time="${drive.formattedDeptTime}"
             data-formatted-arri-time="${drive.formattedArriTime}"
             onclick="handleRowClick(this)">
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
                <span style="color: gray; margin-left: 10px;">
                    소요시간: ${drive.durationStr}
                </span>
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

<div id="loadingSpinner">데이터를 로딩 중입니다...</div>

<div class="more-area">
    <button id="moreBtn" onclick="loadMore()">더 보기</button>
</div>

<div id="fixedFooter" class="fixed-footer">
    <div class="footer-buttons">
        <button id="selectSeatBtn" onclick="selectSeat()">좌석 선택</button>
        <button id="reserveBtn" onclick="goPayPage()">예매</button>
    </div>
</div>

<div class="footer-site">
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
            <p style="margin-top: 1rem; font-size: 0.8rem;">
                Copyright © Acorn Railroad Corporation. All rights reserved.
            </p>
        </div>
    </div>
</div>

<script>
    let currentOffset = ${list.size()};
    const pageSize    = ${pageSize};
    const deptName    = '${dept}';
    const arriName    = '${arri}';
    const timeFilter  = '${timeFilter}';
    const contextPath = '${pageContext.request.contextPath}';

    const preselectedDriveId = '<c:out value="${sessionScope.selectedSeat_driveId}"/>';
    const preselectedCarNo   = '<c:out value="${sessionScope.selectedSeat_carNo}"/>';
    const preselectedSeatNo  = '<c:out value="${sessionScope.selectedSeat_no}"/>';

    let selectedDriveId   = null;
    let selectedDriveInfo = null;
    const fixedFooterEl   = document.getElementById('fixedFooter');
    const moreBtnEl       = document.getElementById('moreBtn');
    const spinnerEl       = document.getElementById('loadingSpinner');

    document.addEventListener('DOMContentLoaded', function() {
        let row = document.querySelector('.train-row.selected');
        if (!row && preselectedDriveId) {
            row = document.querySelector(`.train-row[data-drive-id="${preselectedDriveId}"]`);
            if (row) row.classList.add('selected');
        }

        if (row) {
            selectedDriveId = Number(preselectedDriveId);

            selectedDriveInfo = {
                driveId: Number(preselectedDriveId),
                price: Number(row.getAttribute('data-price')),
                trainNo: row.getAttribute('data-train-no'),
                deptStation: row.getAttribute('data-dept-station'),
                arriStation: row.getAttribute('data-arri-station'),
                formattedDeptTime: row.getAttribute('data-formatted-dept-time'),
                formattedArriTime: row.getAttribute('data-formatted-arri-time'),
                carNo: preselectedCarNo,
                seatNo: preselectedSeatNo
            };

            if (fixedFooterEl) fixedFooterEl.style.display = 'block';
        }

        if (currentOffset < pageSize && moreBtnEl) {
            moreBtnEl.style.display = 'none';
        }
    });

    function handleRowClick(rowEl) {
        const clickedId = Number(rowEl.getAttribute('data-drive-id'));

        const prevSelected = document.querySelector('.train-row.selected');
        if (prevSelected && prevSelected !== rowEl) {
            prevSelected.classList.remove('selected');
        }

        if (selectedDriveId === clickedId) {
            rowEl.classList.remove('selected');
            selectedDriveId   = null;
            selectedDriveInfo = null;
            if (fixedFooterEl) fixedFooterEl.style.display = 'none';
            return;
        }

        rowEl.classList.add('selected');
        selectedDriveId = clickedId;
        if (fixedFooterEl) fixedFooterEl.style.display = 'block';

        selectedDriveInfo = {
            driveId: clickedId,
            price: Number(rowEl.getAttribute('data-price')),
            trainNo: rowEl.getAttribute('data-train-no'),
            deptStation: rowEl.getAttribute('data-dept-station'),
            arriStation: rowEl.getAttribute('data-arri-station'),
            formattedDeptTime: rowEl.getAttribute('data-formatted-dept-time'),
            formattedArriTime: rowEl.getAttribute('data-formatted-arri-time'),
            carNo: (selectedDriveInfo && selectedDriveInfo.driveId === clickedId) ? selectedDriveInfo.carNo : null,
            seatNo: (selectedDriveInfo && selectedDriveInfo.driveId === clickedId) ? selectedDriveInfo.seatNo : null
        };

        console.log("선택된 운행:", selectedDriveInfo);
    }

    function selectSeat() {
        if (!selectedDriveInfo) {
            alert('열차를 먼저 선택해주세요.');
            return;
        }

        const f = document.createElement('form');
        f.method = 'POST';
        f.action = 'SetSelectedTicket'; // 좌석 선택 flow 쪽 컨트롤러 (프로젝트에 이미 있다고 가정)

        const fields = {
            driveId: selectedDriveInfo.driveId,
            price: selectedDriveInfo.price,
            trainNo: selectedDriveInfo.trainNo,
            deptStation: selectedDriveInfo.deptStation,
            arriStation: selectedDriveInfo.arriStation,
            formattedDeptTime: selectedDriveInfo.formattedDeptTime,
            formattedArriTime: selectedDriveInfo.formattedArriTime
        };

        for (const key in fields) {
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = key;
            input.value = fields[key];
            f.appendChild(input);
        }

        document.body.appendChild(f);
        f.submit();
    }

    function goPayPage() {
        if (!selectedDriveInfo) {
            alert('열차를 선택해주세요.');
            return;
        }

        if (!selectedDriveInfo.carNo || !selectedDriveInfo.seatNo) {
            alert('좌석을 먼저 선택해주세요.');
            return;
        }

        const f = document.createElement('form');
        f.method = 'POST';
        f.action = 'kakaoPayView';

        const fields = {
            driveId: selectedDriveInfo.driveId,
            carNo: selectedDriveInfo.carNo,
            seatNo: selectedDriveInfo.seatNo
        };

        for (const key in fields) {
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = key;
            input.value = fields[key];
            f.appendChild(input);
        }

        document.body.appendChild(f);
        f.submit();
    }

    function loadMore() {
        if (!spinnerEl || !moreBtnEl) return;

        spinnerEl.style.display = 'block';
        moreBtnEl.disabled = true;

        const url =
            'DriveInfoList?action=more'
            + '&deptName='  + encodeURIComponent(deptName)
            + '&arriName='  + encodeURIComponent(arriName)
            + '&startTime=' + encodeURIComponent(timeFilter)
            + '&offset='    + encodeURIComponent(currentOffset);

        fetch(url, { method: 'GET' })
            .then(res => res.json())
            .then(dataList => {
                const container = document.querySelector('.train-list-container');

                dataList.forEach(function(drive) {
                    const newRow = buildRowElement(drive);
                    container.appendChild(newRow);
                });

                currentOffset += dataList.length;
                if (dataList.length < pageSize) {
                    moreBtnEl.style.display = 'none';
                }
            })
            .catch(err => {
                console.error('loadMore error:', err);
                alert('데이터 로딩에 실패했습니다.');
            })
            .finally(() => {
                spinnerEl.style.display = 'none';
                moreBtnEl.disabled = false;
            });
    }

    function buildRowElement(drive) {
        const row = document.createElement('div');
        row.className = 'train-row';
        row.setAttribute('data-drive-id', drive.driveId);
        row.setAttribute('data-price', drive.price);
        row.setAttribute('data-train-no', drive.trainNo);
        row.setAttribute('data-dept-station', drive.deptStation);
        row.setAttribute('data-arri-station', drive.arriStation);
        row.setAttribute('data-formatted-dept-time', drive.formattedDeptTime);
        row.setAttribute('data-formatted-arri-time', drive.formattedArriTime);

        row.addEventListener('click', function() {
            handleRowClick(row);
        });

        const formattedPrice = new Intl.NumberFormat('ko-KR', {
            style: 'currency',
            currency: 'KRW',
            minimumFractionDigits: 0
        }).format(drive.price);

        row.innerHTML =
            '<div class="col-train-info" style="width: 15%; text-align: center;">' +
            '  <img src="' + contextPath + '/images/ktx.png" alt="KTX 로고" style="height: 20px;">' +
            '  <p style="font-weight: bold;">' + drive.trainNo + '</p>' +
            '</div>' +
            '<div class="col-route-time" style="width: 50%;">' +
            '  <span style="font-size: 1.1em; font-weight: bold;">' +
            drive.deptStation + ' → ' + drive.arriStation +
            ' (<span style="color: #007bff;">' + drive.formattedDeptTime + ' ~ ' + drive.formattedArriTime + '</span>)' +
            '  </span>' +
            '  <span style="color: gray; margin-left: 10px;">소요시간: ' + drive.durationStr + '</span>' +
            '</div>' +
            '<div class="col-price" style="width: 35%; text-align: right;">' +
            '  <div style="font-weight: bold; font-size: 1.2em; color: #d9534f;">' +
            formattedPrice +
            '  </div>' +
            '  <div style="color: green; font-size: 0.9em;">5%적립</div>' +
            '  <div style="font-size: 0.9em; color: gray;">일반실</div>' +
            '</div>';

        return row;
    }
</script>

</body>
</html>

