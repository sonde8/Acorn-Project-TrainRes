package customer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/customer/login")
public class CustomerLoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {

	    // ✅ JSP는 WEB-INF 아래 있으므로 직접 접근 금지, forward로만 접근
	    req.getRequestDispatcher("/WEB-INF/views/customer/login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {

	    req.setCharacterEncoding("UTF-8");
	
	    String id = req.getParameter("id");
	    String pw = req.getParameter("pw");
	    
	    CustomerDAO dao = new CustomerDAO();
	    Customer user = dao.login(id, pw);
	    
	    if (user != null) {
	        HttpSession session = req.getSession();
	        session.setAttribute("cust", user); 
	        resp.sendRedirect(req.getContextPath() + "/mypage");
	    } else {
	        req.setAttribute("error", "아이디 또는 비밀번호가 틀렸습니다");
	        req.getRequestDispatcher("/WEB-INF/views/customer/login.jsp").forward(req, resp);
	    }
	}
}
