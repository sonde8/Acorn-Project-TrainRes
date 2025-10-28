<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>결제 취소</title>
<style>
    body { font-family:'Pretendard','Malgun Gothic',sans-serif; background:#f5f5f5; margin:0; padding:2rem; }
    .wrap {
        max-width:500px;
        margin:0 auto;
        background:#fff;
        border:1px solid #ddd;
        border-radius:8px;
        padding:2rem;
        text-align:center;
    }
    h1 { margin-top:0; font-size:1.3rem; color:#d9534f; }
    p  { font-size:.95rem; line-height:1.4rem; margin-top:1rem; }
    .btn-row { margin-top:1.5rem; }
    .btn-blue {
        background:#007bff;
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
    <h1>결제가 취소되었습니다</h1>
    <p>다시 예매를 진행하시려면 홈으로 돌아가 열차를 선택해주세요.</p>

    <div class="btn-row">
        <form action="${pageContext.request.contextPath}/home" method="get" style="margin:0;">
            <button type="submit" class="btn-blue">홈으로</button>
        </form>
    </div>
</div>

</body>
</html>
