package Seat;


import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;

@WebServlet("/seat")
public class SeatController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String driveParam = req.getParameter("drive_id");
		String carParam = req.getParameter("car_no");
		
		if(driveParam == null || carParam == null) {
			resp.sendRedirect(req.getContextPath() + "/DriveInfoList");
			return;
		}
		
		int drive_id = Integer.parseInt(req.getParameter("drive_id"));
		int car_no = Integer.parseInt(req.getParameter("car_no"));
		
		SeatDAO dao = new SeatDAO();
		
		ArrayList<Seat> SeatList = dao.SeatList(drive_id, car_no);


		// 모델 저장하기
		
		req.setAttribute("SeatList", SeatList);
		req.setAttribute("drive_id", drive_id);
		req.setAttribute("car_no", car_no);
		
		// view 
		
		req.getRequestDispatcher("/WEB-INF/views/seat/seat.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		
		int drive_id = Integer.parseInt(req.getParameter("drive_id"));
		int car_no = Integer.parseInt(req.getParameter("car_no"));
		String seat_no = req.getParameter("seat_no");
		
		System.out.println("선택된 운행 ID :" + drive_id);
		System.out.println("선택한 호차 :" + car_no);
		System.out.println("선택한 좌석 :" + seat_no);
		
		HttpSession session = req.getSession();
		session.setAttribute("selectedSeat_driveId", drive_id);
		session.setAttribute("selectedSeat_carNo", car_no);
		session.setAttribute("selectedSeat_no", seat_no);
		
		String dept = (String) session.getAttribute("searchDept");
		String arri = (String) session.getAttribute("searchArri");
		String start = (String) session.getAttribute("searchStartTime");
		
		String ctx = req.getContextPath();
		
		String qs = String.format("?deptName=%s&arriName=%s&startTime=%s",
				URLEncoder.encode(dept == null ? "" : dept, StandardCharsets.UTF_8),
		        URLEncoder.encode(arri == null ? "" : arri, StandardCharsets.UTF_8),
		        URLEncoder.encode(start == null ? "" : start, StandardCharsets.UTF_8));
		
		resp.sendRedirect(ctx + "/DriveInfoList" + qs );
	}

	
}
