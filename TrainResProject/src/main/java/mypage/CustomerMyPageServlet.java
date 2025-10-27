package mypage;

import java.io.IOException;
import java.util.List;

import javax.servlet.*;
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
        Customer cust = (Customer) (session != null ? session.getAttribute("cust") : null);

        if (cust == null) {
            resp.sendRedirect(req.getContextPath() + "/customer/login");
            return;
        }

        String path = req.getServletPath();
        String view = "/WEB-INF/views/mypage/mypage.jsp";

        switch (path) {
            case "/mypage":
                Customer customer = cdao.findById(cust.getCustId());
                List<Reservation> reservations = rdao.findByCustomerId(cust.getCustId());
                req.setAttribute("customer", customer);
                req.setAttribute("reservations", reservations);
                view = "/WEB-INF/views/mypage/mypage.jsp";
                break;

            case "/mypage/resdetail":
                int resId = Integer.parseInt(req.getParameter("resId"));
                Reservation res = rdao.findByResId(resId);
                req.setAttribute("reservation", res);
                view = "/WEB-INF/views/mypage/mypage_detail.jsp";
                break;

            case "/mypage/payments":
                List<PaymentView> payments = pdao.findByCustomerId(cust.getCustId());
                req.setAttribute("payments", payments);
                view = "/WEB-INF/views/mypage/mypage_payments.jsp";
                break;

            case "/mypage/edit":
                req.setAttribute("customer", cdao.findById(cust.getCustId()));
                view = "/WEB-INF/views/mypage/mypage_edit.jsp";
                break;
        }

        req.getRequestDispatcher(view).forward(req, resp);
    }
}
