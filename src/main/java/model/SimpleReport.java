package model;

import javax.persistence.*;

/**
 * SimpleReport class is @MappedSuperClass for DailyReport class.
 * It is designed to simplify sending JSON reports without a date.
 */
@MappedSuperclass
public class SimpleReport {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column
    protected Long earnings;

    @Column
    protected Long soldCars;

    public SimpleReport() {
    }

    public SimpleReport(Long earnings, Long soldCars) {
        this.earnings = earnings;
        this.soldCars = soldCars;
    }

    public SimpleReport(SimpleReport report) {
        id = report.id;
        earnings = report.earnings;
        soldCars = report.soldCars;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEarnings() {
        return earnings;
    }

    public void setEarnings(Long earnings) {
        this.earnings = earnings;
    }

    public Long getSoldCars() {
        return soldCars;
    }

    public void setSoldCars(Long soldCars) {
        this.soldCars = soldCars;
    }

    @Override
    public String toString() {
        return "SimpleReport{" +
                "id=" + id +
                ", earnings=" + earnings +
                ", soldCars=" + soldCars +
                '}';
    }
}
