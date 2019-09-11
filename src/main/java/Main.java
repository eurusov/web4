import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlet.*;
import util.DBHelper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws Exception {
        Logger.getLogger("org.hibernate").setLevel(Level.WARNING);
        DBHelper.printConnectInfo();

        /* Creating Servlets*/
        CustomerServlet customerServlet = new CustomerServlet();
        DailyReportServlet dailyReportServlet = new DailyReportServlet();
        ProducerServlet producerServlet = new ProducerServlet();
        NewDayServlet newDayServlet = new NewDayServlet();
        DeleteServlet deleteServlet = new DeleteServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        /* Adding Servlet to context */
        context.addServlet(new ServletHolder(producerServlet), "/producer");
        context.addServlet(new ServletHolder(customerServlet), "/customer");
        context.addServlet(new ServletHolder(dailyReportServlet), "/report/*");
        context.addServlet(new ServletHolder(newDayServlet), "/newday");
        context.addServlet(new ServletHolder(deleteServlet), "/report");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
        server.join();
    }
}
