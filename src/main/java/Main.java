import model.DailyReport;
import model.SimpleCar;
import model.SimpleReport;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import service.CarService;
import service.DailyReportService;
import servlet.CustomerServlet;
import servlet.DailyReportServlet;
import servlet.ProducerServlet;
import util.DBDate;
import util.DBException;
import util.DBHelper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws Exception {
        Logger.getLogger("org.hibernate").setLevel(Level.WARNING);
//        DBHelper.getSessionFactory();
        DBHelper.printConnectInfo();

        CarService carService = CarService.getInstance();
        DailyReportService dailyReportService = DailyReportService.getInstance();

        Long carId = carService.addCar(
                "Honda",
                "Acura TLX",
                "BD51 SMR",
                45000L
        );
        System.out.println("id of added car: " + carId);
        carService.sellCar(carId); // mark this car as sold
        SimpleCar car = carService.getCar(carId);
        System.out.println(car);

        carId = carService.addCar(
                "Toyota",
                "Supra",
                "BND007",
                55000L
        );
        System.out.println("id of added car: " + carId);
        SimpleCar car2 = carService.getCar(carId);
        System.out.println(car2);
        System.out.println();

        System.exit(0);

        dailyReportService.addDailyReport(123000L, 16L);
        DBDate.nextDay();
        long repId = dailyReportService.addDailyReport(3000L, 10L);
        System.out.println("Added report id: " + repId);
        SimpleReport report = dailyReportService.getDailyReport(repId);
        System.out.println("Report data set: " + report);
        System.out.println();

        System.out.println(carService.getAllCars());

        DailyReportService drSrv = DailyReportService.getInstance();
        System.out.println(drSrv.getAllDailyReports());
        System.out.println(drSrv.getLastReport());

        DBHelper.deleteAll();

//            sessionFactory.close();

        CustomerServlet customerServlet = new CustomerServlet();
        DailyReportServlet dailyReportServlet = new DailyReportServlet();
        ProducerServlet producerServlet = new ProducerServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(producerServlet), "/producer");
        context.addServlet(new ServletHolder(customerServlet), "/customer");
        context.addServlet(new ServletHolder(dailyReportServlet), "/report/*");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
        server.join();
    }
}
