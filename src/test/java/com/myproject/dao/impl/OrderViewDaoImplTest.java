package com.myproject.dao.impl;

import com.myproject.dao.connection.DBManager;
import com.myproject.dao.entity.Order;
import com.myproject.dao.entity.OrderViewForUserRequest;
import com.myproject.exception.DaoException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.sql.*;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class OrderViewDaoImplTest {
    private static DBManager dbManager;

    @BeforeClass
    public static void beforeTesting() {
        DBManager.getInstance().loadScript();

     }

//    @Test
//    public void getOrdersForUser() {
//        dbManager = DBManager.getInstance();
//        OrderDaoImpl orderDao = new OrderDaoImpl(dbManager);
//        OrderViewDaoImpl orderViewDao = new OrderViewDaoImpl(dbManager);
//
//        Order.OrderBuilder order = new Order.OrderBuilder();
//        order.setPassport("AB1234")
//                .setDateFrom(Timestamp.valueOf("2022-04-22 17:00:00"))
//                .setDateTo(Timestamp.valueOf("2022-04-22 18:00:00"))
//                .setWithDriver("N")
//                .setReceipt(10.0)
//                .setUserId(1);
//        try {
//            Order savedOrderInDB = orderDao.processTheBooking(order.build(), "user1@gmail.com", 1, true);
//            orderDao.setApprovedOrderByManager("user2@gmail.com", "feedback", "yes", savedOrderInDB.getOrderId());
//            List<OrderViewForUserRequest> list = orderViewDao.getOrdersForUser("user1@gmail.com", 1);
//            assertEquals("porsche", list.get(0).getCar().getName());
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }
//
//    }

//    @Test
//    public void getOrdersForManager() {
//        dbManager = DBManager.getInstance();
//        OrderDaoImpl orderDao = new OrderDaoImpl(dbManager);
//        OrderViewDaoImpl orderViewDao = new OrderViewDaoImpl(dbManager);
//
//        Order.OrderBuilder order = new Order.OrderBuilder();
//        order.setPassport("AB1234")
//                .setDateFrom(Timestamp.valueOf("2022-04-22 17:00:00"))
//                .setDateTo(Timestamp.valueOf("2022-04-22 18:00:00"))
//                .setWithDriver("N")
//                .setReceipt(10.0)
//                .setUserId(1);
//
//        Order savedOrderInDB;
//
//        try {
//            savedOrderInDB = orderDao.processTheBooking(order.build(), "user3@gmail.com", 1, true);
//            orderDao.setApprovedOrderByManager("user2@gmail.com", "feedback1", "yes", savedOrderInDB.getOrderId());
//            List<OrderViewForUserRequest> approved = orderViewDao.getOrdersForManager("yes", 1);
//            assertEquals(1,approved.size());
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }
//
//    }
}