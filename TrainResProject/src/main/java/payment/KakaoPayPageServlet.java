package payment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/kakaoPayView")
public class KakaoPayPageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private PaymentDAO paymentDAO;

	@Override
	public void init() {
		this.paymentDAO = new PaymentDAO();
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String driveIdStr = req.getParameter("driveId");
		if (driveIdStr == null || driveIdStr.trim().isEmpty()) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "driveId 가 없습니다.");
			return;
		}

		long driveId;
		try {
			driveId = Long.parseLong(driveIdStr);
		} catch (NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "driveId 형식 오류");
			return;
		}

		String carNo = req.getParameter("carNo");
		String seatNo = req.getParameter("seatNo");

		PaymentView v = paymentDAO.findDriveForPay(driveId);
		if (v == null) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "해당 운행 정보를 찾을 수 없습니다.");
			return;
		}

		req.setAttribute("v", v);

		req.setAttribute("carNo", carNo);
		req.setAttribute("seatNo", seatNo);

		req.getRequestDispatcher("/WEB-INF/views/payment/kakaoPay.jsp").forward(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);

		// /WEB-INF/views/kakaoPay.jsp 로 포워드
		// req.getRequestDispatcher("/WEB-INF/views/payment/kakaoPay.jsp").forward(req,
		// resp);

	}
}
