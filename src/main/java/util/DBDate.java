package util;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public final class DBDate implements Comparable {

    @Transient
    private static long NOW;

    private Long date;

    public DBDate() {
        this.date = NOW;
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

    public static void nextDay() {
        NOW++;
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
