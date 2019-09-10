package DAO;

import model.Car;
import org.hibernate.*;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public Car getCar(long id) throws HibernateException {
        Transaction tx = session.beginTransaction();

        Car car = (Car) session.get(Car.class, id);

        tx.commit();
        session.close();
        return car;
    }

    @SuppressWarnings("unchecked")
    public List<Car> getAllCar() {
        Transaction tx = session.beginTransaction();

        // TODO: Сделать чтобы выбирались только не проданные машины, возможно в отдельном методе, и через Criteria.
        List Cars = session.createQuery("FROM Car").list();

        tx.commit();
        session.close();
        return Cars;
    }

    public void setSold(long id) throws HibernateException {
        Transaction tx = session.beginTransaction();

        Car car = (Car) session.get(Car.class, id);
        car.setSold();
        session.update(car); // TODO:  Проверить, возможно update не нужен, так как car должен быть persistent.

        tx.commit();
        session.close();
    }

    // TODO:  Переделать чтобы принимал Car вместо набора значений полей.
    public Long addCar(String brand, String model, String licensePlate, Long price) throws HibernateException {
        Transaction tx = session.beginTransaction();

        Long id = (Long) session.save(new Car(brand, model, licensePlate, price));

        tx.commit();
        session.close();
        return id;
    }

    public int ofBrandCount(String brand) {
        // TODO:  Проверить по логам и в дебаге, где здесь реально начинаются транзакции.
        Criteria criteria = session.createCriteria(Car.class);
        criteria.add(Restrictions.eq("brand", brand));
        criteria.add(Restrictions.eq("sold", false));
        criteria.setProjection(Projections.rowCount());

        Transaction tx = session.beginTransaction();

        Object res = criteria.uniqueResult();

        tx.commit();
        session.close();
        return res == null ? 0 : ((Long) res).intValue();
    }

    public Long getCarId(String brand, String model, String licensePlate, boolean sold) {
        String sql = "SELECT id FROM Car WHERE brand=:c_brand AND model=:c_model AND licensePlate=:c_licensePlate AND sold=:c_sold";

        Transaction tx = session.beginTransaction();

        Query query = session.createQuery(sql);
        query.setString("c_brand", brand);
        query.setString("c_model", model);
        query.setString("c_licensePlate", licensePlate);
        query.setParameter("c_sold", sold);

        List res = query.list();

        tx.commit();
        session.close();

        return (res.size() == 0) ? null : (Long) res.get(0);
    }
}
