package payment;

import customer.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/kakaoPayReady")
public class KakaoPayReadyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private PaymentDAO paymentDAO;

    @Override
    public void init() {
        this.paymentDAO = new PaymentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();

        UserDTO user = (UserDTO) session.getAttribute("cust");
        String custId = (user != null && user.getCustId() != null)
                ? user.getCustId()
                : "GUEST";

        String driveIdStr = req.getParameter("driveId");
        if (driveIdStr == null || driveIdStr.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "driveId 파라미터가 없습니다.");
            return;
        }

        long driveId;
        try {
            driveId = Long.parseLong(driveIdStr);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "driveId 형식이 올바르지 않습니다.");
            return;
        }

        String carNo  = req.getParameter("carNo");
        String seatNo = req.getParameter("seatNo");

        PaymentView v = paymentDAO.findDriveForPay(driveId);
        if (v == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "해당 운행 정보를 찾을 수 없습니다.");
            return;
        }

        OrderDTO order = new OrderDTO();
        order.setPartner_order_id("ORDER_" + System.currentTimeMillis());
        order.setPartner_user_id(custId);

        String itemName =
                v.getTrainNo() + " "
              + v.getDeptStationName() + "→" + v.getArriStationName()
              + " (" + v.getDeptTime() + " ~ " + v.getArriTime() + ")";
        order.setItem_name(itemName);

        order.setQuantity("1");
        order.setTotal_amount(String.valueOf(v.getPrice()));

        order.setDriveId((int) driveId);
        order.setCustId(custId);

        order.setCarNo(carNo);
        order.setSeatNo(seatNo);

        String baseUrl =
                req.getScheme() + "://" +
                req.getServerName() +
                ":" + req.getServerPort() +
                req.getContextPath();

        KakaoPay kakaoPaySvc = new KakaoPay();
        String nextRedirectUrl;
        try {
            nextRedirectUrl = kakaoPaySvc.kakaoPayReady(order, baseUrl);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "카카오페이 준비 호출 중 오류가 발생했습니다.");
            return;
        }

        if (nextRedirectUrl == null || nextRedirectUrl.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "카카오페이에서 유효한 redirect URL을 받지 못했습니다.");
            return;
        }

        session.setAttribute("pay_order", order);
        session.setAttribute("kakaoPaySvc", kakaoPaySvc);

        resp.sendRedirect(nextRedirectUrl);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                "POST로만 접근 가능합니다.");
    }
}
