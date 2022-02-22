package com.myproject.command;

import com.myproject.dao.entity.Order;


import java.util.ArrayList;
 import java.util.List;

public class OrderStorage {
    private static List<Order> orderList = new ArrayList<>();

    public static void ddOrder(Order order) {
        orderList.add(order);
    }

    public static List<Order> getOrders() {
        return orderList;
    }

}