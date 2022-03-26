package com.myproject.command;

import com.myproject.dao.entity.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * The OrderStorage class stores all the orders
 */
public class OrderStorage {
    private static List<Order> orderList = new ArrayList<>();

    public static boolean ddOrder(Order order) {
        return orderList.add(order);
    }

    public static List<Order> getOrders() {
        return orderList;
    }

}