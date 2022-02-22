package com.myproject.dao;

import com.myproject.dao.entity.OrderViewForUserRequest;
import com.myproject.exception.DaoException;

import java.util.List;

public interface OrderViewDao {
    List<OrderViewForUserRequest> getOrdersForManager(String approved) throws DaoException;
    List<OrderViewForUserRequest> getOrdersForUser(String login) throws  DaoException;
}
