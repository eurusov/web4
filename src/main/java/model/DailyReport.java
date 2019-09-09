package model;

import util.DBDate;

import javax.persistence.*;

@Entity
@Table(name = "daily_reports")
public class DailyReport extends SimpleReport {

    @Embedded
    private DBDate date;

    public DailyReport() {
        super();
    }

    public DailyReport(Long earnings, Long soldCars) {
        super(earnings, soldCars);
        this.date = new DBDate();
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
