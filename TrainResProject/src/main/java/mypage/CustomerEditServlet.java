package mypage;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import customer.Customer;
import customer.CustomerDAO;

@WebServlet("/mypage/edit/save")
public class CustomerEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CustomerDAO dao = new CustomerDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        Customer cust = (session != null) ? (Customer) session.getAttribute("cust") : null;

        if (cust == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String name = req.getParameter("name");
        String pw   = req.getParameter("password");

        cust.setName(name);
        if (pw != null && !pw.trim().isEmpty()) {
            cust.setPassword(pw);
        }

        int result = dao.update(cust);

        req.setAttribute("message",
                result > 0 ? "✅ 회원 정보가 수정되었습니다." : "❌ 수정 실패! 다시 시도해주세요.");
        req.setAttribute("customer", cust);

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/mypage/mypage_edit.jsp");
        rd.forward(req, resp);
    }
}
