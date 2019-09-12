package service;

import DAO.CarDao;
import model.Car;
import model.SimpleCar;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.DBHelper;

import java.util.List;
import java.util.stream.Collectors;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public SimpleCar getCar(long id) {
        Session sess = sessionFactory.openSession();
        Transaction tx = sess.beginTransaction();

        Car car = new CarDao(sess).getCar(id);

        tx.commit();
        sess.close();
        return new SimpleCar(car);
    }

    public List<SimpleCar> getAllCars() {
        Session sess = sessionFactory.openSession();
        Transaction tx = sess.beginTransaction();

        List<Car> simpleCarList =
                new CarDao(sess).getListOf("", "", "", null, false);

        tx.commit();
        sess.close();
        return simpleCarList.stream()
                .map(SimpleCar::new)
                .collect(Collectors.toList());
    }

    public Long addCar(String brand, String model, String licensePlate, Long price) {
        Session sess = sessionFactory.openSession();
        Transaction tx = sess.beginTransaction();

        CarDao carDao = new CarDao(sess);
        Long id = null;
        if (carDao.countOf(brand, "", "", null, false) < 10) {
            id = carDao.addCar(new Car(brand, model, licensePlate, price));
        }

        tx.commit();
        sess.close();
        return id;
    }

    public boolean sellCar(String brand, String model, String licensePlate) {
        Long id = getCarId(brand, model, licensePlate);
        if (id == null) {
            return false;
        }
        sellCar(id);
        return true;
    }

    private void sellCar(Long id) {
        Session sess = sessionFactory.openSession();
        Transaction tx = sess.beginTransaction();

        Car car = new CarDao(sess).getCar(id);
        if (car != null) {
            car.setSold();
        }

        tx.commit();
        sess.close();
    }

    private Long getCarId(String brand, String model, String licensePlate) {
        Session sess = sessionFactory.openSession();
        Transaction tx = sess.beginTransaction();

        Long id = new CarDao(sess)
                .getCarId(brand, model, licensePlate, false);

        tx.commit();
        sess.close();
        return id;
    }
}
