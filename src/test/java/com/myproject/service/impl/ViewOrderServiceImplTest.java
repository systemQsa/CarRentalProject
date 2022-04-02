package com.myproject.service.impl;

import com.myproject.dao.OrderViewDao;
import com.myproject.dao.connection.DBManager;
import com.myproject.dao.impl.OrderViewDaoImpl;
import com.myproject.exception.ServiceException;
import com.myproject.service.OrderViewService;
import org.junit.BeforeClass;
import org.junit.Test;

public class ViewOrderServiceImplTest {
    private DBManager dbManager;

    @BeforeClass
    public static void beforeTesting() {
        DBManager.getInstance().loadScript();
    }

    @Test(expected = ServiceException.class)
    public void getAllUserPersonalOrders() throws ServiceException {
        dbManager = DBManager.getInstance();

        OrderViewDao orderViewDao = new OrderViewDaoImpl(dbManager);
        OrderViewService orderViewService = new ViewOrderServiceImpl(orderViewDao);
        orderViewService.getAllUserPersonalOrders("notExistUser@gmail.com", 1,3);
    }

    @Test(expected = ServiceException.class)
    public void getOrders() throws ServiceException {
        dbManager = DBManager.getInstance();
        OrderViewDao orderViewDao = new OrderViewDaoImpl(dbManager);
        OrderViewService orderViewService = new ViewOrderServiceImpl(orderViewDao);
        orderViewService.getOrders(null, 1,1);
    }
}