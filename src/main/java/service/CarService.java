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

        List<SimpleCar> simpleCarList =
                new CarDao(sess).getAllCar()
                        .stream()
                        .filter(e -> !e.isSold())
                        .map(SimpleCar::new)
                        .collect(Collectors.toList());
        tx.commit();
        sess.close();
        return simpleCarList;
    }

    public Long addCar(String brand, String model, String licensePlate, Long price) {
        if (isBrandFull(brand)) {
            return null;
        }
        Session sess = sessionFactory.openSession();
        Transaction tx = sess.beginTransaction();

        Long id = new CarDao(sess)
                .addCar(brand, model, licensePlate, price);

        tx.commit();
        sess.close();
        return id;
    }

    public void sellCar(Long id) {
        Session sess = sessionFactory.openSession();
        Transaction tx = sess.beginTransaction();

        new CarDao(sess).setSold(id);

        tx.commit();
        sess.close();
    }

    public Long getCarId(String brand, String model, String licensePlate) {
        Session sess = sessionFactory.openSession();
        Transaction tx = sess.beginTransaction();

        Long id = new CarDao(sess)
                .getCarId(brand, model, licensePlate, false);

        tx.commit();
        sess.close();
        return id;
    }

    private boolean isBrandFull(String brand) {
        return ofBrandCount(brand) >= 10;
    }

    private int ofBrandCount(String brand) {
        return new CarDao(sessionFactory.openSession()).ofBrandCount(brand);
    }

    public boolean sellCar(String brand, String model, String licensePlate) {
        Long id = getCarId(brand, model, licensePlate);
        if (id == null) {
            return false;
        }
        sellCar(id);
        return true;
    }
}
