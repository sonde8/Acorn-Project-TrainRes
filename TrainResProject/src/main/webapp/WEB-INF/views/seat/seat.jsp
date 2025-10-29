<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, Seat.Seat" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 좌석 선택 </title>
<style>
@charset "UTF-8";

:root{
	--frame: #172742;
	--panel: #cdd0d4;
	--head: #03559c;
	--back: #557a9b;
	--cush: #8babcc;
	--arm: #142138;
	--accent: #838a00;
}

.seat-header{
	position: relative;
	height: 70px;
	display: flex;
	justify-content: center;
	align-items: flex-start;
	padding: 10px 20px;
	margin-bottom: 15px;
}

.seat-header h2{
	text-align: center;
	line-height: 1;
	font-size: 30px;
	margin: 0;
}
	
.seat-header .left{
   position: absolute;
   bottom: -6px;
   z-index: 2;
   left: calc(50% - 574px + 48px);
}
	
.seat-header .right{
   position: absolute;
   bottom: -6px;
   z-index: 2;
   right: calc(50% - 574px + 48px);
}
	
.seat-header .bt{
	flex-shrink: 0;
}

.carDropdown{
	position: relative;
	display: inline-block;
	flex-shrink: 0;
}

.carBtn {
  background: #e4f2f5;
  border: 1px solid #a3b4b8;
  color: black;
  border:none;
  padding:10px 18px;
  font-size:15px;
  border-radius:21px;
  cursor:pointer;
}

.carMenu {
  position: absolute;
  background: white;
  border:1px solid #cfcccc;
  min-width: 160px;
  border-radius: 8px;
  padding: 6px 0;
  margin-top: 6px;
  list-style: none;
  box-shadow: 0 10px 20px rgba(0,0,0,.15);
  z-index: 1000;
}

.carMenu li {
	padding: 10px;
	cursor: pointer;
}

.carMenu li:hover { background: white; }

.hidden { display: none; }

.bt{
	display: block;
	margin: 20px auto 0;
	padding: 10px 20px;
	background: gray;
	border: 1px solid #b5bbbd;
	color: white;
	font-size: 15px;
	cursor: pointer;
	border-radius:21px;
	transition: transform 0.12s ease;
}

.bt:hover{
	transform: scale(1.02);
}

.coach{
	max-width: 900px;
	margin: 25px auto 48px;
	padding: 55px 120px;
	border: 4px solid var(--frame);
	border-radius: 57px;
	box-shadow: 0 8px 24px;	
	background: white;
}


.row{
	display: grid;
	grid-template-columns: 1fr 1fr 90px 1fr 1fr;
	gap: 20px 34px;
	align-items: center;
	justify-content: center;
	margin-bottom: 23px;
}

.aisle{
	width: 90px;
	height: 100%;
	}

.seat{
	--w: 130px;
	--h: 110px;
	width: var(--w);
	height: var(--h);
	position: relative;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	cursor: pointer;
	user-select: none;
	background: var(--panel);
	border: 5px solid var(--frame);
	border-radius: 10px;
	transition: transform .12s ease;
}

.seat::before{
	content:"";
	position: absolute;
	left: 10px;
	right: 10px;
	top: 8px;
	height: 15px;
	background: var(--frame);
	border: 2px solid var(--frame);
	border-radius:  8px;
}

.seat > i{
	position: absolute;
	left: 13px;
	right: 13px;
	bottom: 10px;
	height: 15px;
	background: var(--cush);
	border: 2px solid var(--frame);
	border-radius: 8px;
}

.seat > b, .seat > em{
	position: absolute;
	bottom: 8px;
	width: 30px;
	height: 10px;
	background: var(--arm);}
	
.seat .label{
	position: relative;
	font-size: 23px;
	font-weight: 900;
}

.seat .available:hover{
	transform: translateY(-3px) scale(1.02);
}

.seat.selected{
	outline: 4px solid var(--accent);
	outline-offset : 3px;
}

.seat.reserved{
	background: #4d535e !important;
	border-color: #2f3238 !important;
	cursor: not-allowed;
}

.seat.reserved::before,
.seat.reserved::after,
.seat.reserved > i,
.seat.reserved > b,
.seat.reserved > em{
	background:#575c66 !important;
 	border-color:#2f3238 !important;
}

.seat.reserved.label{
	color: gray;
}

@media(max-width:900px){
  .seat{ --w:105px; --h:120px; }
  .seat .label{ font-size:18px; }
  .row{
    grid-template-columns:105px 105px 60px 105px 105px;
  }
}
@media(max-width:680px){
  .seat{ --w:95px; --h:110px; }
  .seat .label{ font-size:16px; }
  .row{
    grid-template-columns:95px 95px 48px 95px 95px;
  }
}
</style>

<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seat.css"> --%>
<script>

function toggleSeat(a){
	if(a.classList.contains('reserved')) return;
	const selected = document.querySelector('.seat.selected');
	if(selected && selected !== a){
		selected.classList.remove('selected');
	}
	a.classList.toggle('selected');
}

