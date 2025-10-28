<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8"/>
<title>결제 완료</title>
<style>
    body { font-family: 'Malgun Gothic', sans-serif; background:#f5f5f5; }
    .box {
        max-width:420px;
        margin:80px auto;
        background:#fff;
        border:1px solid #ddd;
        border-radius:6px;
        padding:24px 28px;
        text-align:center;
        line-height:1.5;
    }
    .price {
        color:#d9534f;
        font-size:1.1rem;
        font-weight:700;
        margin:12px 0 20px;
    }
    .btn-wrap { margin-top:24px; display:flex; justify-content:center; gap:12px; flex-wrap:wrap; }
    .btn {
        border:none;
        border-radius:4px;
        padding:.6rem 1rem;
        font-size:.9rem;
        font-weight:600;
        cursor:pointer;
        color:#fff;
        min-width:100px;
    }
    .btn-home { background:#007bff; }
    .btn-myp  { background:#28a745; }
</style>
</head>
<body>
<div class="box">
    <h2 style="margin-top:0;">결제가 완료되었습니다.</h2>

    <div style="text-align:left; font-size:.9rem; color:#333; margin-top:20px;">
        <div><strong>열차/구간 :</strong> ${order.item_name}</div>
        <div class="price">${order.total_amount}원 결제 완료</div>
    </div>

    <div class="btn-wrap">
        <button class="btn btn-home" onclick="location.href='${pageContext.request.contextPath}/home'">홈으로</button>
        <button class="btn btn-myp" onclick="location.href='${pageContext.request.contextPath}/mypage'">마이페이지</button>
    </div>

</div>
</body>
</html>

