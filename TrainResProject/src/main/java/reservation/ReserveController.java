package reservation;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/Reserve")
public class ReserveController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        // 여기서는 일단 받은 정보 세션에 잠깐 저장만 하고
        // 메인 페이지로 돌려보내는 형태로 마무리.
        // (필요 없으면 이 서블릿 자체를 프로젝트에서 제거해도 무방)

        HttpSession session = req.getSession();

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

        resp.sendRedirect(req.getContextPath() + "/DriveInfoList");
    }
}
