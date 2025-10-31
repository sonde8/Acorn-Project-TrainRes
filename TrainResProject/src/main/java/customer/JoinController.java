package customer;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import customer.UserDAO;
import customer.UserDTO;

@WebServlet("/join")
public class JoinController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/customer/join.jsp").forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String custId = req.getParameter("cust_id");
		String name = req.getParameter("name");
		String password = req.getParameter("password");

		String yyyy = req.getParameter("yyyy");
		String mm = req.getParameter("mm");
		String dd = req.getParameter("dd");

		String birthStr = yyyy + "-" + mm + "-" + dd;

		System.out.println(custId);
		System.out.println(name);
		System.out.println(birthStr);
		System.out.println(password);

		// "2008-09-10" -> 날짜 UserDTO 생일을 Date 으로 변환하고 있음

		Date birthDate = null;
		try {
			LocalDate localDate = LocalDate.parse(birthStr);
			birthDate = Date.valueOf(localDate);
		} catch (IllegalArgumentException e) {
			req.setAttribute("error", "생년월일 형식이 올바르지 않습니다.");
			req.getRequestDispatcher("/WEB-INF/views/customer/join.jsp").forward(req, resp);
			return;
		}

		UserDAO dao = new UserDAO();
		UserDTO newUser = new UserDTO(custId, name, birthDate, password);

		int result = dao.join(newUser);

		if (result > 0) {
			resp.sendRedirect("login?msg=joined");
		} else {
			req.setAttribute("error", "회원가입에 실패했습니다. (ID 중복 등)");
			req.getRequestDispatcher("/WEB-INF/views/customer/join.jsp").forward(req, resp);
		}
	}

}
