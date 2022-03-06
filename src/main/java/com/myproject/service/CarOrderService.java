package com.myproject.service;

 import com.myproject.dao.entity.Car;
 import com.myproject.dao.entity.Order;
 import com.myproject.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
 import java.util.Optional;

public interface CarOrderService {
    boolean bookTheCar(HttpServletRequest request, HttpServletResponse response)throws ServiceException;
    boolean confirmTheBooking() throws ServiceException;
    BigDecimal countReceipt(long diffHours, double carRentPrice, boolean isWithDriver) throws ServiceException;
    Order setOrder(String passport, String fromDate, String toDate, String withDriver, double receipt, long userId,
                   String userLogin, int carId,boolean processPayment) throws ServiceException;
    boolean setUserOrderRelation(long orderId, long userId) throws ServiceException;
    boolean updateOrderByManager(String managerLogin,long orderId,String approved,String feedback) throws ServiceException;
}
