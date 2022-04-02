package com.myproject.dao.impl;


import com.myproject.dao.connection.DBManager;
import com.myproject.dao.entity.Order;
import com.myproject.exception.DaoException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import static org.junit.Assert.*;

public class OrderDaoImplTest {
    private static DBManager dbManager;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @BeforeClass
    public static void beforeTesting() {
         DBManager.getInstance().loadScript();
    }



    @Test
    public void processTheBooking() {
        dbManager = DBManager.getInstance();
        OrderDaoImpl orderDao = new OrderDaoImpl(dbManager);

        Order.OrderBuilder order = new Order.OrderBuilder();
        order.setPassport("AB1234")
                .setUserLogin("user1@gmail.com")
                .setCar(1)
                .setDateFrom(LocalDateTime.parse("2022-04-22 17:00:00",dateTimeFormatter))
                .setDateTo(LocalDateTime.parse("2022-04-22 18:00:00",dateTimeFormatter))
                .setWithDriver("N")
                .setReceipt(10.0)
                .setUserId(1);

        try {
            Order savedOrderInDB = orderDao.processTheBooking(order.build(), true);
            assertEquals(1, savedOrderInDB.getUserId());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = DaoException.class)
    public void processTheBookingNegative() throws DaoException {
        dbManager = DBManager.getInstance();
        OrderDaoImpl orderDao = new OrderDaoImpl(dbManager);

        Order.OrderBuilder order = new Order.OrderBuilder();
        order.setPassport("AB1234")
                .setUserLogin("user1@gmail.com")
                .setCar(1)
                .setDateFrom(LocalDateTime.parse("2022-04-22 17:00:00",dateTimeFormatter))
                .setDateTo(LocalDateTime.parse("2022-04-22 18:00:00",dateTimeFormatter))
                .setWithDriver("N")
                .setReceipt(100)
                .setUserId(1);
        orderDao.processTheBooking(order.build(), true);
    }

    @Test
    public void setApprovedOrderByManager() {
        dbManager = DBManager.getInstance();
        OrderDaoImpl orderDao = new OrderDaoImpl(dbManager);

        Order.OrderBuilder order = new Order.OrderBuilder();
        order.setPassport("AB1234")
                .setUserLogin("user1@gmail.com")
                .setCar(1)
                .setDateFrom(LocalDateTime.parse("2022-04-22 17:00:00",dateTimeFormatter))
                .setDateTo(LocalDateTime.parse("2022-04-22 18:00:00",dateTimeFormatter))
                .setWithDriver("N")
                .setReceipt(10.0)
                .setUserId(1);


        try {
            Order savedOrder = orderDao.processTheBooking(order.build(), true);
            assertTrue(orderDao.setApprovedOrderByManager("user2@gmail.com", "feedback", "yes", savedOrder.getOrderId()));
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
