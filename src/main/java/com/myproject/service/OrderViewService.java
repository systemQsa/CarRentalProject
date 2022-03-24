package com.myproject.service;

import com.myproject.dao.entity.OrderViewForUserRequest;
import com.myproject.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface OrderViewService {

    Optional<List<OrderViewForUserRequest>> getOrders(String approved, int startPage,int noOfRecords) throws ServiceException;

    Optional<List<OrderViewForUserRequest>> getAllUserPersonalOrders(String login, int startPage,int noOfRecords) throws ServiceException;
}
