package reservation;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import customer.Customer;

@WebServlet("/Reserve")
public class ReserveController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();

        // ✅ 1️⃣ 로그인 여부 확인
        Customer loginUser = (Customer) session.getAttribute("cust");
        if (loginUser == null) {
            // 로그인 안 되어 있을 경우 → 현재 요청(POST)을 나중에 다시 실행할 수 있도록 세션에 저장
            String query = req.getQueryString() != null ? "?" + req.getQueryString() : "";
            String currentURL = req.getRequestURI() + query;
            session.setAttribute("redirectAfterLogin", currentURL);

            // 로그인 페이지로 이동
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // ✅ 2️⃣ 로그인 되어 있는 경우 → 예매 정보 세션에 저장
        String driveId    = req.getParameter("driveId");
        String trainNo    = req.getParameter("trainNo");
        String dept       = req.getParameter("deptStation");
        String arri       = req.getParameter("arriStation");
        String deptTime   = req.getParameter("formattedDeptTime");
        String arriTime   = req.getParameter("formattedArriTime");

        session.setAttribute("lastReserved_driveId", driveId);
        session.setAttribute("lastReserved_trainNo", trainNo);
        session.setAttribute("lastReserved_dept", dept);
        session.setAttribute("lastReserved_arri", arri);
        session.setAttribute("lastReserved_deptTime", deptTime);
        session.setAttribute("lastReserved_arriTime", arriTime);

        // ✅ 3️⃣ 정상 예매 후 메인 페이지로 이동
        resp.sendRedirect(req.getContextPath() + "/DriveInfoList");
    }
}
