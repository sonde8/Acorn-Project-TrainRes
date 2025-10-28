<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>결제 확인</title>
<style>
/* 그대로 */
    body {
        font-family: 'Pretendard','Malgun Gothic',sans-serif;
        background:#f5f5f5; margin:0; padding:2rem;
    }
    .wrap {
        max-width:600px;
        margin:0 auto;
        background:#fff;
        border:1px solid #ddd;
        border-radius:8px;
        padding:2rem;
    }
    h1 {
        margin-top:0;
        font-size:1.4rem;
        text-align:center;
    }
    .info-box {
        margin-top:1rem;
        line-height:1.5rem;
        font-size:0.95rem;
    }
    .price {
        font-size:1.2rem;
        font-weight:bold;
        color:#d9534f;
        margin-top:1rem;
    }
    .btn-row {
        margin-top:2rem;
        text-align:center;
        display:flex;
        gap:10px;
        justify-content:center;
        flex-wrap:wrap;
    }
    .btn-blue {
        background:#007bff;
        color:#fff;
        border:none;
        border-radius:4px;
        padding:.7rem 1.2rem;
        font-size:.95rem;
        cursor:pointer;
    }
    .btn-gray {
        background:#999;
        color:#fff;
        border:none;
        border-radius:4px;
        padding:.7rem 1.2rem;
        font-size:.95rem;
        cursor:pointer;
    }
</style>
</head>
<body>

<div class="wrap">
    <h1>결제 정보 확인</h1>

    <div class="info-box">
        <div><strong>열차번호:</strong> ${v.trainNo}</div>
        <div><strong>구간:</strong> ${v.deptStationName} → ${v.arriStationName}</div>
        <div><strong>출발~도착:</strong> ${v.deptTime} ~ ${v.arriTime}</div>
        <c:if test="${not empty carNo && not empty seatNo}">
            <div><strong>좌석:</strong> ${carNo}호차 ${seatNo}좌석</div>
        </c:if>
        <div class="price">
            <fmt:formatNumber value="${v.price}" pattern="#,###원"/>
        </div>
    </div>

    <form method="post"
          action="${pageContext.request.contextPath}/kakaoPayReady"
          style="margin:0;">

        <input type="hidden" name="driveId" value="${v.driveId}"/>
        <input type="hidden" name="carNo"  value="${carNo}"/>
        <input type="hidden" name="seatNo" value="${seatNo}"/>

        <div class="btn-row">
            <button type="submit" class="btn-blue">
                카카오페이로 결제하기
            </button>

            <button type="button" class="btn-gray"
                    onclick="history.back();">
                돌아가기
            </button>
        </div>
    </form>
</div>

</body>
</html>
