package model;

import javax.persistence.Embeddable;

@Embeddable
public final class VirtualDate implements Comparable {

    private static long currentVirtualDateValue;

    private Long value;

    /* needed by Hibernate */
    private VirtualDate() {
        this.value = currentVirtualDateValue;
    }

    private VirtualDate(Long value) {
        this.value = value;
    }

    private Long getLongValue() {
        return value;
    }

    static VirtualDate getTodayDate() {
        return new VirtualDate();
    }

    public static VirtualDate getYesterdayDate() {
        return new VirtualDate(currentVirtualDateValue - 1L);
    }

    public static void nextDayHasCome() {
        currentVirtualDateValue++;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        if (o.getClass() != VirtualDate.class) {
            throw new ClassCastException();
        }
        return ((VirtualDate) o).getLongValue().compareTo(value);
    }

    @Override
    public String toString() {
        return "DBDate{" +
                "date=" + value +
                '}';
    }
}
