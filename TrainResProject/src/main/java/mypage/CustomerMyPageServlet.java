package mypage;

import customer.UserDTO;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import customer.Customer;
import customer.CustomerDAO;
import reservation.Reservation;
import reservation.ReservationDAO;
import payment.PaymentDAO;
import payment.PaymentView;

@WebServlet({"/mypage", "/mypage/resdetail", "/mypage/payments", "/mypage/edit"})
public class CustomerMyPageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final CustomerDAO cdao = new CustomerDAO();
    private final ReservationDAO rdao = new ReservationDAO();
    private final PaymentDAO pdao = new PaymentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        UserDTO user = (session != null) ? (UserDTO) session.getAttribute("cust") : null;

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String path = req.getServletPath();
        String view = "/WEB-INF/views/mypage/mypage.jsp";

        switch (path) {
            case "/mypage": {
                Customer fresh = cdao.findById(user.getCustId());
                List<Reservation> reservations = rdao.findByCustomerId(user.getCustId());
                req.setAttribute("customer", fresh);
                req.setAttribute("reservations", reservations);
                view = "/WEB-INF/views/mypage/mypage.jsp";
                break;
            }
            case "/mypage/resdetail": {
                String resIdStr = req.getParameter("resId");
                if (resIdStr == null || resIdStr.isBlank()) {
                    req.setAttribute("error", "잘못된 접근입니다. 예약번호가 없습니다.");
                    view = "/WEB-INF/views/mypage/mypage.jsp";
                    break;
                }
                int resId;
                try {
                    resId = Integer.parseInt(resIdStr);
                } catch (NumberFormatException e) {
                    req.setAttribute("error", "예약번호 형식이 올바르지 않습니다.");
                    view = "/WEB-INF/views/mypage/mypage.jsp";
                    break;
                }
                Reservation resInfo = rdao.findByResId(resId);
                if (resInfo == null) {
                    req.setAttribute("error", "해당 예약을 찾을 수 없습니다.");
                    view = "/WEB-INF/views/mypage/mypage.jsp";
                    break;
                }
                req.setAttribute("reservation", resInfo);
                view = "/WEB-INF/views/mypage/mypage_detail.jsp";
                break;
            }
            case "/mypage/payments": {
                List<PaymentView> payments = pdao.findByCustomerId(user.getCustId());
                req.setAttribute("payments", payments);
                view = "/WEB-INF/views/mypage/mypage_payments.jsp";
                break;
            }
            case "/mypage/edit": {
                Customer fresh = cdao.findById(user.getCustId());
                req.setAttribute("customer", fresh);
                view = "/WEB-INF/views/mypage/mypage_edit.jsp";
                break;
            }
        }

        RequestDispatcher rd = req.getRequestDispatcher(view);
        rd.forward(req, resp);
    }
}
