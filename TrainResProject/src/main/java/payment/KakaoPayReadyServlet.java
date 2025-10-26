package payment;
 

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** ready 요청: hidden 필드로 넘어온 주문 정보를 기반으로 카카오 결제 페이지로 리다이렉트 */
@WebServlet(name = "KakaoPayReadyServlet", urlPatterns = "/kakaoPay", loadOnStartup = 1)
public class KakaoPayReadyServlet extends HttpServlet {

    private KakaoPay kakaoPay;

    @Override
    public void init() {
        this.kakaoPay = new KakaoPay(); // 서블릿용 KakaoPay(스프링 제거 버전) 사용
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        OrderDTO order = new OrderDTO();
        order.setPartner_order_id(req.getParameter("partner_order_id"));
        order.setPartner_user_id(req.getParameter("partner_user_id"));
        order.setItem_name(req.getParameter("item_name"));

        try {
            order.setQuantity( (req.getParameter("quantity")));
            order.setTotal_amount (req.getParameter("total_amount"));
            
        } catch (NumberFormatException e) {
            resp.sendError(400, "수량/금액 형식이 올바르지 않습니다.");
            return;
        }

        // ready 호출 (리다이렉트 URL 반환)
        String next = kakaoPay.kakaoPayReady(order);
        // 승인 단계에서 필요하므로 주문과 kakaoPay(혹은 tid)를 세션에 보관
        HttpSession session = req.getSession(true);
        session.setAttribute("pay_order", order);
        session.setAttribute("kakaoPaySvc", kakaoPay);

        resp.sendRedirect(next); // 카카오 결제 페이지로 이동
    }
}
