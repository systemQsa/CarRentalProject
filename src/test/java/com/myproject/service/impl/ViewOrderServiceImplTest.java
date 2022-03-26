package com.myproject.service.impl;

import com.myproject.dao.OrderViewDao;
import com.myproject.dao.connection.DBManager;
import com.myproject.dao.impl.OrderViewDaoImpl;
import com.myproject.exception.ServiceException;
import com.myproject.service.OrderViewService;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ViewOrderServiceImplTest {
    private DBManager dbManager;

    @BeforeClass
    public static void beforeTesting() {
        DBManager.getInstance().loadScript();
    }

    @Test
    public void getAllUserPersonalOrders() {
        dbManager = DBManager.getInstance();

        OrderViewDao orderViewDao = new OrderViewDaoImpl(dbManager);
        OrderViewService orderViewService = new ViewOrderServiceImpl(orderViewDao);

        assertThrows(ServiceException.class,()->orderViewService.getAllUserPersonalOrders("notExistUser@gmail.com", 1,3));

    }

    @Test
    public void getOrders() {
        dbManager = DBManager.getInstance();
        OrderViewDao orderViewDao = new OrderViewDaoImpl(dbManager);
        OrderViewService orderViewService = new ViewOrderServiceImpl(orderViewDao);

        assertThrows(ServiceException.class, () -> orderViewService.getOrders(null, 1,1));


    }
}