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

    public SimpleCar() {
    }

    public SimpleCar(String brand, String model, String licensePlate, Long price) {
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.price = price;
    }

    public SimpleCar(Car car) {
        id = car.getId();
        brand = car.getBrand();
        model = car.getModel();
        licensePlate = car.getLicensePlate();
        price = car.getPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
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
