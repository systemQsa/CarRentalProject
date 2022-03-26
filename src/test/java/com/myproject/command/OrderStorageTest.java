package com.myproject.command;

import com.myproject.dao.entity.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrderStorageTest {
    private static Order.OrderBuilder order;
    private static Order.OrderBuilder order2;


    @Before
    public void beforeTest() {
        order = new Order.OrderBuilder();
        order.setPassport("Ab1234").setUserId(1);
        order2 = new Order.OrderBuilder();
        order2.setReceipt(12.0).setUserLogin("user@gmail.com");

    }

    @After
    public void afterTest() {
        OrderStorage.getOrders().clear();
    }

    @Test
    public void addOrder() {
        OrderStorage.ddOrder(order.build());
        OrderStorage.ddOrder(order2.build());
        assertEquals(2, OrderStorage.getOrders().size());
    }

    @Test
    public void getOrders() {
        OrderStorage.getOrders().clear();
        Order.OrderBuilder newOrder = new Order.OrderBuilder();
        newOrder.setUserLogin("some@logingmail.com")
                .setWithDriver("N");
        OrderStorage.ddOrder(newOrder.build());

        assertEquals(1, OrderStorage.getOrders().size());
    }
}