function applySelect(){
	 const selectedSeat = document.querySelector('.seat.selected');
	 if(!selectedSeat){
		 alert("좌석을 선택해주세요");
		 return;
	 }
	 
	 const seatLabel = (selectedSeat.querySelector('.label')?.textContent || selectedSeat.textContent).trim();
	 const car_no = document.querySelector('input[name="car_no"]').value;
	 const seat_no = selectedSeat.textContent.trim(); 

	    alert(car_no + '호차 ' + seat_no + ' 좌석이 선택되었습니다.');
	  
	  document.getElementById('seat_no').value = selectedSeat.textContent.trim();
	  document.getElementById('seatForm').submit();
	  }
 
 function selectCarNo(carNo){
	    const driveId = '<%= request.getParameter("drive_id") == null ? "" : request.getParameter("drive_id") %>';
	    if (!driveId) { alert('운행 정보(drive_id)가 없습니다.'); return; }

	    // 공백 없이, 인코딩해서 이동
	    const ctx = '<%= request.getContextPath() %>';
	    const url = ctx + '/seat?drive_id=' + encodeURIComponent(driveId) +
	                '&car_no=' + encodeURIComponent(carNo);
	    location.href = url;
	  }
 
 function toggleCarMenu(){
	  var menu = document.getElementById('carMenu');
	  if(!menu){ console.warn('carMenu not found'); return; }
	  menu.classList.toggle('hidden');
	}
 
</script>
</head>
<body>

<%
  Object carAttr = request.getAttribute("car_no");
  String carParam = request.getParameter("car_no");
  int selectedCarNo = 1;
  try {
      if (carAttr != null) selectedCarNo = Integer.parseInt(String.valueOf(carAttr));
      else if (carParam != null) selectedCarNo = Integer.parseInt(carParam);
  } catch(Exception ignore){ selectedCarNo = 1; }
%>

<div class = "seat-header">
  <div class = "left">
	<div class="carDropdown">
	
 	 <div id="carBtn" class="carBtn" onclick="toggleCarMenu()">
    	<%= selectedCarNo %>호차  ▼
	 </div>
	 
  	  <ul id="carMenu" class="carMenu hidden" role="menu">
   	 	<li role="menuitem" onclick="selectCarNo(1)">1호차</li>
    	<li role="menuitem" onclick="selectCarNo(2)">2호차</li>
    	<li role="menuitem" onclick="selectCarNo(3)">3호차</li>
 	  </ul>
	 </div>
  </div>	 
	 <h2> 좌 석 선 택 </h2>	 
  <div class = "right">
	  <button class= "bt" type="button" onclick= 'applySelect()'> 선 택 </button>
  </div>	 
</div>

<!-- 차량 전체 -->
<div class = "coach">
	
	<!-- 좌석 -->
	<div class = "seats">

	<% 
		ArrayList<Seat> SeatList = (ArrayList<Seat>) request.getAttribute("SeatList");

		if(SeatList == null) SeatList = new ArrayList<>();
	
		String[] rows = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
		String[] leftColumn = {"A", "B"};
		String[] rightColumn = {"C", "D"};
	
		for(String row : rows){
	%>

		<div class= "row">
		<% 	// 왼쪽
			for(String col : leftColumn){
		
			String seat_no = row + col;
			Seat targetSeat = null;
		
			for(Seat s : SeatList){
				if (seat_no.equals(s.getSeat_no())) {
					targetSeat = s;
					break;
				}
			}
		
			String cls = (targetSeat != null && "Y".equals(targetSeat.getReserved())) ? "reserved" : "available";
		%>

			<div class ="seat <%= cls %>" onclick = "toggleSeat(this)">
		
				<span class = "label"> <%= seat_no %> </span>
				<i></i><b></b><em></em>
			
			</div>
		
		<%} %>


		<!-- 통로 -->
		<div class = "aisle"></div>
	
		<% // 오른쪽
			for(String col : rightColumn){
		
				String seat_no = row + col;
				Seat targetSeat = null;
		
			for( Seat s : SeatList){
				if(s.getSeat_no().equals(seat_no)){
					targetSeat = s;
					break;
				}
			}
		
			String cls = (targetSeat != null && "Y".equals(targetSeat.getReserved())) ? "reserved" : "available";
		  
		%>
		
			<div class="seat <%= cls %>" onclick = "toggleSeat(this)">
		
				<span class = 'label'> <%= seat_no %> </span>
				<i></i><b></b><em></em>
			
			</div>
		
		<%} %>
	</div>
	
  <%} %>
 </div>		
</div>

<form id="seatForm" method="post" action="<%=request.getContextPath()%>/seat">

	<input type="hidden" name="drive_id" value="<%= request.getAttribute("drive_id") %>">
	<input type="hidden" name="car_no" value="<%= request.getAttribute("car_no") %>">
	<input type="hidden" name="seat_no" id="seat_no">

</form>
 
</body>
</html>