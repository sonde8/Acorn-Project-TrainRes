package payment;
 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/** 결제 취소 페이지 */
@WebServlet("/kakaoPayCancel")
public class KakaoPayCancelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/kakaoPayCancel.jsp").forward(req, resp);
    }
}
