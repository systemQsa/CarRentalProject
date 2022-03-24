package com.myproject.factory.impl;


import com.myproject.dao.CarDao;
import com.myproject.dao.DriverDao;
import com.myproject.dao.OrderDao;
import com.myproject.dao.OrderViewDao;
import com.myproject.dao.impl.*;
import com.myproject.factory.DaoFactory;

public class DaoFactoryImpl implements DaoFactory {

    @Override
    public UserDaoImpl getUserDao() {
        return new UserDaoImpl();
    }

    @Override
    public CarDao getCarDao() {
        return new CarDaoImpl();
    }

    @Override
    public DriverDao getDriverDao() {
        return new DriverDaoImpl();
    }

    @Override
    public OrderDao getOrderDao() {
        return new OrderDaoImpl();
    }

    @Override
    public OrderViewDao getOrderViewDao() {
        return new OrderViewDaoImpl();
    }
}
