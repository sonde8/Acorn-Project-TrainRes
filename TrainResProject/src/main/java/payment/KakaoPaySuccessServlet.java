package payment;

import Seat.SeatDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/kakaoPaySuccess")
public class KakaoPaySuccessServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private PaymentDAO paymentDAO;

    @Override
    public void init() {
        this.paymentDAO = new PaymentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);

        if (session == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "세션이 만료되었습니다. 다시 결제를 시도해주세요.");
            return;
        }

        String pgToken = req.getParameter("pg_token");
        if (pgToken == null || pgToken.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "pg_token 파라미터가 없습니다.");
            return;
        }

        OrderDTO order = (OrderDTO) session.getAttribute("pay_order");
        KakaoPay kakaoPaySvc = (KakaoPay) session.getAttribute("kakaoPaySvc");

        if (order == null || kakaoPaySvc == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "결제 세션 정보가 없습니다. 다시 시도해주세요.");
            return;
        }

        String approveJson;
        try {
            approveJson = kakaoPaySvc.kakaoPayApprove(pgToken, order);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "카카오 결제 승인 중 오류가 발생했습니다.");
            return;
        }

        try {
            paymentDAO.insertReservation(order);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            SeatDAO seatDAO = new SeatDAO();

            int carNoInt = 0;
            try {
                if (order.getCarNo() != null) {
                    carNoInt = Integer.parseInt(order.getCarNo());
                }
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }

            if (order.getSeatNo() != null && carNoInt > 0) {
                boolean ok = seatDAO.reserveSeat(
                        order.getDriveId(),
                        carNoInt,
                        order.getSeatNo(),
                        order.getCustId()
                );
                if (!ok) {
                    System.err.println("좌석 점유 실패 (이미 예약된 좌석일 가능성)");
                }
            } else {
                System.err.println("좌석 정보가 없어 좌석 점유를 건너뜀");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        session.removeAttribute("pay_order");
        session.removeAttribute("kakaoPaySvc");

     // ⭐️⭐️⭐️ 문제 해결을 위해 추가할 코드: 열차 목록 선택 상태를 유지하던 세션 정보 삭제
        session.removeAttribute("selectedSeat_driveId"); 
        session.removeAttribute("selectedSeat_carNo");
        session.removeAttribute("selectedSeat_no");
        // ⭐️⭐️⭐️
        
        req.setAttribute("order", order);
        req.setAttribute("approveJson", approveJson);

        req.getRequestDispatcher("/WEB-INF/views/payment/kakaoPaySuccess.jsp")
           .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
