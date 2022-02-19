package com.myproject.dao;

import com.myproject.dao.entity.Order;
import com.myproject.exception.DaoException;

public interface  OrderDao {
    boolean processTheBooking(Order order,String userLogin,int carId) throws DaoException;
    double getDriverRentPrice(int id) throws DaoException;
}
