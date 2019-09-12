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
    private Long id;

    @Column(nullable = false)
    private Long earnings;

    @Column(nullable = false)
    private Long soldCars;

    /* needed by Hibernate */
    SimpleReport() {
    }

    SimpleReport(final Long earnings, final Long soldCars) {
        this.earnings = earnings;
        this.soldCars = soldCars;
    }

    /**
     * Copying constructor. Intended for create SimpleReport from DailyReport, for transfer to GSON.
     */
    public SimpleReport(final SimpleReport report) {
        id = report.id;
        earnings = report.earnings;
        soldCars = report.soldCars;
    }

    Long getId() {
        return id;
    }

    Long getEarnings() {
        return earnings;
    }

    Long getSoldCars() {
        return soldCars;
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
