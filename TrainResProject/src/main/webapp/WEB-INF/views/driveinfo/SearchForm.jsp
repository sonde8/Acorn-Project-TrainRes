<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="search-form">
    <form action="DriveInfoList" method="GET"> 
        
        <label for="dept-station">출발역:</label>
        <select id="dept-station" name="deptName">
            <option value="광주송정">광주송정</option>
            <option value="용산">용산</option>
            <option value="서울">서울</option>
            <option value="부산">부산</option>
            <option value="대전">대전</option>
        </select>
        
        <label for="arri-station">도착역:</label>
        <select id="arri-station" name="arriName">
            <option value="광주송정">광주송정</option>
            <option value="용산" selected>용산</option>
            <option value="서울">서울</option>
            <option value="부산">부산</option>
            <option value="대전">대전</option>
        </select>
        
        <!-- 시간 필터링 -->
        <label for="dept-time-filter">출발 시간</label>
        <select id=dept-time-filter name="startTime">
        	<option value="00">00</option>
        	<option value="01">01</option>
        	<option value="02">02</option>
        	<option value="03">03</option>
        	<option value="04">04</option>
        	<option value="05">05</option>
        	<option value="06">06</option>
        	<option value="07">07</option>
        	<option value="08">08</option>
        	<option value="09">09</option>
        	<option value="10">10</option>
        	<option value="11">11</option>
        	<option value="12">12</option>
        	<option value="13">13</option>
        	<option value="14">14</option>
        	<option value="15">15</option>
        	<option value="16">16</option>
        	<option value="17">17</option>
        	<option value="18">18</option>
        	<option value="19">19</option>
        	<option value="20">20</option>
        	<option value="21">21</option>
        	<option value="22">22</option>
        	<option value="23">23</option>
        </select>
        
        <button type="submit">열차 조회하기</button>
    </form>
</div>
</body>
</html>