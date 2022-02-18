package com.myproject.dao.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class Order {
    private int orderId;
    private int userId;
    private int carId;
    private String passport;
    private char withDriver;
    private Timestamp fromDate;
    private Timestamp toDate;
    private BigDecimal receipt;

    public static class OrderBuilder {
        private final Order order;

        public OrderBuilder() {
            order = new Order();
        }

        public OrderBuilder setOrderId(int orderId) {
            order.orderId = orderId;
            return this;
        }

        public OrderBuilder setUserId(int userId) {
            order.userId = userId;
            return this;
        }

        public OrderBuilder setCar(int car) {
            order.carId = car;
            return this;
        }

        public OrderBuilder setPassport(String passport) {
            order.passport = passport;
            return this;
        }

        public OrderBuilder setWithDriver(char withDriver) {
            order.withDriver = withDriver;
            return this;
        }

        public OrderBuilder setFromDate(Timestamp fromDate) {
            order.fromDate = fromDate;
            return this;
        }

        public OrderBuilder setToDate(Timestamp toDate) {
            order.toDate = toDate;
            return this;
        }

        public OrderBuilder setReceipt(BigDecimal receipt) {
            order.receipt = receipt;
            return this;
        }

        public Order build() {
            return order;
        }
    }

    public long getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public int getCarId() {
        return carId;
    }

    public String getPassport() {
        return passport;
    }

    public char getWithDriver() {
        return withDriver;
    }

    public Timestamp getFromDate() {
        return fromDate;
    }

    public Timestamp getToDate() {
        return toDate;
    }

    public BigDecimal getReceipt() {
        return receipt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getOrderId() == order.getOrderId() && getWithDriver() == order.getWithDriver()
                && Objects.equals(getUserId(), order.getUserId()) && Objects.equals(getCarId(), order.getCarId())
                && Objects.equals(getPassport(), order.getPassport()) && Objects.equals(getFromDate(), order.getFromDate())
                && Objects.equals(getToDate(), order.getToDate()) && Objects.equals(getReceipt(), order.getReceipt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderId(), getUserId(), getCarId(), getPassport(),
                getWithDriver(), getFromDate(), getToDate(), getReceipt());
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", user=" + userId +
                ", car=" + carId +
                ", passport='" + passport + '\'' +
                ", withDriver=" + withDriver +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", receipt=" + receipt +
                '}';
    }
}
