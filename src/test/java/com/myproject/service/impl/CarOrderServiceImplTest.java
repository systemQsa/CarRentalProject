package com.myproject.service.impl;

import com.myproject.dao.OrderDao;
import com.myproject.dao.connection.DBManager;
import com.myproject.dao.entity.Order;
import com.myproject.dao.impl.OrderDaoImpl;
import com.myproject.exception.DaoException;
import com.myproject.exception.ServiceException;
import com.myproject.service.CarOrderService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.Assert.*;

public class CarOrderServiceImplTest {
    private DBManager dbManager;

    @BeforeClass
    public static void beforeTesting(){
        DBManager.getInstance().loadScript();
    }

    @Test
    public void countReceiptWithoutDriver() {
        dbManager = DBManager.getInstance();
        OrderDao orderDao = new OrderDaoImpl(dbManager);
        CarOrderService carOrderService = new CarOrderServiceImpl(orderDao);
        //todo redone method with driver price 1
        try {
            BigDecimal bigDecimal = carOrderService.countReceipt(2, 10.0, false);
            assertEquals(20,bigDecimal.intValue());
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void countReceiptWithDriver() {
        dbManager = DBManager.getInstance();
        OrderDao orderDao = new OrderDaoImpl(dbManager);
        CarOrderService carOrderService = new CarOrderServiceImpl(orderDao);
        //todo redone method with driver price 2
        try {
            BigDecimal bigDecimal = carOrderService.countReceipt(2, 10.0, true);
            assertEquals(120,bigDecimal.intValue());
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void countReceiptNegative() {
        dbManager = DBManager.getInstance();
        OrderDao orderDao = new OrderDaoImpl(dbManager);
        CarOrderService carOrderService = new CarOrderServiceImpl(orderDao);
        //todo redone method with driver price 3
        assertThrows(ServiceException.class,()->carOrderService.countReceipt(0, 10.0, false));

    }

    @Test
    public void updateOrderByManager() {
        dbManager = DBManager.getInstance();
        OrderDao orderDao = new OrderDaoImpl(dbManager);
        CarOrderService carOrderService = new CarOrderServiceImpl(orderDao);

        Order.OrderBuilder order = new Order.OrderBuilder();
        order.setPassport("AB1234")
                .setUserLogin("user1@gmail.com")
                .setCar(1)
                .setDateFrom(Timestamp.valueOf("2022-04-22 17:00:00"))
                .setDateTo(Timestamp.valueOf("2022-04-22 18:00:00"))
                .setWithDriver("N")
                .setReceipt(10.0)
                .setUserId(1);

        try {
            Order savedOrder = orderDao.processTheBooking(order.build(), true);
            assertTrue(carOrderService.updateOrderByManager("user2@gmail.com",savedOrder.getOrderId(),"yes","some feedback here"));
        } catch (ServiceException | DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setOrder() {
        dbManager = DBManager.getInstance();
        OrderDao orderDao = new OrderDaoImpl(dbManager);
        CarOrderService carOrderService = new CarOrderServiceImpl(orderDao);
        Order order;

        Order.OrderBuilder newOrder = new Order.OrderBuilder();
        newOrder.setUserLogin("user1@gmail.com")
                .setUserId(1)
                .setCar(1)
                .setPassport("AC1234")
                .setDateFrom(Timestamp.valueOf("2022-05-22 13:00:00"))
                .setDateTo(Timestamp.valueOf("2022-05-22 14:00:00"))
                .setWithDriver("N")
                .setReceipt(10.0);

        try {
            order = carOrderService.setOrder(newOrder.build(), true);
            assertEquals("AC1234",order.getPassport());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

}