package com.myproject.dao.impl;

import com.myproject.dao.OrderViewDao;
import com.myproject.dao.connection.ConnectionPool;
import com.myproject.dao.entity.Car;
import com.myproject.dao.entity.Order;
import com.myproject.dao.entity.OrderViewForUserRequest;
import com.myproject.dao.query.QuerySQL;
import com.myproject.exception.DaoException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderViewDaoImpl implements OrderViewDao {
    private Connection connection = null;
    private static final Logger logger = LogManager.getLogger(OrderViewDaoImpl.class);

    @Override
    public List<OrderViewForUserRequest> getOrdersForUser(String login,int startPage) throws DaoException {
        connection = ConnectionPool.getInstance().getConnection();
        ResultSet resultSet;
        List<OrderViewForUserRequest> list = new ArrayList<>();
       // System.out.println("\n\nLogin " + login);

        try(PreparedStatement statement = connection.prepareStatement(QuerySQL.ALL_ORDERS_USER_VIEW)){
            statement.setString(1,login);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                OrderViewForUserRequest.OrderViewBuilder orderViewBuilder = new OrderViewForUserRequest.OrderViewBuilder();
                Car.CarBuilder carBuilder = new Car.CarBuilder();
                Order.OrderBuilder order = new Order.OrderBuilder();

                        OrderViewForUserRequest res =  orderViewBuilder
                        .setOrder(order.setPassport(resultSet.getString("passport"))
                                .setDateFrom(resultSet.getTimestamp("from_date"))
                                .setDateTo(resultSet.getTimestamp("to_date"))
                                .setWithDriver(resultSet.getString("with_driver"))
                                .setReceipt(resultSet.getDouble("receipt")).build())
                        .setCar(carBuilder.setName(resultSet.getString("name"))
                                .setCarClass(resultSet.getString("carClass"))
                                .setBrand(resultSet.getString("brand")).build())
                        .setFeedback(resultSet.getString("feedback"))
                        .setApproved(resultSet.getString("approved")).build();

                        list.add(res);
            }

        }catch (SQLException e){
            logger.warn("Some problem cant get all user orders");
            throw new DaoException(" Oops! Cant get all your orders.");
        }finally {
            ConnectionPool.closeConnection(connection);
        }
        System.out.println("\n\nLogin " + list);
        return list;
    }

    @Override
    public List<OrderViewForUserRequest> getOrdersForManager(String approved) throws DaoException {
        connection = ConnectionPool.getInstance().getConnection();

        ResultSet resultSet;
        List<OrderViewForUserRequest>list = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(QuerySQL.VIEW_ALL_APPROVED_OR_NOT_APPROVED_ORDERS_BY_MANAGER)){
               statement.setString(1,approved);
               resultSet = statement.executeQuery();
               while (resultSet.next()){

              OrderViewForUserRequest.OrderViewBuilder viewOrderBuilder = new OrderViewForUserRequest.OrderViewBuilder();
                   Order.OrderBuilder order = new Order.OrderBuilder();
                   Car.CarBuilder car = new Car.CarBuilder();
                   OrderViewForUserRequest res =  viewOrderBuilder.setLogin(resultSet.getString("login"))
                                   .setOrder(order.setPassport(resultSet.getString("passport"))
                                           .setReceipt(resultSet.getDouble("receipt"))
                                           .setDateFrom(resultSet.getTimestamp("from_date"))
                                           .setDateTo(resultSet.getTimestamp("to_date"))
                                           .setWithDriver(resultSet.getString("with_driver")).build())
                                   .setCar(car.setName(resultSet.getString("name"))
                                           .setCarClass(resultSet.getString("carClass"))
                                           .setBrand(resultSet.getString("brand")).build()).build();

                                   list.add(res);
               }


        }catch (SQLException e){
            logger.warn("some problem occur manager cant get all users orders approved or not approved!");
            throw new DaoException("Cant find all Orders for Users",e);
        }finally {
            ConnectionPool.closeConnection(connection);
        }
        System.out.println("\nList" + list + "\n");
        return list;
    }
}
