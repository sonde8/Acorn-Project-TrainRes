package mypage;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import customer.Customer;
import customer.CustomerDAO;
import customer.UserDTO;

@WebServlet("/mypage/edit/save")
public class CustomerEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CustomerDAO dao = new CustomerDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        UserDTO user = (session != null) ? (UserDTO) session.getAttribute("cust") : null;

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // DB에서 최신 Customer를 읽어서 수정
        Customer cust = dao.findById(user.getCustId());
        if (cust == null) {
            // 사용자가 DB에 없으면 로그인부터 다시
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String name = req.getParameter("name");
        String pw   = req.getParameter("password");

        if (name != null) cust.setName(name.trim());
        if (pw != null && !pw.trim().isEmpty()) {
            cust.setPassword(pw.trim());
        } else {
            // 비번 미입력 시 기존 비번 유지 (아무 것도 안 함)
        }

        int result = dao.update(cust);

        // 헤더에 보이는 이름 갱신을 위해 세션의 UserDTO도 업데이트
        if (result > 0) {
            user.setName(cust.getName());
            session.setAttribute("cust", user);
        }

        req.setAttribute("message",
                result > 0 ? "✅ 회원 정보가 수정되었습니다." : "❌ 수정 실패! 다시 시도해주세요.");
        req.setAttribute("customer", cust);

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/mypage/mypage_edit.jsp");
        rd.forward(req, resp);
    }
}
