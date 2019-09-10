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
        System.out.println("Car was added with id = " + carId);
//        carService.sellCar(carId); // mark this car as sold
        SimpleCar car = carService.getCar(carId);
        System.out.println(car);

        carId = carService.addCar(
                "Toyota",
                "Supra",
                "BND007",
                55000L
        );
        System.out.println("Car was added with id = " + carId);
        car = carService.getCar(carId);
        System.out.println(car);
        System.out.println();


        Long repId = dailyReportService.addDailyReport(123000L, 16L);
        System.out.println("DailyReport was added with id = " + repId);
        SimpleReport simpleReport = dailyReportService.getDailyReport(repId);
        System.out.println("getDailyReport by this id returns: " + simpleReport);

        DBDate.nextDay();
        System.out.println("\nDate was changed.\n");

        repId = dailyReportService.addDailyReport(3000L, 10L);
        System.out.println("DailyReport was added with id = " + repId);
        simpleReport = dailyReportService.getDailyReport(repId);
        System.out.println("getDailyReport by this id returns: " + simpleReport);

        System.out.println("\nAll not sold cars list:\n" + carService.getAllCars());
        System.out.println("\nAll reports list:\n" + dailyReportService.getAllDailyReports());
        System.out.println("\nLast report:\n" + dailyReportService.getLastReport());
        System.out.println();

//        DBHelper.deleteAll();

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
