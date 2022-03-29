package com.myproject.dao;

import com.myproject.dao.connection.ConnectManager;
import com.myproject.dao.entity.Order;
import com.myproject.exception.DaoException;

public interface OrderDao {

     boolean checkIfSuchOrderExistsInDb(Order order) throws DaoException;

    Order processTheBooking(Order order, boolean processPayment) throws DaoException;

    void setConnection(ConnectManager connectManager) throws DaoException;

    boolean setApprovedOrderByManager(String managerLogin, String feedback, String approved, long orderId) throws DaoException;
}
