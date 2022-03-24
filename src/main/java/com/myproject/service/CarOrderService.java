package com.myproject.service;

import com.myproject.dao.entity.Order;
import com.myproject.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public interface CarOrderService {

    boolean checkOrderPresenceInDb(Order order) throws ServiceException;

    boolean bookTheCar(HttpServletRequest request, HttpServletResponse response) throws ServiceException;

    BigDecimal countReceipt(long diffHours, double carRentPrice, boolean isWithDriver) throws ServiceException;

    Order setOrder(Order order, boolean processPayment) throws ServiceException;

    boolean updateOrderByManager(String managerLogin, long orderId, String approved, String feedback) throws ServiceException;
}
