package servlet;

import service.DailyReportService;
import util.ServletHelper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DailyReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getPathInfo().contains("all")) {
            ServletHelper.writeJsonToResponse(DailyReportService.getInstance().getAllDailyReports(), resp);
        } else if (req.getPathInfo().contains("last")) {
            ServletHelper.writeJsonToResponse(DailyReportService.getInstance().getLastReport(), resp);
        }
    }
}
