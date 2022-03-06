package com.myproject.dao.entity;

import java.io.Serializable;
import java.util.Objects;


public class OrderViewForUserRequest implements Serializable {
    private static final long serialUID = 1L;
    private String login;
    private int amountOfRecords;
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
        public OrderViewBuilder setAmountOfRecords(int amountOfRecords){
            orderView.amountOfRecords = amountOfRecords;
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

        public OrderViewBuilder setApproved(String approved) {
            orderView.approved = approved;
            return this;
        }

        public OrderViewBuilder setFeedback(String feedback) {
            orderView.feedback = feedback;
            return this;
        }


        public OrderViewForUserRequest build() {
            return orderView;
        }
    }

    public String getLogin() {
        return login;
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

    public int getAmountOfRecords() {
        return amountOfRecords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderViewForUserRequest that = (OrderViewForUserRequest) o;
        return Objects.equals(getLogin(), that.getLogin()) && Objects.equals(getOrder(), that.getOrder())
                && Objects.equals(getCar(), that.getCar()) && Objects.equals(getApproved(), that.getApproved())
                && Objects.equals(getFeedback(), that.getFeedback());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getOrder(), getCar(), getApproved(), getFeedback());
    }

    @Override
    public String toString() {
        return "OrderViewForUserRequest{" +
                "login='" + login + '\'' +
                ", order=" + order +
                ", car=" + car +
                ", approved='" + approved + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';

    }
}
