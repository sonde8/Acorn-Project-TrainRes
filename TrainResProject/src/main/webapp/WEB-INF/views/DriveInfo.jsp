<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>운행 정보 조회</title>
<style>
.train-row {
	border : 1px solid #ccc;
	margin-bottom : 10px;
	padding : 15px;
	display : flex;
	cursor : pointer;
	transition: background-color 0.1s ease;
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
/* 기타 스타일 */
.col-train-info, .col-route-time, .col-price {
    /* 기존 스타일 유지 */
}

</style>
</head>
<body>
    <h2 style="text-align: center;">${dept} → ${arri} 운행 정보</h2>
    
    <div class="train-list-container">
        <%-- 초기 10개(또는 조회된 개수) 데이터 출력 (JSTL은 그대로 유지) --%>
        <c:forEach var="drive" items="${list}">
            <div class="train-row" style="border: 1px solid #ccc; margin-bottom: 10px; padding: 15px; display: flex;"
            	data-drive-id="<c:out value='${drive.driveId}'/>"
            	onclick="handleRowClick(this)"
            >
                
                <div class="col-train-info" style="width: 15%; text-align: center;">
                    <img src="images/<c:out value='${drive.trainType}'/>_logo.png" 
                         alt="${drive.trainType} 로고" style="height: 20px;">
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
        <button id="moreBtn" onclick="loadMore()" 
            style="padding: 10px 20px; font-size: 16px; cursor: pointer;">
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
    
    
<script>
    // 현재 로드된 데이터 개수를 저장하는 변수 (더보기))
    let currentOffset = ${list.size()};
    const pageSize = ${pageSize}; // 서블릿에서 설정한 10개
    
    
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
    		if(fixedFooter) fixedFooter.style.display = 'none'; // 푸터를 숨김
    	} else {
    		// 새로운 행을 클릭하면 선택이 됨
    		selectedDriveId = driveId;
    		clickedElement.classList.add('selected'); // 새 행에 스타일 추가
    		if(fixedFooter) fixedFooter.style.display = 'block'; // 푸터가 표시됨
    	}
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
        row.innerHTML = `
            <div class="col-train-info" style="width: 15%; text-align: center;">
                <img src="images/\${drive.trainType}_logo.png" 
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
    	if (selectedDriveId) {
    		alert(selectedDriveId + '번 열차의 좌석을 선택합니다.');
    	} else {
    		alert('열차를 선택해주세요.');
    	}
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