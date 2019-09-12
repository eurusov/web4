package servlet;

import service.CarService;
import util.ServletHelper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletHelper.writeJsonToResponse(CarService.getInstance().getAllCars(), resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");

        CarService carService = CarService.getInstance();
        boolean sold = carService.sellCar(brand, model, licensePlate);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println("");
        resp.flushBuffer();
    }
}
