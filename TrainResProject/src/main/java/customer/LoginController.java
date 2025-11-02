package customer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import customer.UserDAO;
import customer.UserDTO;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/customer/login.jsp").forward(req, resp);
	}
	
	
	
	@Override
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
	
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		
		System.out.println(id);
		System.out.println(pw);
		
		UserDAO dao = new UserDAO();
		UserDTO user = dao.login(id, pw);
		
		if (user != null) {   
			//로그인 성공시 
			//로그인 정보를 세션에 저장하기
		    HttpSession session = req.getSession();
		    session.setAttribute("cust", user); 
		    resp.sendRedirect("DriveInfoList");
		} else {
			// 로그인 실패 시 다시 로그인 페이지로 보냄
			req.setAttribute("error","아이디 또는 비밀번호가 틀렸습니다");
			req.getRequestDispatcher("/WEB-INF/views/customer/login.jsp").forward(req, resp);
			
		}

	}

}
