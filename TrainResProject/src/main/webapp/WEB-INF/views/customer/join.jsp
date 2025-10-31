<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원가입</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/joinstyle.css">

<style>
* {
	box-sizing: border-box;
	margin: 0;
	padding: 0;
	font-family: 'Malgun Gothic', 'Dotum', sans-serif;
}

body {
	background-color: #f7f9fc;
	color: #333;
	min-height: 100vh;
}

.login-bar {
	height: 100px;
	background-color: #00458C;
	color: #ffffff;
	text-align: center;
	line-height: 100px;
	font-size: 30px;
	font-weight: bold;
	position: fixed;
	top: 0;
	width: 100%;
	z-index: 10;
}

.login-container {
	width: 100%;
	max-width: 600px;
	margin: 0 auto;
	margin-top: 150px;
	padding: 30px;
	background-color: white;
	border-radius: 8px;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
	position: relative;
	z-index: 5;
	margin-bottom: 50px;
}

.tabs {
	width: 100%;
	display: flex;
	justify-content: space-between;
	margin-bottom: 30px;
}

.tabs div {
	flex-grow: 1;
	text-align: center;
	padding: 10px 0;
	font-size: 14px;
	font-weight: bold;
	color: #666;
	border-bottom: 3px solid #e0e0e0;
	cursor: pointer;
	transition: color 0.2s, border-bottom-color 0.2s;
}

.tabs div.active {
	color: #00458C;
	border-bottom: 3px solid #00458C;
}

.step-content {
	padding: 10px 0;
	display: none;
}

.step-content.active {
	display: block;
}

.form-group {
	display: flex;
	align-items: center;
	margin-bottom: 20px;
}

.form-group label {
	width: 120px;
	flex-shrink: 0;
	font-weight: bold;
	font-size: 15px;
	color: #333;
	padding-right: 15px;
}

.input-wrap {
	flex-grow: 1;
	display: flex;
	align-items: center;
	gap: 8px;
	position: relative;
}

.input-field {
	width: 100%;
	padding: 12px 15px;
	font-size: 15px;
	border: 1px solid #e0e0e0;
	border-radius: 3px;
	background-color: #f7f9fb;
	transition: border-color 0.2s, background-color 0.2s;
	height: 45px;
}

.input-field:focus {
	border-color: #007bff;
	background-color: white;
	outline: none;
}

.check-button {
	background-color: #e0e0e0;
	color: #333;
	border: none;
	padding: 0 15px;
	font-size: 14px;
	height: 45px;
	border-radius: 3px;
	cursor: pointer;
	flex-shrink: 0;
	transition: background-color 0.2s;
}

.check-button:hover {
	background-color: #d0d0d0;
}

.input-info {
	position: absolute;
	bottom: -18px;
	left: 0;
	font-size: 12px;
	color: #999;
}

.input-wrap select {
	padding: 12px 10px;
	border: 1px solid #e0e0e0;
	border-radius: 3px;
	background-color: #f7f9fb;
	font-size: 15px;
	height: 45px;
	flex-grow: 1;
	-webkit-appearance: none;
	-moz-appearance: none;
	appearance: none;
	background-image:
		url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 16 16"><path fill="%23666" d="M8 11.5l-4-4h8l-4 4z"/></svg>');
	background-repeat: no-repeat;
	background-position: right 10px center;
	cursor: pointer;
}

.submit-button {
	width: 100%;
	padding: 15px;
	margin-top: 30px;
	font-size: 18px;
	font-weight: bold;
	color: white;
	background-color: #007bff;
	border: none;
	border-radius: 3px;
	cursor: pointer;
	transition: background-color 0.2s;
}

.submit-button:hover {
	background-color: #0056b3;
}

.terms-placeholder {
	padding: 20px;
	border: 1px solid #e0e0e0;
	border-radius: 5px;
	background-color: #fcfcfc;
	text-align: center;
	font-size: 16px;
	color: #666;
	line-height: 1.5;
	margin-bottom: 20px;
}

.home-button {
	width: 100%;
	padding: 15px;
	margin-top: 30px;
	font-size: 18px;
	font-weight: bold;
	color: white;
	background-color: #00458C;
	border: none;
	border-radius: 3px;
	cursor: pointer;
	transition: background-color 0.2s;
	text-decoration: none;
	display: block;
	text-align: center;
}

.home-button:hover {
	background-color: #003366;
}

@media ( max-width : 650px) {
	.login-container {
		margin-top: 100px;
		padding: 20px;
		max-width: 95%;
	}
	.form-group {
		flex-direction: column;
		align-items: flex-start;
	}
	.form-group label {
		width: 100%;
		margin-bottom: 5px;
		padding-right: 0;
	}
	.input-wrap {
		width: 100%;
	}
	.check-button {
		padding: 0 10px;
	}
}
</style>

