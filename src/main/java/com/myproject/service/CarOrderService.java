package com.myproject.service;

import com.myproject.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CarOrderService {
    boolean bookTheCar(HttpServletRequest request, HttpServletResponse response)throws ServiceException;
    double confirmTheBooking() throws ServiceException;
    double countReceipt(long diffDays,double carRentPrice,boolean isWithDriver) throws ServiceException;
}
