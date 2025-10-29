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
        // UserDTO user = (UserDTO) session.getAttribute("cust");
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
                int resId = Integer.parseInt(req.getParameter("resId"));
                Reservation resInfo = rdao.findByResId(resId);

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
