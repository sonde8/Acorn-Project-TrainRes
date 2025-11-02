## 에이콘레일 서비스
- 서비스명 : AcornRail
- 서비스설명 : 기차 예매 서비스
<br>

## 프로젝트 기간
2025.10.20 ~ 2025.10.30


<br>


## 주요 기능
- 열차 운행편 조회
- 열차 좌석 선택 기능
- 열차 예매 기능
<br>

## 기술 스택
## 기술 스택
<table>
    <tr>
        <th>구분</th>
        <th>내용</th>
    </tr>
    <tr>
        <td>사용언어</td>
        <td>
            <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=Java&logoColor=white"/>
            <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=black"/>
        </td>
    </tr>
    <tr>
        <td>프론트엔드</td>
        <td>
            <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=HTML5&logoColor=white"/>
            <img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=CSS3&logoColor=white"/>
        </td>
    </tr>
    <tr>
        <td>백엔드</td>
        <td>
            <img src="https://img.shields.io/badge/Apache%20Tomcat-F8DC75?style=for-the-badge&logo=Apache%20Tomcat&logoColor=black"/>
        </td>
    </tr>
    <tr>
        <td>데이터베이스</td>
        <td>
            <img src="https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=Oracle&logoColor=white"/>
        </td>
    </tr>
</table>

## 서비스 플로우
<img width="1474" height="741" alt="스크린샷 2025-11-02 오후 6 59 24" src="https://github.com/user-attachments/assets/ac69e022-c817-412d-ad75-f25716ff4d7d" />

## 유저 플로우
<img width="1457" height="719" alt="스크린샷 2025-11-02 오후 6 59 32" src="https://github.com/user-attachments/assets/6d8b16ba-e7e3-43e5-9eeb-ec565e302724" />

## ERD
<img width="1264" height="732" alt="유승재 ERD" src="https://github.com/user-attachments/assets/42d1bb23-d8b0-47a0-98c1-2cf0cba46da9" />

<br>
<hr>

## 주요기능
### 1. 로그인/회원가입
<img width="1394" height="666" alt="스크린샷 2025-11-02 오후 7 00 27" src="https://github.com/user-attachments/assets/f26e26cf-ad11-43f7-ba37-be0f6c162024" />
<img width="1397" height="669" alt="스크린샷 2025-11-02 오후 7 00 35" src="https://github.com/user-attachments/assets/e5efd4fe-0d75-4ff7-9703-03556c37bd59" />

- 회원가입시 DB에 중복된 아이디가 있는지 검사하고 중복된 아이디로는 회원가입이 불가합니다.
- 비밀번호란에 입력된 비밀번호와 동일한지 검사합니다.
- 다음 단계로 이동시 DB에 회원가입 정보가 저장됩니다.

<br>
<hr>

### 2. 메인페이지
<img width="1393" height="776" alt="스크린샷 2025-11-02 오후 7 00 44" src="https://github.com/user-attachments/assets/b1e4d189-fc11-4a7c-8be4-830b5668cf37" />

- 로그인시 로그인에 사용된 이름을 DB에서 불러와 화면에 출력합니다
- 출발역, 도착역, 출발 시간을 설정하여 필터링하여 운행정보를 조건에 맞게 DB에서 불러옵니다.

<br>
<hr>

### 3. 운행 정보 페이지
<img width="1390" height="783" alt="스크린샷 2025-11-02 오후 7 00 52" src="https://github.com/user-attachments/assets/b3b512c7-e096-45ce-8ad9-6abfb3f1350e" />
<img width="1393" height="781" alt="스크린샷 2025-11-02 오후 7 01 30" src="https://github.com/user-attachments/assets/5f5bdc49-5e71-47d4-b148-64281acadeed" />

- 메인페이지에서 선택한 출발지, 도착지 정보를 가져와 화면에 출력합니다.
- 조건에 맞는 데이터들만 DB에서 불러와 화면에 출력이 됩니다
- 더보기 클릭시 데이터를 10개를 추가로 불러옵니다.
- 좌석 선택 버튼 클릭시 운행정보를 session에 담고 좌석 선택 페이지로 보냅니다.

<br>
<hr>

### 4. 좌석 선택 페이지
<img width="1399" height="689" alt="스크린샷 2025-11-02 오후 7 01 42" src="https://github.com/user-attachments/assets/e94b8d78-a607-42df-8923-4c9f082f23b3" />

- 좌석 선택 클릭시 좌석 정보가 session에 담깁니다

<br>
<hr>

### 5. 예메 페이지
<img width="1397" height="778" alt="스크린샷 2025-11-02 오후 7 01 50" src="https://github.com/user-attachments/assets/0044e440-dd96-4eb0-926c-8541166f8ac6" />
<img width="1392" height="776" alt="스크린샷 2025-11-02 오후 7 01 56" src="https://github.com/user-attachments/assets/a8ec5513-bce0-40e8-b378-8478bb35698c" />

- 현재 session에 담긴 데이터들을 가지고 카카오 결제 api를 불러옵니다.

<br>
<hr>

### 6. 마이페이지
<img width="1387" height="780" alt="스크린샷 2025-11-02 오후 7 02 04" src="https://github.com/user-attachments/assets/3b106f89-d78b-4daf-8cef-866c9ea10337" />
<img width="1396" height="784" alt="스크린샷 2025-11-02 오후 7 02 12" src="https://github.com/user-attachments/assets/bcf0662b-cdce-4631-b537-2ce736465647" />
<img width="1394" height="780" alt="스크린샷 2025-11-02 오후 7 02 18" src="https://github.com/user-attachments/assets/ec3888a1-4bca-40e5-bf95-7119d69d77c9" />

- 나의 기본정보를 DB에서 불러와 화면에 출력합니다.
- DB에서 데이터를 불러와 예약 내역을 확인할 수 있습니다.
- 비밀번호 수정이 가능합니다



<br>
<hr>

  

## 팀원소개
<img width="1475" height="823" alt="스크린샷 2025-11-02 오후 6 58 44" src="https://github.com/user-attachments/assets/fbd60534-58bb-4ede-9c56-5fca1187da9b" />



