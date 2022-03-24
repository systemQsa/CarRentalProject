package com.myproject.factory;

import com.myproject.dao.CarDao;
import com.myproject.dao.DriverDao;
import com.myproject.dao.OrderDao;
import com.myproject.dao.OrderViewDao;
import com.myproject.dao.impl.UserDaoImpl;

public interface DaoFactory {
    UserDaoImpl getUserDao();
    CarDao getCarDao();
    OrderDao getOrderDao();
    OrderViewDao getOrderViewDao();
    DriverDao getDriverDao();
}
