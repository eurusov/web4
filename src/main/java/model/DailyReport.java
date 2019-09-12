package model;

import javax.persistence.*;

@Entity
@Table(name = "daily_reports")
public class DailyReport extends SimpleReport {

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "date", nullable = false, unique = true))
    private VirtualDate date;

    /* needed by Hibernate */
    public DailyReport() {
        super();
    }

    public DailyReport(final Long earnings, final Long soldCars) {
        super(earnings, soldCars);
        this.date = VirtualDate.getTodayDate();
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
