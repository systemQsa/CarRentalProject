package com.myproject.service.impl;

import com.myproject.dao.impl.OrderDaoImpl;
import com.myproject.exception.DaoException;
import com.myproject.exception.ServiceException;
import com.myproject.service.CarOrderService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CarOrderServiceImpl implements CarOrderService {
    private OrderDaoImpl orderDao = new OrderDaoImpl();
    private static final Logger logger = LogManager.getLogger(CarOrderServiceImpl.class);

    @Override
    public double confirmTheBooking() {
        return 0;
    }

    @Override
    public double countReceipt(long diffDays, double carRentPrice, boolean isWithDriver) throws ServiceException {
        int idPriceDriverByDefault = 1;
        double optionWithDriverCoast = 0;
        if (isWithDriver) {
            try {
                optionWithDriverCoast = orderDao.getDriverRentPrice(idPriceDriverByDefault);
                optionWithDriverCoast += ((diffDays * 24) * carRentPrice);
            } catch (DaoException e) {
                logger.error("SOME PROBLEM  IN CarOrderServiceImpl class CANT COUNT THE TOTAL RECEIPT");
                throw new ServiceException("SOME PROBLEM CANT COUNT THE TOTAL RECEIPT", e);
            }
        } else {
            optionWithDriverCoast += ((diffDays * 24) * carRentPrice);
        }
        return optionWithDriverCoast;
    }

    @Override
    public boolean bookTheCar(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }
}
