package customer;

	import java.io.IOException;
	import javax.servlet.ServletException;
	import javax.servlet.annotation.WebServlet;
	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;

	@WebServlet("/checkId") // 이 URL로 요청이 들어오면 이 서블릿이 실행됩니다.
	public class IdCheckController extends HttpServlet {
		private static final long serialVersionUID = 1L;

		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			req.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json;charset=UTF-8"); // JSON 형태로 응답 설정
			
			// 1. 클라이언트가 보낸 아이디 값을 받습니다.
			String custId = req.getParameter("id"); 
			
			// 2. DAO를 이용해 DB에서 중복 여부를 확인합니다.
			UserDAO dao = new UserDAO();
			boolean isDuplicated = dao.checkId(custId); // UserDAO에 이 메서드가 있다고 가정
			
			// 3. 결과를 JSON 형태로 클라이언트에게 응답합니다.
			// "result" : true (중복임) 또는 false (사용 가능)
			String resultJson = "{\"result\": " + isDuplicated + "}";
			
			resp.getWriter().write(resultJson);
		}
	}

