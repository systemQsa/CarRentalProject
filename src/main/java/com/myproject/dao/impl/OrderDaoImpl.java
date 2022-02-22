package com.myproject.dao.impl;


import com.myproject.dao.OrderDao;
import com.myproject.dao.connection.ConnectionPool;
import com.myproject.dao.entity.Order;
import com.myproject.dao.query.QuerySQL;
import com.myproject.exception.DaoException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.concurrent.atomic.AtomicReference;

public class OrderDaoImpl implements OrderDao {
    private Connection connection = null;
    private static final Logger logger = LogManager.getLogger(OrderDaoImpl.class);

    @Override
    public boolean setApprovedOrderByManager(String managerLogin, String feedback, String approved, long orderId) throws DaoException {
        connection = ConnectionPool.getInstance().getConnection();
        boolean response = false;
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.SET_APPROVED_ORDER_BY_MANAGER)) {
            statement.setString(1, feedback);
            statement.setString(2, managerLogin);
            statement.setString(3, approved);
            statement.setLong(4, orderId);
            if (statement.executeUpdate() > 0) {
                response = true;
            }
        } catch (SQLException e) {
            logger.warn("Cant approve the order by manager");
            throw new DaoException("SOME PROBLEM CANT APPROVE THE ORDER");
        } finally {
            ConnectionPool.closeConnection(connection);
        }
        return response;
    }

    @Override
    public Order processTheBooking(Order order, String login, int carId,boolean processPayment) throws DaoException {
        connection = ConnectionPool.getInstance().getConnection();
        boolean result = false;
        ResultSet resultSet;
        ResultSet resultSet2;
        AtomicReference<Order> orderAtomicReference = new AtomicReference<>(order);
        AtomicReference<Double> userBalance = new AtomicReference<>();
        double balance = 0;
        int orderId = 0;
        System.out.println("\nOrder      " + order + "\n");
        try (PreparedStatement setOrderToDB =
                     connection.prepareStatement(QuerySQL.INSERT_NEW_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            PreparedStatement getUserBalance = connection.prepareStatement(QuerySQL.GET_USER_BALANCE);
            PreparedStatement setOrderForUser = connection.prepareStatement(QuerySQL.SET_ORDERS_FOR_USER);
            PreparedStatement changeUserBalance = connection.prepareStatement(QuerySQL.TOP_UP_USER_BALANCE);

            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);


            setOrderToDB.setString(1, order.getPassport());
            setOrderToDB.setTimestamp(2, order.getDateFrom());
            setOrderToDB.setTimestamp(3, order.getDateTo());
            setOrderToDB.setString(4, order.getWithDriver());
            setOrderToDB.setDouble(5, order.getReceipt());
            setOrderToDB.setLong(6, order.getUserId());

            //1
            if (setOrderToDB.executeUpdate() > 0) {
                resultSet2 = setOrderToDB.getGeneratedKeys();
                if (resultSet2.next()) {
                    orderId = resultSet2.getInt(1);
                    order.setOrderId(orderId);
                }
            }

            //2
            setOrderForUser.setInt(1, orderId);
            setOrderForUser.setInt(2, carId);
            if (setOrderForUser.executeUpdate() > 0) {
                result = true;
            }


             if (processPayment){

                 //3
                 getUserBalance.setLong(1, order.getUserId());
                 resultSet = getUserBalance.executeQuery();
                 if (resultSet.next()) {
                     balance = resultSet.getDouble("balance");
                     userBalance.set(balance);
                 }
                 Double res = userBalance.get();
                 Double value = res - orderAtomicReference.get().getReceipt();
                 if (value <= 0) {
                     throw new SQLException("NOT ENOUGH BALANCE ON YOUR CARD");
                 }

                 userBalance.compareAndSet(res, value);
                 changeUserBalance.setDouble(1, userBalance.get());
                 changeUserBalance.setString(2, login);
                 if (changeUserBalance.executeUpdate() > 0) {
                     result = true;
                 }
             }

            connection.commit();
            getUserBalance.close();
            setOrderForUser.close();
            changeUserBalance.close();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.fatal("TRANSACTION FAILED CANNOT PROCESS THE PAYMENT");
                throw new DaoException("TRANSACTION FAILED ", e);
            }
            logger.fatal("SOME PROBLEM CANT PROCESS THE USER PAYMENT");
            throw new DaoException("CANT PROCESS THE PAYMENT IN OrderDaoImpl class", e);
        }
        return order;
    }

    @Override
    public double getDriverRentPrice(int id) throws DaoException {
        connection = ConnectionPool.getInstance().getConnection();
        ResultSet resultSet;
        double rentalPrice = 0;
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.GET_DRIVER_RENT_PRICE)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                rentalPrice = resultSet.getDouble("price");
            }
        } catch (SQLException e) {
            logger.error("CANT GET RENTAL DRIVER PRICE");
            throw new DaoException("CANT GET RENTAL DRIVER PRICE", e);
        } finally {
            ConnectionPool.closeConnection(connection);
        }
        return rentalPrice;
    }

}
