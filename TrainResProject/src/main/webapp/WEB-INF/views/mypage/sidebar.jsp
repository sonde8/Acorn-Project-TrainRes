<%@ page contentType="text/html; charset=UTF-8" %>
<div class="sidebar">
  <h2>🚄 MY에이콘레일</h2>
  <ul>
    <li onclick="location.href='<%=request.getContextPath()%>/mypage'">마이페이지</li>
    <li onclick="location.href='<%=request.getContextPath()%>/mypage/resdetail?resId=1'">승차권 조회</li>
    <li onclick="location.href='<%=request.getContextPath()%>/DriveInfoList'">기차여행</li>
    <li onclick="location.href='<%=request.getContextPath()%>/mypage/payments'">결제내역</li>
    <li onclick="location.href='<%=request.getContextPath()%>/mypage/edit'">회원정보관리</li>
  </ul>
</div>
