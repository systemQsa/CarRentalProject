package com.myproject.command;

import com.myproject.dao.entity.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrderStorageTest {
    private static Order.OrderBuilder order;
    private static Order.OrderBuilder order2;

    @BeforeClass
    public static void init(){
        order = new Order.OrderBuilder();
        order.setPassport("Ab1234").setUserId(1);
        order2 = new Order.OrderBuilder();
        order2.setReceipt(12.0).setUserLogin("user@gmail.com");
    }

    @Before
    public void beforeTest() {
        OrderStorage.ddOrder(order.build());
        OrderStorage.ddOrder(order2.build());
    }

    @After
    public void afterTest() {
        OrderStorage.getOrders().clear();
    }

    @Test
    public void ddOrder() {
        assertEquals(2, OrderStorage.getOrders().size());
    }

    @Test
    public void getOrders() {
        OrderStorage.getOrders().remove(0);
        assertEquals(1, OrderStorage.getOrders().size());
    }
}