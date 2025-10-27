package reservation;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import driveinfo.DriveInfoResultDTO;

@WebServlet("/SetSelectedTicket")
public class SetSelectedTicketController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		DriveInfoResultDTO selectedTicket = new DriveInfoResultDTO();
		
		try {
			// JSP에서 hidden input으로 넘긴 파라미터를 받아서 DTO에 세팅합니다.
            // 문자열은 바로 받지만, 숫자는 파싱(Integer.parseInt)해야 합니다.
            selectedTicket.setDriveId(Integer.parseInt(req.getParameter("driveId")));
            String priceStr = req.getParameter("price");
            if (priceStr != null && !priceStr.trim().isEmpty()) {
                // BigDecimal 객체를 생성하여 setPrice 메소드에 전달
                selectedTicket.setPrice(new BigDecimal(priceStr.trim())); 
            } else {
                // 값이 없는 경우 0 또는 기본값으로 처리하거나, 오류를 throw 할 수 있습니다.
                selectedTicket.setPrice(BigDecimal.ZERO); 
            }
            
            // 나머지 문자열 필드 설정
            selectedTicket.setTrainNo(req.getParameter("trainNo"));
            selectedTicket.setDeptStation(req.getParameter("deptStation"));
            selectedTicket.setArriStation(req.getParameter("arriStation"));
            
            // 포맷팅된 시간 정보도 세션에 저장하여 다음 페이지에서 사용할 수 있도록 합니다.
            selectedTicket.setFormattedDeptTime(req.getParameter("formattedDeptTime"));
            selectedTicket.setFormattedArriTime(req.getParameter("formattedArriTime"));

            // 2. 세션에 저장
            HttpSession session = req.getSession(); // 이미 로그인되어 세션이 존재합니다.
            session.setAttribute("selectedTicket", selectedTicket); 
            
            // 로깅: 서버 콘솔에서 확인 (선택 사항)
            System.out.println("선택된 열차 정보 세션 저장 완료: " + selectedTicket.getDriveId());

            // 3. 다음 페이지인 좌석 선택 서블릿(팀원 담당)으로 리다이렉트
            // 💡 [필수 수정] 팀원과 좌석 선택 서블릿의 최종 URL을 협의하여 여기에 넣어주세요.
            resp.sendRedirect("SeatSelectionPage");
		} catch (NumberFormatException e) {
			System.err.println("데이터 파싱오류:" + e.getMessage());
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST,  "잘못된 열차 정보 요청입니다");
		}
	}
}
