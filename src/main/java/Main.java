import model.Car;
import model.DailyReport;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.hibernate.SessionFactory;
import service.CarService;
import service.DailyReportService;
import servlet.CustomerServlet;
import servlet.DailyReportServlet;
import util.DBDate;
import util.DBException;
import util.DBHelper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws Exception {
        Logger.getLogger("org.hibernate").setLevel(Level.WARNING);
        DBHelper dbService = new DBHelper();
        SessionFactory sessionFactory = DBHelper.getSessionFactory();
        DBHelper.printConnectInfo();

        try {
            long carId = dbService.addCar(
                    "Honda",
                    "Acura TLX",
                    "BD51 SMR",
                    45000L);
            System.out.println("Added car id: " + carId);

            carId = dbService.addCar(
                    "Toyota",
                    "Supra",
                    "BND007",
                    55000L);
            System.out.println("Added car id: " + carId);


            Car car = dbService.getCar(carId);
            System.out.println("Car data set: " + car);
            System.out.println();

            dbService.addDailyReport(123000L, 16L);
            DBDate.nextDay();
            long repId = dbService.addDailyReport(3000L, 10L);
            System.out.println("Added report id: " + repId);
            DailyReport report = dbService.getDailyReport(repId);
            System.out.println("Report data set: " + report);
            System.out.println();

            CarService carSrv = CarService.getInstance();
            System.out.println(carSrv.getAllCars());

            DailyReportService drSrv = DailyReportService.getInstance();
            System.out.println(drSrv.getAllDailyReports());
            System.out.println(drSrv.getLastReport());

//            sessionFactory.close();
        } catch (DBException e) {
            e.printStackTrace();
        }

        CustomerServlet customerServlet = new CustomerServlet();
        DailyReportServlet dailyReportServlet = new DailyReportServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(customerServlet), "/customer");
        context.addServlet(new ServletHolder(dailyReportServlet), "/report/*");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
        server.join();
    }
}
