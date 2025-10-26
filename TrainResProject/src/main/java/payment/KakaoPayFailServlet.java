package payment;
 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/** 결제 실패 페이지 */
@WebServlet("/kakaoPaySuccessFail")
public class KakaoPayFailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/kakaoPaySuccessFail.jsp").forward(req, resp);
    }
}
