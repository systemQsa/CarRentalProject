package com.myproject.dao.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Order {
    private long orderId;
    private User user;
    private Car car;
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

        public OrderBuilder setOrderId(long orderId) {
            order.orderId = orderId;
            return this;
        }

        public OrderBuilder setUser(User user) {
            order.user = user;
            return this;
        }

        public OrderBuilder setCar(Car car) {
            order.car = car;
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
}
