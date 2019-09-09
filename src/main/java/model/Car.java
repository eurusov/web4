package model;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car extends SimpleCar {

    @Column
    private boolean sold;

    public Car() {
        super();
    }

    public Car(String brand, String model, String licensePlate, Long price) {
        super(brand, model, licensePlate, price);
        sold = false;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold() {
        this.sold = true;
    }

    @Override
    public String toString() {
        return super.toString() + " Car{" +
                "sold=" + sold +
                "}";
    }
}
