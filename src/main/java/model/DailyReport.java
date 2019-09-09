package model;

import util.DBDate;

import javax.persistence.*;

@Entity
@Table(name = "daily_reports")
public class DailyReport {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "earnings")
    private Long earnings;

    @Column(name = "soldCars")
    private Long soldCars;

    @Embedded
    @AttributeOverride(name = "date", column = @Column)
    private DBDate date;

    public DailyReport() {
    }

    public DailyReport(Long earnings, Long soldCars) {
        this.earnings = earnings;
        this.soldCars = soldCars;
        this.date = new DBDate();
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

    public DBDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "DailyReport{" +
                "id=" + id +
                ", earnings=" + earnings +
                ", soldCars=" + soldCars +
                ", date=" + date +
                '}';
    }
}
