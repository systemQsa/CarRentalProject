package com.myproject.dao.entity;

import java.io.Serializable;
import java.util.Objects;

public class Driver implements Serializable {
    private static final long serialUID = 23463745783490234L;
    private int id;
    private String name;
    private String surname;
    private String phone;
    private double rentPrice;

    public static class DriverBuilder {
        private Driver driver;

        public DriverBuilder() {
            driver = new Driver();
        }

        public DriverBuilder setDriverId(int id) {
            driver.id = id;
            return this;
        }

        public DriverBuilder setName(String name) {
            driver.name = name;
            return this;
        }

        public DriverBuilder setSurname(String surname) {
            driver.surname = surname;
            return this;
        }

        public DriverBuilder setPhone(String phone) {
            driver.phone = phone;
            return this;
        }

        public DriverBuilder setRentPrice(double rentPrice) {
            driver.rentPrice = rentPrice;
            return this;
        }
        public Driver build(){return driver;}
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(double rentPrice) {
        this.rentPrice = rentPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return id == driver.id && Double.compare(driver.rentPrice, rentPrice) == 0
                && Objects.equals(name, driver.name) && Objects.equals(surname, driver.surname)
                && Objects.equals(phone, driver.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, phone, rentPrice);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", rentPrice=" + rentPrice +
                '}';
    }
}
