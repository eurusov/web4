package DAO;

import model.Car;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public Car get(long id) throws HibernateException {
        return (Car) session.get(Car.class, id);
    }

    public void setSold(long id) throws HibernateException {
        Transaction transaction = session.beginTransaction();
        Car car = (Car) session.get(Car.class, id);
        car.setSold();
        session.update(car);
        transaction.commit();
        session.close();
    }

    public long insertCar(String brand, String model, String licensePlate, Long price) throws HibernateException {
        return (Long) session.save(new Car(brand, model, licensePlate, price));
    }

    public List<Car> getAllCar() {
        Transaction transaction = session.beginTransaction();
        List Cars = session.createQuery("FROM Car").list();
        transaction.commit();
        session.close();
        return Cars;
    }

    public int ofBrandCount(String brand) {
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Car.class);
        criteria.add(Restrictions.eq("brand", brand));
        criteria.setProjection(Projections.rowCount());
//        List Cars = session.createQuery("select FROM Car").list();
        transaction.commit();
        int res;
        if (criteria.uniqueResult() == null) {
            res = 0;
        } else {
            res = ((Long) criteria.uniqueResult()).intValue();
        }
        session.close();
        return res;
    }
}
