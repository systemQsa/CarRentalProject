package com.myproject.service;

import com.myproject.dao.entity.OrderViewForUserRequest;
import com.myproject.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface OrderViewService {
    Optional<List<OrderViewForUserRequest>> getOrders(String approved) throws ServiceException;
}
