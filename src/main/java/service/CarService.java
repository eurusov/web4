package service;

import DAO.CarDao;
import model.Car;
import model.SimpleCar;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import util.DBException;
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

//    public List<Car> getAllCars() {
//        return new CarDao(sessionFactory.openSession()).getAllCar();
//    }

    public SimpleCar getCar(long id) throws DBException {
        Car car = new CarDao(sessionFactory.openSession()).getCar(id);
        return new SimpleCar(car);
    }

    public List<SimpleCar> getAllCars() {
        return new CarDao(sessionFactory.openSession()).getAllCar()
                .stream()
                .filter(e -> !e.isSold())
                .map(SimpleCar::new)
                .collect(Collectors.toList());
    }

    public Long addCar(String brand, String model, String licensePlate, Long price) {
        if (isBrandFull(brand)) {
            return null;
        }
        return new CarDao(sessionFactory.openSession())
                .addCar(brand, model, licensePlate, price);
    }

    public void sellCar(Long id) {
        new CarDao(sessionFactory.openSession()).setSold(id);
    }

    private boolean isBrandFull(String brand) {
        return ofBrandCount(brand) >= 10;
    }

    private int ofBrandCount(String brand) {
        return new CarDao(sessionFactory.openSession()).ofBrandCount(brand);
    }
}
