package com.myproject.dao;

import com.myproject.dao.connection.ConnectManager;
import com.myproject.dao.entity.Order;
import com.myproject.exception.DaoException;

public interface  OrderDao {
    Order processTheBooking(Order order,String userLogin,int carId,boolean processPayment) throws DaoException;
    double getDriverRentPrice(int id) throws DaoException;
    void setConnection(ConnectManager connectManager) throws DaoException;
    boolean setApprovedOrderByManager(String managerLogin,String feedback,String approved,long orderId)throws DaoException;
}
