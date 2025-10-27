package driveinfo;

import java.io.IOException;
import java.util.List; // ArrayList 대신 List 인터페이스 사용

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import driveinfo.DriveInfoResultDTO;

@WebServlet("/DriveInfoList")
public class DriveInfoServlet extends HttpServlet{
	
	// 더보기에서 한 번에 가져올 데이터 개수
	private final int PAGE_SIZE = 10;
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    	// AJAX 요청인지 확인하는 부분
    	String action = req.getParameter("action");
    	 
    	if("more".equals(action)) {
    		handleAjaxRequest(req, resp);
    	} else {
    		handleInitialRequest(req, resp);
    	}		
    }
    
    // 초기 요청 (JSP 페이지 첫 로드) 처리
	private void handleInitialRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    
        // 1. 요청에서 파라미터 값을 읽어오기
        String deptStation = req.getParameter("deptName");
        String arriStation = req.getParameter("arriName");
        String startTimeFilter = req.getParameter("startTime");	// 시간 필터 파라미터 추가
        
        // 2. 분기점: 파라미터 유무 확인ㅌ4
        if (deptStation == null || arriStation == null || deptStation.trim().isEmpty() || arriStation.trim().isEmpty()) {
            // A) 파라미터가 없는 경우 (최초 접근): 검색 폼 (페이지 1)으로 이동
            req.getRequestDispatcher("/WEB-INF/views/driveinfo/SearchForm.jsp").forward(req, resp);
            return; 
        }
        
        // B) 파라미터가 있는 경우 (폼 제출): 데이터 조회 및 결과 페이지 (페이지 2)로 이동 첫 10개만 조회
        DriveInfoService service = new DriveInfoService();
        
        // 첫 페이지 (offset=0, limit=10 만 조회)
        List<DriveInfoResultDTO> list = service.getDriveInfosByRoutePaging(deptStation, arriStation, startTimeFilter, 0, PAGE_SIZE); 
 
        // List<DriveInfoResultDTO> list = service.getDriveInfosByRoute(deptStation, arriStation, startTimeFilter); 
        
        // 조회된 값과 결과를 request에 저장
        req.setAttribute("dept", deptStation);
        req.setAttribute("arri", arriStation);
        // 시간 호출을 위한 request 저장 추가
        req.setAttribute("timeFilter", startTimeFilter);
        req.setAttribute("list", list);
        req.setAttribute("pageSize", PAGE_SIZE);
        
        req.getRequestDispatcher("/WEB-INF/views/driveinfo/DriveInfo.jsp").forward(req, resp);
    }
	
	// 더 보기 Ajax 요청 처리
	private void handleAjaxRequest (HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String deptStation = req.getParameter("deptName");
		String arriStation = req.getParameter("arriName");
		String startTimeFilter = req.getParameter("startTime");
		
		// 현재 로드된 개수 (다음 데이터의 시작 위치)를 파라미터로 받음
		
		int offset = Integer.parseInt(req.getParameter("offset"));
		
		DriveInfoService service = new DriveInfoService();
		
		// 다음 10개 데이터만 조회
		List<DriveInfoResultDTO> list = service.getDriveInfosByRoutePaging(deptStation, arriStation, startTimeFilter, offset, PAGE_SIZE);
		
		// 응답 설정 : JSON 형태로 클라이언트에게 데이터를 보냄
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		// GSON 라이브러리를 사용하여 LIST<DTO>를 문자열로 반환
		Gson gson = new Gson();
		
		String jsonList = gson.toJson(list);
		
		resp.getWriter().write(jsonList);
		
	}	
}