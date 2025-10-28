package driveinfo;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/DriveInfoList")
public class DriveInfoServlet extends HttpServlet {

    private static final int PAGE_SIZE = 10;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("more".equals(action)) {
            handleAjaxRequest(req, resp);
        } else {
            handleInitialRequest(req, resp);
        }
    }

    private void handleInitialRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String deptStation = req.getParameter("deptName");
        String arriStation = req.getParameter("arriName");
        String startTimeFilter = req.getParameter("startTime");

        if (deptStation == null || arriStation == null ||
            deptStation.trim().isEmpty() || arriStation.trim().isEmpty()) {

            req.getRequestDispatcher("/WEB-INF/views/driveinfo/SearchForm.jsp")
               .forward(req, resp);
            return;
        }

        DriveInfoService service = new DriveInfoService();
        List<DriveInfoResultDTO> list =
                service.getDriveInfosByRoutePaging(
                        deptStation,
                        arriStation,
                        startTimeFilter,
                        0,
                        PAGE_SIZE
                );

        req.setAttribute("dept", deptStation);
        req.setAttribute("arri", arriStation);
        req.setAttribute("timeFilter", startTimeFilter);
        req.setAttribute("list", list);
        req.setAttribute("pageSize", PAGE_SIZE);

        req.getRequestDispatcher("/WEB-INF/views/driveinfo/DriveInfo.jsp")
           .forward(req, resp);
    }

    private void handleAjaxRequest(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String deptStation = req.getParameter("deptName");
        String arriStation = req.getParameter("arriName");
        String startTimeFilter = req.getParameter("startTime");

        int offset = 0;
        try {
            offset = Integer.parseInt(req.getParameter("offset"));
        } catch (NumberFormatException e) {
            offset = 0;
        }

        DriveInfoService service = new DriveInfoService();
        List<DriveInfoResultDTO> list =
                service.getDriveInfosByRoutePaging(
                        deptStation,
                        arriStation,
                        startTimeFilter,
                        offset,
                        PAGE_SIZE
                );

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        String jsonList = gson.toJson(list);

        resp.getWriter().write(jsonList);
    }
}