</head>

<body>



	<div class="login-bar">회원가입</div>
	<div class="login-container">
		<div class="tabs" id="signup-tabs">
			<div id="tab-1" class="active">본인인증</div>
			<div id="tab-2">약관동의</div>
			<div id="tab-3">정보입력</div>
			<div id="tab-4">가입완료</div>
		</div>

		<div class="step-content active" id="step-1">
			<form name="frm" class="login-form"
				action="<%=request.getContextPath()%>/join" method="post">
				<div class="form-group">
					<label for="user-id">아이디</label>
					<div class="input-wrap">
						<input type="text" id="user-id" name="cust_id"
							placeholder="영문, 숫자 4자 이상" class="input-field" required>
						<button type="button" class="check-button" id="idCheckButton"
							onclick="checkIdDuplicate()">중복확인</button>
						<span class="input-info">사용 가능 여부를 확인해 주세요.</span>
					</div>
				</div>
				<div class="form-group">
					<label for="user-name">이름</label>
					<div class="input-wrap">
						<input type="text" id="user-name" name="name"
							placeholder="실명을 입력하세요" class="input-field" required>
					</div>
				</div>

				<div class="form-group">
					<label for="password">비밀번호</label>
					<div class="input-wrap">
						<input type="password" id="password" name="password"
							placeholder="영문, 숫자, 특수문자 포함 8자 이상" class="input-field" required>
						<span class="input-info">보안을 위해 강력한 비밀번호를 사용하세요.</span>
					</div>
				</div>

				<div class="form-group">
					<label for="password-confirm">비밀번호 확인</label>
					<div class="input-wrap">
						<input type="password" id="password-confirm" name="password"
							placeholder="비밀번호를 다시 한 번 입력하세요" class="input-field" required>
					</div>
				</div>

				<div class="form-group">
					<label>생년월일</label>
					<div class="input-wrap">
						<select id="birth-year" name="yyyy">
							<option value="">년도</option>
						</select> <select id="birth-month" name="mm">
							<option value="">월</option>
						</select> <input type="text" id="birth-day" name="dd"
							placeholder="일 (예: 01)" maxlength="2" class="input-field"
							required style="max-width: 120px;">
					</div>
				</div>

				<button type="button" class="submit-button"
					onclick="goToNextStep(2)">다음 단계로 이동</button>
			</form>
		</div>

		<div class="step-content" id="step-2">
			<div class="terms-placeholder">
				<h2>약관동의</h2>
				<p>여기에 서비스 이용약관 및 개인정보 수집/이용 동의에 대한 내용이 표시됩니다.</p>
				<p>동의 체크박스를 추가하고, 다음 단계(정보입력)로 넘어갈 버튼이 필요합니다.</p>
			</div>
			<button type="button" class="submit-button" onclick="goToNextStep(3)"
				style="background-color: #28a745;">약관에 동의하고 다음 단계로</button>
		</div>

		<div class="step-content" id="step-3">
			<div class="terms-placeholder">
				<h2>추가 정보 입력</h2>
				<p>여기에 주소, 이메일 등 추가적인 회원 정보 입력 필드가 표시됩니다.</p>
			</div>
			<button type="button" class="submit-button" onclick="goToNextStep(4)">정보
				입력 완료</button>
		</div>

		<div class="step-content" id="step-4">
			<div class="terms-placeholder" style="border-color: #28a745;">
				<h2>🎉 회원가입이 완료되었습니다!</h2>
				<p>서비스를 이용해 주셔서 감사합니다.</p>
			</div>
			<a href="#" class="home-button">홈으로 이동</a>
		</div>
	</div>

	<script>
    // 현재 단계를 관리하는 전역 변수 (기존 코드 유지)
    let currentStep = 1;
    // ⭐⭐⭐ [추가] 중복 확인 상태를 관리하는 전역 변수 ⭐⭐⭐
    let isIdChecked = false;
    let isIdAvailable = false; 

    // ⭐⭐⭐ [추가] 아이디 중복 확인 함수 ⭐⭐⭐
    function checkIdDuplicate() {
        const userIdField = document.getElementById('user-id');
        const userId = userIdField.value.trim();
        // check-button은 여러 개이므로, input-wrap 내부의 span을 찾는 것이 안전합니다.
        const inputInfo = userIdField.closest('.input-wrap').querySelector('.input-info');
        const checkButton = userIdField.closest('.input-wrap').querySelector('.check-button');

        // 입력값 유효성 검사 (예: 4자 이상)
        if (userId.length < 4) {
            inputInfo.textContent = '아이디는 영문, 숫자 4자 이상이어야 합니다.';
            inputInfo.style.color = 'red';
            isIdAvailable = false;
            return;
        }
        
        // 아이디 입력 필드가 비활성화되어 있는 경우 중복 확인 요청을 보내지 않음 (선택적)
        if (userIdField.readOnly) return; 

        // 비동기 통신(AJAX)을 사용하여 서버에 중복 확인 요청
        fetch('<%=request.getContextPath()%>/checkId', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'id=' + encodeURIComponent(userId) 
        })
        .then(response => response.json())
        .then(data => {
            isIdChecked = true;
            if (data.result) { // result가 true이면 중복
                inputInfo.textContent = '이미 사용 중인 아이디입니다. 다른 아이디를 사용하세요.';
                inputInfo.style.color = 'red';
                isIdAvailable = false;
            } else { // result가 false이면 사용 가능
                inputInfo.textContent = `"${userId}"는 사용 가능한 아이디입니다.`;
                inputInfo.style.color = 'blue';
                isIdAvailable = true;
                // 중복 확인 후 아이디 변경 방지 및 버튼 상태 변경
                userIdField.readOnly = true; 
                checkButton.disabled = true;
                checkButton.textContent = '확인 완료';
            }
        })
        .catch(error => {
            console.error('ID 체크 중 오류 발생:', error);
            inputInfo.textContent = '중복 확인 중 오류가 발생했습니다.';
            inputInfo.style.color = 'red';
            isIdAvailable = false;
        });
    }

    // 아이디 입력 필드가 변경되면 상태 초기화
    document.addEventListener('DOMContentLoaded', function() {
        document.getElementById('user-id').addEventListener('input', function() {
            isIdChecked = false;
            isIdAvailable = false;
            this.readOnly = false;
            // check-button은 여러 개이므로, input-wrap 내부의 버튼을 찾습니다.
            const checkButton = this.closest('.input-wrap').querySelector('.check-button');
            if (checkButton) {
                checkButton.disabled = false;
                checkButton.textContent = '중복확인';
            }
            
            const inputInfo = this.closest('.input-wrap').querySelector('.input-info');
            inputInfo.textContent = '사용 가능 여부를 확인해 주세요.';
            inputInfo.style.color = '#999';
        });
        
        populateDateFields();
    });
    // ⭐⭐⭐ [추가 끝] 아이디 중복 확인 관련 로직 ⭐⭐⭐


    // 날짜 드롭다운 채우기 함수 (기존 코드 유지)
    function populateDateFields() {
        const currentYear = new Date().getFullYear();
        const yearSelect = document.getElementById('birth-year');
        const monthSelect = document.getElementById('birth-month');

        for (let i = currentYear; i >= 1950; i--) {
            const option = document.createElement('option');
            option.value = i;
            option.textContent = i;
            yearSelect.appendChild(option);
        }

        for (let i = 1; i <= 12; i++) {
            const option = document.createElement('option');
            const monthStr = i < 10 ? '0' + i : i;
            option.value = monthStr;
            option.textContent = monthStr;
            monthSelect.appendChild(option);
        }
    }
    
    // ⭐⭐⭐ [통합 및 수정] 다음 단계로 이동 함수 (goToNextStep) ⭐⭐⭐
    function goToNextStep(stepNumber) {
        
        // 1단계에서 다음 단계로 넘어갈 때 (중복 확인 검사)
        if (currentStep === 1) {
            // ⭐ 핵심 검사 로직 추가 ⭐
            if (!isIdChecked || !isIdAvailable) {
                alert('아이디 중복 확인을 완료하고, 사용 가능한 아이디인지 확인해 주세요.');
                return; 
            }
            // TODO: 비밀번호 일치 여부, 기타 유효성 검사 로직을 여기에 추가해야 합니다.
            if(document.getElementById('password').value !== document.getElementById('password-confirm').value){
                alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
                return;
            }
        }
        
        const tabToDeactivate = document.getElementById(`tab-${"${currentStep}"}`);
        if (tabToDeactivate) {
            tabToDeactivate.classList.remove('active');
        }

        const stepToDeactivate = document.getElementById(`step-${"${currentStep}"}`);
        if (stepToDeactivate) {
            stepToDeactivate.classList.remove('active');
        }

        // 단계 이동
        currentStep = stepNumber;

        document.getElementById(`tab-${"${currentStep}"}`).classList.add('active');
        document.getElementById(`step-${"${currentStep}"}`).classList.add('active');
        
        // 최종 제출 단계 (4단계)
        if(stepNumber == 4){
            // 최종 가입 정보 서버로 전송
            let frm  = document.frm;
            frm.submit(); 
        }
    }
    // ⭐⭐⭐ [통합 및 수정 끝] 다음 단계로 이동 함수 (goToNextStep) ⭐⭐⭐
    
    // window.onload 대신 DOMContentLoaded 이벤트 리스너를 위에서 사용했습니다.
</script>
</body>

</html>