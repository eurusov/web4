package DAO;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
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

    /**
     * Return the persistent instance of the Car class with the given id, or null if there is no such persistent instance.
     *
     * @param id Car id
     * @return a persistent instance or null
     */
    public @Nullable
    Car getCar(long id) {
        return (Car) session.get(Car.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Car> getAllCar() {
        // TODO: Сделать параметризацию sold.
        return session.createQuery("FROM Car WHERE sold=false").list();
    }

    // TODO:  Переделать чтобы принимал Car вместо набора значений полей.
    public Long addCar(String brand, String model, String licensePlate, Long price) throws HibernateException {
        return (Long) session.save(new Car(brand, model, licensePlate, price));
    }

    public void setSold(long id) throws HibernateException {
        Car car = (Car) session.get(Car.class, id);
        car.setSold();
        session.update(car); // TODO:  Проверить, возможно update не нужен, так как car должен быть persistent.
    }

    /**
     * @return the number of cars of this brand in the car dealership that are not sold
     */
    public int ofBrandCount(String brand) {
        // TODO:  Проверить по логам и в дебаге, где здесь реально начинаются транзакции.
        Transaction tx = session.beginTransaction();

        Criteria criteria = session.createCriteria(Car.class);
        criteria.add(Restrictions.eq("brand", brand));
        criteria.add(Restrictions.eq("sold", false));
        criteria.setProjection(Projections.rowCount());
        Object res = criteria.uniqueResult();

        tx.commit();
        session.close();
        return res == null ? 0 : ((Long) res).intValue();
    }

    /**
     * @param sold <tt>true</tt> if the search is carried out among sold cars, otherwise <tt>false</tt>
     * @return the <code>id</code> of a arbitrary car with the specified brand, model, license plate and sold attribute,
     * <p> or <tt>null</tt> if such car is not found
     */
    @Nullable
    public Long getCarId(String brand, String model, String licensePlate, boolean sold) {
        String sql = "SELECT id FROM Car"
                + " WHERE brand=:c_brand"
                + " AND model=:c_model"
                + " AND licensePlate=:c_licensePlate"
                + " AND sold=:c_sold";
        Query query = session.createQuery(sql);
        query.setString("c_brand", brand);
        query.setString("c_model", model);
        query.setString("c_licensePlate", licensePlate);
        query.setParameter("c_sold", sold);

        // TODO: посмотреть что возвращает в случае если ничего не найдено: null или пустой список
        List res = query.list();

        return (res.size() == 0) ? null : (Long) res.get(0);
    }

    /**
     * @return Number of cars sold.
     */
//    @Nullable
//    public Long soldCount() {
//        String countHql = "SELECT COUNT(*) FROM Car WHERE sold=true";
//        return (Long) session.createQuery(countHql).uniqueResult();
//    }
//

    /**
     * @return Total sales of cars.
     */
    @NotNull
    public Long soldAmount() {
        String sumHql = "SELECT SUM(price) FROM Car WHERE sold=true";
        return (Long) session.createQuery(sumHql).uniqueResult();
    }

    /**
     * Remove sold cars from DB.
     *
     * @return The number of deleted entities.
     */
    public int removeSoldCars() {
        String sumHql = "DELETE FROM Car WHERE sold=true";
        return session.createQuery(sumHql).executeUpdate();
    }
}
