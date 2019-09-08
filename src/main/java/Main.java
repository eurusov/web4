import model.Car;
import org.hibernate.SessionFactory;
import util.DBException;
import util.DBHelper;

public class Main {

    public static void main(String[] args) {
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

            Car car = dbService.getCar(carId);
            System.out.println("Car data set: " + car);

            sessionFactory.close();
        } catch (DBException e) {
            e.printStackTrace();
        }

    }
}
