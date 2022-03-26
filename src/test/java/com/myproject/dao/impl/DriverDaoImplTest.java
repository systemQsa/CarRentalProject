package com.myproject.dao.impl;

import com.myproject.dao.DriverDao;
import com.myproject.dao.connection.DBManager;
import com.myproject.exception.DaoException;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class DriverDaoImplTest {
    private static DBManager dbManager;

    @BeforeClass
    public static void init() {
        DBManager.getInstance().loadScript();
    }

    @Test
    public void updateDriverPrice() {
        dbManager = DBManager.getInstance();
        DriverDao driverDao = new DriverDaoImpl(dbManager);
        try {
            assertTrue(driverDao.updateDriverPrice(12.0));
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDriverPrice() {
        dbManager = DBManager.getInstance();
        DriverDao driverDao = new DriverDaoImpl(dbManager);
        try {
            driverDao.updateDriverPrice(20.0);
            assertEquals("20.0",String.valueOf(driverDao.getDriverPrice()));
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}