package com.myproject.dao;

import com.myproject.exception.DaoException;

public interface  OrderDao {
    boolean processTheBooking() throws DaoException;
    double getDriverRentPrice(int id) throws DaoException;
}
