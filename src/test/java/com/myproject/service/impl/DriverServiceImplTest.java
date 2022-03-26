package com.myproject.service.impl;

import com.myproject.dao.DriverDao;
import com.myproject.dao.connection.DBManager;
import com.myproject.dao.impl.DriverDaoImpl;
import com.myproject.exception.ServiceException;
import com.myproject.service.DriverService;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class DriverServiceImplTest {
    private DBManager dbManager;

    @BeforeClass
    public static void init() {
        DBManager.getInstance().loadScript();
    }

    @Test
    public void changeDriverPrice() {
        dbManager = DBManager.getInstance();
        dbManager = DBManager.getInstance();
        DriverDao driverDao = new DriverDaoImpl(dbManager);
        DriverService driverService = new DriverServiceImpl(driverDao);

        try {
            assertTrue(driverService.changeDriverPrice(45.5));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDriverRentalPrice() {
        dbManager = DBManager.getInstance();
        DriverDao driverDao = new DriverDaoImpl(dbManager);
        DriverService driverService = new DriverServiceImpl(driverDao);

        try {
            driverService.changeDriverPrice(34.23);
            assertEquals("34.23", String.valueOf(driverService.getDriverRentalPrice()));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}