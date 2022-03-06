package com.myproject.service.impl;

import com.myproject.dao.OrderViewDao;
import com.myproject.dao.entity.OrderViewForUserRequest;
import com.myproject.dao.impl.OrderViewDaoImpl;
import com.myproject.exception.DaoException;
import com.myproject.exception.ServiceException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.OrderViewService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class ViewOrderServiceImpl implements OrderViewService {
    private final OrderViewDao orderViewDao = new AbstractFactoryImpl().getFactory().getDaoFactory().getOrderViewDao();
    private static final Logger logger = LogManager.getLogger(ViewOrderServiceImpl.class);
    @Override
    public Optional<List<OrderViewForUserRequest>> getAllUserPersonalOrders(String login,int startPage) throws ServiceException {
        List<OrderViewForUserRequest> allUserOrdersPersonal;
        try {
           allUserOrdersPersonal =  orderViewDao.getOrdersForUser(login,startPage);
        } catch (DaoException e) {
            logger.warn("something went wrong ViewOrderServiceImpl failed");
            throw new ServiceException(e.getMessage());
        }
        logger.info("User got its personal orders successfully");
        return Optional.of(allUserOrdersPersonal);
    }

    @Override
    public Optional<List<OrderViewForUserRequest>> getOrders(String approved,int startPage) throws ServiceException {
         List<OrderViewForUserRequest> res = null;
        try {
            if (approved.equals("approved")) {
               res =  orderViewDao.getOrdersForManager("yes",startPage);
            } else if (approved.equals("declined")) {
               res =  orderViewDao.getOrdersForManager("no",startPage);
            }
        }catch (DaoException e){
            logger.warn("getOrders() in ViewOrderServiceImpl Failed");
            throw new ServiceException(e.getMessage());
        }
        assert res != null;
        logger.info("Manager got orders successfully");
        return Optional.of(res);
    }
}