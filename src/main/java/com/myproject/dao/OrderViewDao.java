package com.myproject.dao;

import com.myproject.dao.connection.ConnectManager;
import com.myproject.dao.entity.OrderViewForUserRequest;
import com.myproject.exception.DaoException;

import java.util.List;

public interface OrderViewDao {
    List<OrderViewForUserRequest> getOrdersForManager(String approved,int startPage) throws DaoException;
    List<OrderViewForUserRequest> getOrdersForUser(String login,int startPage) throws  DaoException;
    void setConnection(ConnectManager connectManager) throws DaoException;
}
