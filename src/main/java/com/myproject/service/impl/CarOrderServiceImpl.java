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


public class CarOrderServiceImpl implements CarOrderService {
    private final OrderDaoImpl orderDao = new OrderDaoImpl();
    private static final Logger logger = LogManager.getLogger(CarOrderServiceImpl.class);

    @Override
    public boolean confirmTheBooking() {
        return false;
    }

    @Override
    public boolean setUserOrderRelation(int orderId, int userId) throws ServiceException {
        return false;
    }

    @Override
    public BigDecimal countReceipt(long diffDays, double carRentPrice, boolean isWithDriver) throws ServiceException {
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
        return new BigDecimal(optionWithDriverCoast);
    }

    @Override
    public boolean setOrder(String passport, String fromDate, String toDate,
                          String withDriver, double receipt, int userId,String userLogin,int carId) throws ServiceException {
        Order.OrderBuilder order = new Order.OrderBuilder();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean isPaymentSuccessful;
        Date parseFromDate = null;
        try {
            parseFromDate = dateFormat.parse(fromDate);
            Date parseToDate = dateFormat.parse(toDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp fromDateTime = new Timestamp(parseFromDate.getTime());
         Timestamp toDateTime = new Timestamp(parseFromDate.getTime());

        order.setPassport(passport)
                .setFromDate(fromDate)
                .setToDate(toDate)
                .setWithDriver(withDriver)
                .setReceipt(receipt)
                .setUserId(userId)
                .setDateFrom(fromDateTime)
                .setDateTo(toDateTime);
        try {
            isPaymentSuccessful = orderDao.processTheBooking(order.build(),userLogin,carId);
        } catch (DaoException e) {
            logger.fatal("SOME PROBLEM CANT PROCESS THE USER PAYMENT");
            throw new ServiceException("CANT PROCESS THE USER PAYMENT",e);
        }
        return isPaymentSuccessful;
    }

    @Override
    public boolean bookTheCar(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }
}
