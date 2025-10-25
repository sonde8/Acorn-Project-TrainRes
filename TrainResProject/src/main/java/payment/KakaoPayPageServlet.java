package payment;

 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/** 결제 시작 화면: driveId로 조회한 정보를 모델에 담아 JSP로 포워드 */
@WebServlet("/kakaoPayView")
public class KakaoPayPageServlet extends HttpServlet {

    private PaymentDAO paymentDAO;

    @Override
    public void init() {
        // 실제로는 JNDI DataSource를 주입하거나, 생성자 파라미터로 넘겨주는 구조 추천
        this.paymentDAO = new PaymentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String driveIdStr = req.getParameter("driveId");
        if (driveIdStr == null) {
            resp.sendError(400, "잘못된 요청입니다. (driveId 누락)");
            return;
        }

        long driveId;
        try {
            driveId = Long.parseLong(driveIdStr);
        } catch (NumberFormatException e) {
            resp.sendError(400, "잘못된 요청입니다. (driveId 형식 오류)");
            return;
        }

        PaymentView v = paymentDAO.findDriveForPay(driveId);
        if (v == null) {
            resp.sendError(400, "해당 운행 정보가 없습니다.");
            return;
        }

        req.setAttribute("v", v);
        // /WEB-INF/views/kakaoPay.jsp 로 포워드
        req.getRequestDispatcher("/WEB-INF/views/kakaoPay.jsp").forward(req, resp);
    }
}
