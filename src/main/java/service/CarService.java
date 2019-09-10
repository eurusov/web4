package service;

import DAO.CarDao;
import model.SimpleCar;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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

    public List<SimpleCar> getAllCars() {
        return new CarDao(sessionFactory.openSession()).getAllCar()
                .stream()
                .filter(e -> !e.isSold())
                .map(SimpleCar::new)
                .collect(Collectors.toList());
    }

    public void setSoldCar(Long id) {
        CarDao dao = new CarDao(sessionFactory.openSession());
        dao.setSold(id);
    }

    public boolean canAccept(String brand) {
        CarDao dao = new CarDao(sessionFactory.openSession());
        int cnt = dao.ofBrandCount(brand);
        return cnt < 10;
    }

    public boolean addCar(String brand, String model, String licensePlate, Long price) throws DBException {
        if (!canAccept(brand)) {
            return false;
        }
        DBHelper dbService = new DBHelper();
        dbService.addCar(brand, model, licensePlate, price);
        return true;
    }
}
