package com.myproject.service;

 import com.myproject.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public interface CarOrderService {
    boolean bookTheCar(HttpServletRequest request, HttpServletResponse response)throws ServiceException;
    boolean confirmTheBooking() throws ServiceException;
    BigDecimal countReceipt(long diffHours, double carRentPrice, boolean isWithDriver) throws ServiceException;
    boolean setOrder(String passport, String fromDate,String toDate,String withDriver,double receipt,int userId,String userLogin,int carId) throws ServiceException;
    boolean setUserOrderRelation(int orderId, int userId) throws ServiceException;
}
