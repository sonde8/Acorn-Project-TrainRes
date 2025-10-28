package customer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class CustomerLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/views/customer/login.jsp")
           .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        String pw = req.getParameter("pw");

        System.out.println(id);
        System.out.println(pw);

        CustomerDAO dao = new CustomerDAO();
        Customer user = dao.login(id, pw);

        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("cust", user);  // 세션에 Customer 저장
            resp.sendRedirect("DriveInfoList");  // 로그인 성공 후 이동
        } else {
            req.setAttribute("error", "아이디 또는 비밀번호가 틀렸습니다");
            req.getRequestDispatcher("/WEB-INF/views/customer/login.jsp").forward(req, resp);
        }
    }
}
