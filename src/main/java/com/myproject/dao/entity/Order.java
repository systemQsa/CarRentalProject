package com.myproject.dao.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

public class Order implements Serializable {
    private static final long serialUID = 1345687982345674L;
    private long orderId;
    private long userId;
    private int carId;
    private String passport;
    private String withDriver;
    private String fromDate;
    private String toDate;
    private double receipt;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private String userLogin;


    public static class OrderBuilder {
        private final Order order;

        public OrderBuilder() {
            order = new Order();
        }


        public OrderBuilder setUserId(long userId) {
            order.userId = userId;
            return this;
        }

        public OrderBuilder setUserLogin(String userLogin) {
            order.userLogin = userLogin;
            return this;
        }

        public OrderBuilder setCar(int car) {
            order.carId = car;
            return this;
        }

        public OrderBuilder setDateFrom(LocalDateTime dateFrom) {
            order.dateFrom = dateFrom;
            return this;
        }

        public OrderBuilder setDateTo(LocalDateTime dateTo) {
            order.dateTo = dateTo;
            return this;
        }

        public OrderBuilder setPassport(String passport) {
            order.passport = passport;
            return this;
        }

        public OrderBuilder setWithDriver(String withDriver) {
            order.withDriver = withDriver;
            return this;
        }

        public OrderBuilder setFromDate(String fromDate) {
            order.fromDate = fromDate;
            return this;
        }

        public OrderBuilder setToDate(String toDate) {
            order.toDate = toDate;
            return this;
        }

        public OrderBuilder setReceipt(double receipt) {
            order.receipt = receipt;
            return this;
        }

        public Order build() {
            return order;
        }
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getUserId() {
        return userId;
    }

    public int getCarId() {
        return carId;
    }

    public String getPassport() {
        return passport;
    }

    public String getWithDriver() {
        return withDriver;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public double getReceipt() {
        return receipt;
    }

    public String getUserLogin() {
        return userLogin;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getUserId() == order.getUserId() && getCarId() == order.getCarId()
                && Double.compare(order.getReceipt(), getReceipt()) == 0 && Objects.equals(getPassport(), order.getPassport())
                && Objects.equals(getWithDriver(), order.getWithDriver()) && Objects.equals(getDateFrom(), order.getDateFrom())
                && Objects.equals(getDateTo(), order.getDateTo()) && Objects.equals(getUserLogin(), order.getUserLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getCarId(), getPassport(), getWithDriver(), getFromDate(),
                getToDate(), getReceipt(), getUserLogin());
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", carId=" + carId +
                ", passport=" + passport +
                ", withDriver=" + withDriver +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", receipt=" + receipt +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", userLogin=" + userLogin + "}";
    }
}
