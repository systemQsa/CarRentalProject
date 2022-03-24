package com.myproject.dao;

import com.myproject.exception.DaoException;

public interface DriverDao {

    boolean updateDriverPrice(int price) throws DaoException;
    double getDriverPrice() throws DaoException;
}
