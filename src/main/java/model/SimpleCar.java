package model;

import javax.persistence.*;

@MappedSuperclass
public class SimpleCar {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String brand;

    @Column
    private String model;

    @Column
    private String licensePlate;

    @Column
    private Long price;

    /* needed by Hibernate */
    SimpleCar() {
    }

    SimpleCar(String brand, String model, String licensePlate, Long price) {
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.price = price;
    }

    /**
     * Copying constructor. Intended for create SimpleCar from Car, for transfer to GSON.
     */
    public SimpleCar(SimpleCar car) {
        id = car.id;
        brand = car.brand;
        model = car.model;
        licensePlate = car.licensePlate;
        price = car.price;
    }

    @Override
    public String toString() {
        return "SimpleCar{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", price=" + price +
                '}';
    }
}
