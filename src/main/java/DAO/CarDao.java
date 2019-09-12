package DAO;

import com.sun.istack.internal.Nullable;
import model.Car;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
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
    @Nullable
    public Car getCar(long id) {
        return (Car) session.get(Car.class, id);
    }

    /**
     * @param sold <tt>true</tt> if the search is carried out among sold cars, otherwise <tt>false</tt>
     * @return the <code>id</code> of a arbitrary car with the specified brand, model, license plate and sold attribute,
     * <p> or <tt>null</tt> if such car is not found
     */
    @Nullable
    public Long getCarId(String brand, String model, String licensePlate, boolean sold) {
        String hql = "SELECT id FROM Car"
                + " WHERE brand=:c_brand"
                + " AND model=:c_model"
                + " AND licensePlate=:c_licensePlate"
                + " AND sold=:c_sold";
        Query query = session.createQuery(hql);
        query.setString("c_brand", brand);
        query.setString("c_model", model);
        query.setString("c_licensePlate", licensePlate);
        query.setParameter("c_sold", sold);

        // TODO: посмотреть что возвращает в случае если ничего не найдено: null или пустой список
        List res = query.list();

        return (res.size() == 0) ? null : (Long) res.get(0);
    }

    public Long addCar(Car car) {
        return (Long) session.save(car);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public List<Car> getListOf(
            String brand,
            String model,
            String licensePlate,
            Long price,
            Boolean sold
    ) {
        Criteria criteria = getCriteria(brand, model, licensePlate, price, sold);
        return criteria.list();
    }

    @Nullable
    public Long countOf(
            String brand,
            String model,
            String licensePlate,
            Long price,
            Boolean sold
    ) {
        Criteria criteria = getCriteria(brand, model, licensePlate, price, sold);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    /**
     * @return Total sales of cars.
     */
    @Nullable
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
        String delHql = "DELETE FROM Car WHERE sold=true";
        return session.createQuery(delHql).executeUpdate();
    }

    private Criteria getCriteria(
            String brand,
            String model,
            String licensePlate,
            Long price,
            Boolean sold
    ) {
        Criteria criteria = session.createCriteria(Car.class);
        if (!"".equals(brand)) {
            criteria.add(Restrictions.eq("brand", brand));
        }
        if (!"".equals(model)) {
            criteria.add(Restrictions.eq("model", model));
        }
        if (!"".equals(licensePlate)) {
            criteria.add(Restrictions.eq("licensePlate", licensePlate));
        }
        if (price != null) {
            criteria.add(Restrictions.eq("price", price));
        }
        if (sold != null) {
            criteria.add(Restrictions.eq("sold", sold));
        }
        return criteria;
    }
}
