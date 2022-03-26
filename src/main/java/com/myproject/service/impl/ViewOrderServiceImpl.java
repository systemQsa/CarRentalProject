package com.myproject.service.impl;

import com.myproject.dao.OrderViewDao;
import com.myproject.dao.entity.OrderViewForUserRequest;
import com.myproject.exception.DaoException;
import com.myproject.exception.ServiceException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.OrderViewService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.util.List;
import java.util.Optional;

/**
 * The ViewOrderServiceImpl class represents class which provides methods to get orders
 * all approved and not bookings for user
 * and all bookings for manager to view
 */

public class ViewOrderServiceImpl implements OrderViewService {
    private final OrderViewDao orderViewDao;
    private static final Logger logger = LogManager.getLogger(ViewOrderServiceImpl.class);

    public ViewOrderServiceImpl(){
        orderViewDao = new AbstractFactoryImpl().getFactory().getDaoFactory().getOrderViewDao();
    }


    public ViewOrderServiceImpl(OrderViewDao orderViewDao){
        this.orderViewDao = orderViewDao;
    }

    /**
     * The method gets all user successful and not successful orders  from DataBase
     * @param login - gets user login
     * @param startPage - gets page number
     * @param noOfRecords - gets required number of records to receive and display
     * @return Optional list of orders
     * @throws ServiceException in case if something went wrong and user can`t get all orders
     */
    @Override
    public Optional<List<OrderViewForUserRequest>> getAllUserPersonalOrders(String login,int startPage,int noOfRecords) throws ServiceException {
        List<OrderViewForUserRequest> allUserOrdersPersonal;
        try {
           allUserOrdersPersonal =  orderViewDao.getOrdersForUser(login,startPage,noOfRecords);
        } catch (DaoException e) {
            logger.warn("something went wrong ViewOrderServiceImpl failed");
            throw new ServiceException(e.getMessage());
        }
        logger.info("User got its personal orders successfully");
        return Optional.of(allUserOrdersPersonal);
    }


    /**
     * The method gets all orders depends on manager need
     * @param approved - gets required booking status (approved/declined)
     * @param startPage - gets page number
     * @param noOfRecords - gets required number of records
     * @return Oprional list of orders (returns all approved/all declined orders)
     * @throws ServiceException in case if by some reason manager can`t get all orders
     */
    @Override
    public Optional<List<OrderViewForUserRequest>> getOrders(String approved,int startPage,int noOfRecords) throws ServiceException {
         List<OrderViewForUserRequest> res = null;
        try {
            if (approved.equals("approved")) {
               res =  orderViewDao.getOrdersForManager("yes",startPage,noOfRecords);
                System.out.println("res  "+res);
            } else if (approved.equals("declined")) {
               res =  orderViewDao.getOrdersForManager("no",startPage,noOfRecords);
            }
        }catch (DaoException | NullPointerException e){
            logger.warn("getOrders() in ViewOrderServiceImpl Failed");
            throw new ServiceException(e.getMessage());
        }
        assert res != null;
        logger.info("Manager got orders successfully");
        return Optional.of(res);
    }
}
