package com.myproject.dao;

import com.myproject.exception.DaoException;

public interface DriverDao {

    boolean updateDriverPrice(double price) throws DaoException;
    double getDriverPrice() throws DaoException;
}
