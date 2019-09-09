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

    public DailyReport(final Long earnings, final Long soldCars) {
        super(earnings, soldCars);
        this.date = new DBDate();
    }

    public DBDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "DailyReport{" +
                "id=" + super.getId() +
                ", earnings=" + super.getEarnings() +
                ", soldCars=" + super.getSoldCars() +
                ", date=" + date +
                '}';
    }
}
