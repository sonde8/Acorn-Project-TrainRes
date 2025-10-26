<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>결제 화면</title>
<style>
  body { font-family: Arial, sans-serif; margin: 32px; color:#222; }
  .title { font-size: 36px; font-weight: 800; border-bottom: 6px solid #2c79c1; display:inline-block; padding-bottom:8px; }
  .wrap { border: 2px solid #bbb; padding: 24px; margin-top: 24px; border-radius: 6px; }
  .bold { font-weight: 700; }
  .box { border:1px solid #ddd; border-radius:8px; padding:16px; background:#fafafa; }
  .row { display:flex; gap:16px; align-items:center; }
  .row>div { flex:1 }
  .paybtn { background:#0f69ff; color:#fff; border:0; padding:12px 20px; border-radius:8px; font-weight:700; cursor:pointer; }
  .pill { padding:10px 16px; border:1px solid #ddd; border-radius:8px; background:#fff; }
  .footer { margin-top: 16px; font-size:12px; color:#666; border-top:1px solid #ddd; padding-top:12px; }
</style>
</head>
<body>

<h1 class="title">결제 화면</h1>

<div class="wrap">
  <h2 style="text-align:center; letter-spacing:8px; font-size:28px; margin:0 0 14px 0;" class="bold">결 제</h2>

  <div class="box" style="margin: 10px 0 24px 0;">
    <div class="row">
      <div class="bold">
        무궁화호&nbsp;&nbsp; ${v.trainNo}
      </div>
      <div class="bold" style="text-align:right;">${v.price}원</div>
    </div>
    <div style="margin-top:6px;">
      ${v.deptStationName} → ${v.arriStationName} (${v.deptTime} - ${v.arriTime})<br/>
      일반실 | 순방향 | 1호차 | 1어른
    </div>
  </div>

  <form method="post" action="${pageContext.request.contextPath}/kakaoPay">
    <!-- 카카오페이에 넘길 값들 -->
    <input type="hidden" name="partner_order_id" value="DRV-${v.driveId}">
    <input type="hidden" name="partner_user_id"  value="demo-user"><!-- 로그인 계정으로 바꾸세요 -->
    <input type="hidden" name="item_name" value="${v.deptStationName} → ${v.arriStationName} (${v.deptTime} - ${v.arriTime}) ${v.trainNo}">
    <input type="hidden" name="quantity" value="1">
    <input type="hidden" name="total_amount" value="${v.price}">

    <div class="row" style="gap:24px;">
      <div class="pill" style="max-width:280px;">
        <div class="bold" style="margin-bottom:8px;">결제수단 선택</div>
        <button type="button" class="paybtn" style="width:100%;">네이버 페이</button>
      </div>

      <div class="pill" style="display:flex; align-items:center; gap:12px;">
        <div class="bold">결제하실 금액 : ${v.price}원</div>
        <button class="paybtn" type="submit">결제</button>
      </div>
    </div>
  </form>

  <div class="footer">이용약관 · 이용내역조회 · 부가서비스 · …</div>
</div>

</body>
</html>
