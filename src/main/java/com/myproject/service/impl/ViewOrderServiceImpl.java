package com.myproject.service.impl;

import com.myproject.dao.OrderViewDao;
import com.myproject.dao.entity.OrderViewForUserRequest;
import com.myproject.dao.impl.OrderViewDaoImpl;
import com.myproject.exception.DaoException;
import com.myproject.exception.ServiceException;
import com.myproject.service.OrderViewService;

import java.util.List;
import java.util.Optional;

public class ViewOrderServiceImpl implements OrderViewService {
    private final OrderViewDao orderViewDao = new OrderViewDaoImpl();

    @Override
    public Optional<List<OrderViewForUserRequest>> getAllUserPersonalOrders(String login,int startPage) throws ServiceException {
        List<OrderViewForUserRequest> allUserOrdersPersonal;
        try {
           allUserOrdersPersonal =  orderViewDao.getOrdersForUser(login,startPage);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }

        return Optional.of(allUserOrdersPersonal);
    }

    @Override
    public Optional<List<OrderViewForUserRequest>> getOrders(String approved) throws ServiceException {
        OrderViewForUserRequest orderViewForUserRequest = null;
        List<OrderViewForUserRequest> res = null;
        try {
            if (approved.equals("approved")) {
               res =  orderViewDao.getOrdersForManager("yes");
            } else if (approved.equals("declined")) {
               res =  orderViewDao.getOrdersForManager("no");
            }
        }catch (DaoException e){
            throw new ServiceException(e.getMessage());
        }
        assert res != null;
        return Optional.of(res);
    }
}
