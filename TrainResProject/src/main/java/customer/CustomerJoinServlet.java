package customer;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/join")
public class CustomerJoinServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/views/customer/join.jsp")
           .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String custId   = req.getParameter("cust_id");
        String name     = req.getParameter("name");
        String birthStr = req.getParameter("birth");
        String password = req.getParameter("password");

        System.out.println(custId);
        System.out.println(name);
        System.out.println(birthStr);
        System.out.println(password);

        Date birthDate = null;
        try {
            LocalDate localDate = LocalDate.parse(birthStr); // yyyy-MM-dd
            birthDate = Date.valueOf(localDate);
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", "생년월일 형식이 올바르지 않습니다.");
            req.getRequestDispatcher("/WEB-INF/views/customer/join.jsp").forward(req, resp);
            return;
        }

        CustomerDAO dao = new CustomerDAO();
        Customer newUser = new Customer(custId, name, birthDate, password);

        int result = dao.join(newUser);

        if (result > 0) {
            resp.sendRedirect("login?msg=joined");
        } else {
            req.setAttribute("error", "회원가입에 실패했습니다. (ID 중복 등)");
            req.getRequestDispatcher("/WEB-INF/views/customer/join.jsp").forward(req, resp);
        }
    }
}
