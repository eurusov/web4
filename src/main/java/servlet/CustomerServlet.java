package servlet;

import service.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletHelper.writeJsonToResponse(CarService.getInstance().getAllCars(), resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");

        CarService carService = CarService.getInstance();
        boolean sold = carService.sellCar(brand, model, licensePlate);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println("");
        resp.flushBuffer();
    }

    void printMap(Map<String, String[]> map) {
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String k = entry.getKey();
            String[] v = entry.getValue();
            System.out.print(k);
            System.out.println(Arrays.toString(v));
        }
    }

    void printMap2(Map<String, String[]> map) {
        System.out.println(map.size());
        for (String k : map.keySet()) {
            String[] v = map.get(k);
            System.out.print(k + " = ");
            System.out.println(Arrays.toString(v));
        }
    }

}
