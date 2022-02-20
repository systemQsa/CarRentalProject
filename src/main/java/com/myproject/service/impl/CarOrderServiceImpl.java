package com.myproject.service.impl;

import com.myproject.dao.entity.Order;
import com.myproject.dao.impl.OrderDaoImpl;
import com.myproject.exception.DaoException;
import com.myproject.exception.ServiceException;
import com.myproject.service.CarOrderService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class CarOrderServiceImpl implements CarOrderService {
    private final OrderDaoImpl orderDao = new OrderDaoImpl();
    private static final Logger logger = LogManager.getLogger(CarOrderServiceImpl.class);
    private final Lock lock = new ReentrantLock();
    @Override
    public boolean confirmTheBooking() {
        return false;
    }

    @Override
    public boolean setUserOrderRelation(int orderId, int userId) throws ServiceException {
        return false;
    }

    @Override
    public BigDecimal countReceipt(long diffHours, double carRentPrice, boolean isWithDriver) throws ServiceException {
        int idPriceDriverByDefault = 1;
        double optionWithDriverCoast = 0;
        if (isWithDriver) {
            try {
                optionWithDriverCoast = orderDao.getDriverRentPrice(idPriceDriverByDefault);
                optionWithDriverCoast += (diffHours * carRentPrice);

            } catch (DaoException e) {
                logger.error("SOME PROBLEM  IN CarOrderServiceImpl class CANT COUNT THE TOTAL RECEIPT");
                throw new ServiceException("SOME PROBLEM CANT COUNT THE TOTAL RECEIPT", e);
            }
        } else {
            optionWithDriverCoast += (diffHours * carRentPrice);
        }
        return new BigDecimal(optionWithDriverCoast);
    }

    @Override
    public boolean setOrder(String passport, String fromDate, String toDate,
                            String withDriver, double receipt, int userId, String userLogin, int carId) throws ServiceException {
        Order.OrderBuilder order = new Order.OrderBuilder();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean isPaymentSuccessful;
        Date parseFromDate = null;
        Date parseToDate = null;
        try {
            parseFromDate = dateFormat.parse(fromDate);
            parseToDate = dateFormat.parse(toDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Timestamp fromDateTime = new Timestamp(parseFromDate.getTime());
        Timestamp toDateTime = new Timestamp(parseToDate.getTime());

        order.setPassport(passport)
                .setFromDate(fromDate)
                .setToDate(toDate)
                .setWithDriver(withDriver)
                .setReceipt(receipt)
                .setUserId(userId)
                .setDateFrom(fromDateTime)
                .setDateTo(toDateTime);
        try {
            lock.lock();
            isPaymentSuccessful = orderDao.processTheBooking(order.build(), userLogin, carId);
            lock.unlock();
        } catch (DaoException e) {
            logger.fatal("SOME PROBLEM CANT PROCESS THE USER PAYMENT");
            throw new ServiceException("CANT PROCESS THE USER PAYMENT", e);
        }
        return isPaymentSuccessful;
    }

    @Override
    public boolean bookTheCar(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }
}
