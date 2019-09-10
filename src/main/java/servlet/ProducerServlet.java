package servlet;

import service.CarService;
import util.DBException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ProducerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, String[]> params = req.getParameterMap();
        CarService carService = CarService.getInstance();
        boolean accepted = false;
        try {
            accepted = carService.addCar(
                    params.get("brand")[0],
                    params.get("model")[0],
                    params.get("licensePlate")[0],
                    Long.valueOf(params.get("price")[0])
            );
        } catch (DBException ignored) {
        }
        resp.setStatus(accepted ? HttpServletResponse.SC_OK : HttpServletResponse.SC_FORBIDDEN);
        resp.getWriter().println("");
        resp.flushBuffer();
    }
}
