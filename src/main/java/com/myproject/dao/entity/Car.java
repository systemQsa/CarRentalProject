package com.myproject.dao.entity;

import java.io.Serializable;
import java.util.Objects;

public class Car implements Serializable {
    private static final long serialUID = 567898341290456784L;
    private int carId;
    private String name;
    private String brand;
    private String carClass;
    private double rentalPrice;
    private String photo;

    public static class CarBuilder {
        private final Car car;

        public CarBuilder() {
            car = new Car();
        }

        public CarBuilder setCarId(int carId) {
            car.carId = carId;
            return this;
        }

        public CarBuilder setName(String name) {
            car.name = name;
            return this;
        }

        public CarBuilder setBrand(String brand) {
            car.brand = brand;
            return this;
        }

        public CarBuilder setCarClass(String carClass) {
            car.carClass = carClass;
            return this;
        }
        public CarBuilder setRentalPrice(double rentalPrice){
            car.rentalPrice = rentalPrice;
            return this;
        }
        public CarBuilder setPhoto(String photoPath){
            car.photo = photoPath;
            return this;
        }

        public Car build(){return car;}
    }

    public int getCarId() {
        return carId;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getCarClass() {
        return carClass;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public String getPhoto() {
        return photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return getCarId() == car.getCarId() && Double.compare(car.getRentalPrice(), getRentalPrice()) == 0
                && Objects.equals(getName(), car.getName()) && Objects.equals(getBrand(), car.getBrand())
                && Objects.equals(getCarClass(), car.getCarClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCarId(), getName(), getBrand(), getCarClass(), getRentalPrice());
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", carClass='" + carClass + '\'' +
                ", rentalPrice=" + rentalPrice +
                '}';
    }
}