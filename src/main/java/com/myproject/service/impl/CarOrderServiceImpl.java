package com.myproject.service.impl;

import com.myproject.dao.DriverDao;
import com.myproject.dao.OrderDao;
import com.myproject.dao.entity.Order;
import com.myproject.exception.DaoException;
import com.myproject.exception.ServiceException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.CarOrderService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The CarOrderServiceImpl class represents methods to processing the request related with Order entity
 */
public class CarOrderServiceImpl implements CarOrderService {
    private  OrderDao orderDao;
    private final DriverDao driverDao;
    private static final Logger logger = LogManager.getLogger(CarOrderServiceImpl.class);
    private final Lock lock = new ReentrantLock();

    public CarOrderServiceImpl(){
        orderDao = new AbstractFactoryImpl().getFactory().getDaoFactory().getOrderDao();
        driverDao = new AbstractFactoryImpl().getFactory().getDaoFactory().getDriverDao();
    }

    public CarOrderServiceImpl(DriverDao driverDao){
        this.driverDao = driverDao;
    }

    public CarOrderServiceImpl(DriverDao driverDao,OrderDao orderDao){
        this.driverDao = driverDao;
        this.orderDao = orderDao;
    }


    /**
     * The method count total receipt by given data
     * @param diffHours - gets amount of rental hours
     * @param carRentPrice - gets car rental price
     * @param isWithDriver - gets option with driver or not
     * @return counted price for the booking
     * @throws ServiceException in case by some reason cannot count the receipt for the booking
     */
    @Override
    public BigDecimal countReceipt(long diffHours, double carRentPrice, boolean isWithDriver) throws ServiceException {
        double optionWithDriverCoast = 0;
        if (diffHours <= 0) {
            throw new ServiceException("The minimum order time is an hour! Please enter time properly!");
        }
        if (isWithDriver) {
            try {
                optionWithDriverCoast = (diffHours * driverDao.getDriverPrice());
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

    /**
     * The method if desired order already presence in DB
     * @param order - gets the order desired to be booked
     * @return in the order already exists in DB returns true
     * @throws ServiceException in case by some reason the searched order cannot be found
     */
    @Override
    public boolean checkOrderPresenceInDb(Order order) throws ServiceException {
        try {
           orderDao.checkIfSuchOrderExistsInDb(order);
        } catch (DaoException e) {
            logger.warn("Something went wrong cant check the presence in db fir given order");
            throw new ServiceException(e);
        }
        return false;
    }

    /**
     * The method if the booking were accepted or declined register the order
     * @param managerLogin - gets manager login who were accepted or declined the given booking
     * @param orderId - gets order id
     * @param approved - gets if the booking is approved
     * @param feedback - gets manager feedback
     * @return if manager update registered new order returns true
     * @throws ServiceException in case if given order cannot be registered by manager
     */
    @Override
    public boolean updateOrderByManager(String managerLogin, long orderId, String approved, String feedback) throws ServiceException {
        try {
            return orderDao.setApprovedOrderByManager(managerLogin, feedback, approved, orderId);
        } catch (DaoException e) {
            logger.warn("Some problem occur can`t update/register the new order in CarOrderServiceImpl class");
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * The method process the booking and payment process
     * @param order - gets desired order
     * @param processPayment - gets response if the booking wanted to be paid
     * @return the order
     * @throws ServiceException in case the payment was failed or due to cannot process the booking
     */

    @Override
    public Order setOrder(Order order, boolean processPayment) throws ServiceException {
       Order orderResult;
        try {
            lock.lock();
            orderResult = orderDao.processTheBooking(order, processPayment);

        } catch (DaoException e) {
            logger.fatal("SOME PROBLEM CANT PROCESS THE USER PAYMENT");
            throw new ServiceException(e);
        }finally {
            lock.unlock();
        }
        return orderResult;
    }
}
