package util;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public final class DBDate implements Comparable {

    @Transient
    private static long currentDate;

    private Long date;

    public DBDate() {
        this.date = currentDate;
    }

    public DBDate(Long date) {
        this.date = date;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public static DBDate yesterday() {
        return new DBDate(currentDate - 1L);
    }

    public static void nextDay() {
        currentDate++;
    }

    @Override
    public int compareTo(Object o) {
        return ((DBDate) o).getDate().compareTo(date);
    }

    @Override
    public String toString() {
        return "DBDate{" +
                "date=" + date +
                '}';
    }
}
