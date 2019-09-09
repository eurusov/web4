package DAO;

import model.Car;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public Car get(long id) throws HibernateException {
        return (Car) session.get(Car.class, id);
    }

    public long insertCar(String brand, String model, String licensePlate, Long price) throws HibernateException {
        return (Long) session.save(new Car(brand, model, licensePlate, price));
    }
    public List<Car> getAllCar() {
        Transaction transaction = session.beginTransaction();
        List<Car> Cars = session.createQuery("FROM Car").list();
        transaction.commit();
        session.close();
        return Cars;
    }

}
