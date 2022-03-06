package com.myproject.service.impl;

import com.myproject.dao.OrderDao;
import com.myproject.dao.entity.Order;
import com.myproject.dao.impl.OrderDaoImpl;
import com.myproject.exception.DaoException;
import com.myproject.exception.ServiceException;
import com.myproject.factory.impl.AbstractFactoryImpl;
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
    private final OrderDao orderDao = new AbstractFactoryImpl().getFactory().getDaoFactory().getOrderDao();
    private static final Logger logger = LogManager.getLogger(CarOrderServiceImpl.class);
    private final Lock lock = new ReentrantLock();

    @Override
    public boolean confirmTheBooking() {
        return false;
    }

    @Override
    public boolean setUserOrderRelation(long orderId, long userId) throws ServiceException {
        return false;
    }

    @Override
    public BigDecimal countReceipt(long diffHours, double carRentPrice, boolean isWithDriver) throws ServiceException {
        int idPriceDriverByDefault = 1;
        double optionWithDriverCoast = 0;
        if (diffHours <= 0) {
            throw new ServiceException("The minimum order time is an hour! Please enter time properly!");
        }
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
    public boolean updateOrderByManager(String managerLogin, long orderId, String approved, String feedback) throws ServiceException {
        boolean isApproved;
        try {
            isApproved = orderDao.setApprovedOrderByManager(managerLogin, feedback, approved, orderId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return isApproved;
    }

    @Override
    public Order setOrder(String passport, String fromDate, String toDate,
                          String withDriver, double receipt, long userId, String userLogin, int carId, boolean processPayment) throws ServiceException {
        Order.OrderBuilder order = new Order.OrderBuilder();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Order orderResult;
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
            orderResult = orderDao.processTheBooking(order.build(), userLogin, carId, processPayment);
            lock.unlock();
        } catch (DaoException e) {
            logger.fatal("SOME PROBLEM CANT PROCESS THE USER PAYMENT");
            throw new ServiceException(e);
        }
        return orderResult;
    }

    @Override
    public boolean bookTheCar(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }
}
