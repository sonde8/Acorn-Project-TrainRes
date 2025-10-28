<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, Seat.Seat" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 좌석 선택 </title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/seat.css">
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
	 
	 const car_no = document.querySelector('input[name="car_no"]').value;
		 <%-- '<%= String.valueOf(request.getAttribute("car_no")) %>'; --%>
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
 

</script>
</head>
<body>

<h2> 좌석 선택 </h2>

<div class = "carSelec">
		<button onclick = "selectCarNo(1)"> 1호차 </button>
		<button onclick = "selectCarNo(2)"> 2호차 </button>
		<button onclick = "selectCarNo(3)"> 3호차 </button>
</div>

<div class = "SeatChat">

<% ArrayList<Seat> SeatList = (ArrayList<Seat>) request.getAttribute("SeatList");

if(SeatList == null) SeatList = new ArrayList<>();
	
	String[] rows = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
	String[] leftColumn = {"A", "B"};
	String[] rightColumn = {"C", "D"};
	
for(String row : rows){
%>
	
	<div class= "row">

<% 

	// 왼쪽
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
		
			<%= seat_no %>
			
		</div>
		
<%} %>


	<!-- 통로 -->
	<div class = "aisle"></div>
	
<% 
	// 오른쪽
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
		
			<%= seat_no %>
			
		</div>
		
<%} %>
	</div>
	
<%} %>
		
</div>

<form id="seatForm" method="post" action="<%=request.getContextPath()%>/seat">

	<input type="hidden" name="drive_id" value="<%= request.getAttribute("drive_id") %>">
	<input type="hidden" name="car_no" value="<%= request.getAttribute("car_no") %>">
	<input type="hidden" name="seat_no" id="seat_no">



</form>
  

  <button class= "bt" type="button" onclick= 'applySelect()'> 선택 적용 </button>

</body>
</html>

