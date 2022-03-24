package com.myproject.service.impl;

import com.myproject.dao.DriverDao;
import com.myproject.exception.DaoException;
import com.myproject.exception.ServiceException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.DriverService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DriverServiceImpl implements DriverService {
    private static final Logger logger = LogManager.getLogger(DriverServiceImpl.class);
    private DriverDao driverDao;


    public DriverServiceImpl(){
        driverDao = new AbstractFactoryImpl().getFactory().getDaoFactory().getDriverDao();
    }

    public DriverServiceImpl(DriverDao driverDao){
        this.driverDao = driverDao;
    }

    @Override
    public boolean changeDriverPrice(int newDriverPrice) throws ServiceException {
        try {
            driverDao.updateDriverPrice(newDriverPrice);
        } catch (DaoException e) {
            logger.warn("Problem occur in DriverServiceImpl class can`t set new Rental price for driver");
            throw new ServiceException(e);
        }
        return true;
    }
    @Override
    public double getDriverRentalPrice() throws ServiceException {
        double driverPrice;
        try {
            driverPrice = driverDao.getDriverPrice();
        } catch (DaoException e) {
            logger.warn("Problem occur in DriverServiceImpl class cant get Driver Rental Price");
            throw new ServiceException(e);
        }
        return driverPrice;
    }
}
