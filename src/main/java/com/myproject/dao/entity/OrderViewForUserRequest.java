package com.myproject.dao.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;


public class OrderViewForUserRequest implements Serializable {
    private static final long serialUID = 1L;
    private String login;
    private String passport;
    private double receipt;
    private Timestamp fromDate;
    private Timestamp toDate;
    private String withDriver;
    private String name;
    private String carClass;
    private String brand;
    private Order order;
    private Car car;
    private String approved;
    private String feedback;


    public static class OrderViewBuilder {
        private OrderViewForUserRequest orderView;

        public OrderViewBuilder() {
            orderView = new OrderViewForUserRequest();
        }

        public OrderViewBuilder setLogin(String login) {
            orderView.login = login;
            return this;
        }

        public OrderViewBuilder setPassport(String passport) {
            orderView.passport = passport;
            return this;
        }

        public OrderViewBuilder setOrder(Order order) {
            orderView.order = order;
            return this;
        }

        public OrderViewBuilder setCar(Car car) {
            orderView.car = car;
            return this;
        }
        public OrderViewBuilder setApproved(String approved){
            orderView.approved = approved;
            return this;
        }

        public OrderViewBuilder setFeedback(String feedback){
            orderView.feedback = feedback;
            return this;
        }
        public OrderViewBuilder setReceipt(double receipt){
            orderView.receipt = receipt;
            return this;
        }
        public OrderViewBuilder setFromDate(Timestamp fromDate){
            orderView.fromDate = fromDate;
            return this;
        }

        public OrderViewBuilder setToDate(Timestamp toDate){
            orderView.toDate = toDate;
            return this;
        }

        public OrderViewBuilder setWithDriver(String withDriver){
            orderView.withDriver = withDriver;
            return this;
        }
        public OrderViewBuilder setName(String name){
            orderView.name = name;
            return this;
        }

        public OrderViewBuilder setCarClass(String carClass){
            orderView.carClass = carClass;
            return this;
        }

        public OrderViewBuilder setBrand(String brand){
            orderView.brand = brand;
            return this;
        }

        public OrderViewForUserRequest build() {
            return orderView;
        }
    }

    public String getLogin() {
        return login;
    }

    public String getPassport() {
        return passport;
    }

    public double getReceipt() {
        return receipt;
    }

    public Timestamp getFromDate() {
        return fromDate;
    }

    public Timestamp getToDate() {
        return toDate;
    }

    public String getWithDriver() {
        return withDriver;
    }

    public String getName() {
        return name;
    }

    public String getCarClass() {
        return carClass;
    }

    public String getBrand() {
        return brand;
    }

    public Order getOrder() {
        return order;
    }

    public Car getCar() {
        return car;
    }

    public String getApproved() {
        return approved;
    }

    public String getFeedback() {
        return feedback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderViewForUserRequest that = (OrderViewForUserRequest) o;
        return Objects.equals(getLogin(), that.getLogin()) && Objects.equals(getPassport(),
                that.getPassport()) && Objects.equals(getOrder(), that.getOrder()) && Objects.equals(getCar(),
                that.getCar()) && Objects.equals(getApproved(), that.getApproved()) && Objects.equals(getFeedback(),
                that.getFeedback());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getPassport(), getOrder(), getCar(), getApproved(), getFeedback());
    }

    @Override
    public String toString() {
        return "OrderViewForUserRequest{" +
                "user=" + login +
                ", order=" + order +
                ", car=" + car +
                '}';
    }
}
