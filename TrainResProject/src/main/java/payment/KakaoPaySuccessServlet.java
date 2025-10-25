package payment;

 
 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/** 결제 성공 콜백: pg_token을 받아 approve 호출 후 결과를 JSP로 전달 */
@WebServlet("/kakaoPaySuccess")
public class KakaoPaySuccessServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pgToken = req.getParameter("pg_token");
        if (pgToken == null) {
            resp.sendError(400, "pg_token이 없습니다.");
            return;
        }

        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendError(400, "세션이 만료되었습니다. 처음부터 다시 시도해주세요.");
            return;
        }

        OrderDTO order = (OrderDTO) session.getAttribute("pay_order");
        KakaoPay kakaoPay = (KakaoPay) session.getAttribute("kakaoPaySvc");

        if (order == null || kakaoPay == null) {
            resp.sendError(400, "결제 세션 정보가 없습니다. 처음부터 다시 시도해주세요.");
            return;
        }

        // 승인 요청: JSON 결과(문자열) 반환 (VO 매핑 버전도 가능)
        String approvalJson = kakaoPay.kakaoPayInfo(pgToken, order);
        req.setAttribute("resultJson", approvalJson);

        // 사용 완료된 세션 데이터 정리
        session.removeAttribute("pay_order");
        session.removeAttribute("kakaoPaySvc");

        req.getRequestDispatcher("/WEB-INF/views/kakaoPaySuccess.jsp").forward(req, resp);
    }
}
