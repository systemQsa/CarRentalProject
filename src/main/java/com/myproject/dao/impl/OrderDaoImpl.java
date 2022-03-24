package com.myproject.dao.impl;


import com.myproject.dao.OrderDao;
import com.myproject.dao.connection.ConnectManager;
import com.myproject.dao.connection.ConnectionPool;
import com.myproject.dao.entity.Order;
import com.myproject.dao.query.QuerySQL;
import com.myproject.exception.DaoException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.concurrent.atomic.AtomicReference;

public class OrderDaoImpl implements OrderDao {
    private Connection connection = null;
    private static final Logger logger = LogManager.getLogger(OrderDaoImpl.class);
    private ConnectManager connectManager;

    public OrderDaoImpl() {
        connectManager = ConnectionPool.getInstance();
    }

    public OrderDaoImpl(ConnectManager connect) {
        this.connectManager = connect;
    }

    @Override
    public void setConnection(ConnectManager connectManager) {
        this.connectManager = connectManager;
    }

    public OrderDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean setApprovedOrderByManager(String managerLogin, String feedback, String approved, long orderId) throws DaoException {
        connection = connectManager.getConnection();
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
            connectManager.closeConnection(connection);
        }
        return response;
    }

    @Override
    public boolean checkIfSuchOrderExistsInDb(Order order) throws DaoException {
        System.out.println("\nchecking the order!!!! " + order.getUserLogin() + " " + order.getCarId());
        connection = connectManager.getConnection();
        ResultSet suchOrderInDB = null;
        String query = "SELECT count(user_id) as record FROM orders JOIN orders_cars " +
                "ON orders.id_order=orders_cars.order_id passport="+order.getPassport() + " AND from_date="+order.getFromDate()
                 + " AND to_date=" + order.getToDate() +
                " AND with_driver="+order.getWithDriver() + " AND receipt=" +order.getReceipt() +
                " AND user_id="+order.getUserId() + " AND car_id=" + order.getCarId();

        System.out.println(query);
        try(PreparedStatement checkOrderPresence = connection.prepareStatement(QuerySQL.CHECK_IF_ORDER_ALREADY_PRESENT_IN_DB_BY_USER)){


            checkOrderPresence.setString(1, order.getPassport());
            checkOrderPresence.setTimestamp(2,order.getDateFrom());
            checkOrderPresence.setTimestamp(3,order.getDateTo());
            checkOrderPresence.setString(4,order.getWithDriver());
            checkOrderPresence.setDouble(5,order.getReceipt());
            checkOrderPresence.setLong(6,order.getUserId());
            checkOrderPresence.setInt(7,order.getCarId());

            suchOrderInDB = checkOrderPresence.executeQuery();

            System.out.println("=====1=====");
            System.out.println("=====2=====");
            if (suchOrderInDB.next()) {
                System.out.println("=====3=====");
                int records = suchOrderInDB.getInt(1);

                System.out.println("number "  + records);
                if (records > 1) {
                    System.out.println("order exists!!");
                    connection.rollback();
                    throw new DaoException("You have made this booking already!");
                }
            }
            System.out.println("=====4=====");

        }catch (SQLException e){
            throw new DaoException("You have made this booking already!");
        }finally {
            connectManager.closeConnection(connection);
        }
        return false;
    }

    @Override
    public Order processTheBooking(Order order, boolean processPayment) throws DaoException {
        connection = connectManager.getConnection();
        boolean result = false;
        ResultSet resultSet;
        ResultSet resultSet2;
        ResultSet suchOrderInDB;
        AtomicReference<Order> orderAtomicReference = new AtomicReference<>(order);
        AtomicReference<Double> userBalance = new AtomicReference<>();
        double balance = 0;
        int orderId = 0;
        try (PreparedStatement setOrderToDB =
                     connection.prepareStatement(QuerySQL.INSERT_NEW_ORDER, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement getUserBalance =
                     connection.prepareStatement(QuerySQL.GET_USER_BALANCE);
             PreparedStatement setOrderForUser =
                     connection.prepareStatement(QuerySQL.SET_ORDERS_FOR_USER);
             PreparedStatement changeUserBalance =
                     connection.prepareStatement(QuerySQL.TOP_UP_USER_BALANCE)) {

            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            setOrderToDB.setString(1, order.getPassport());
            setOrderToDB.setTimestamp(2, order.getDateFrom());
            setOrderToDB.setTimestamp(3, order.getDateTo());
            setOrderToDB.setString(4, order.getWithDriver());
            setOrderToDB.setDouble(5, order.getReceipt());
            setOrderToDB.setLong(6, order.getUserId());
            System.out.println("====1====" + order.getUserLogin() + "id " + order.getUserId());
            //1
            if (setOrderToDB.executeUpdate() > 0) {
                resultSet2 = setOrderToDB.getGeneratedKeys();
                if (resultSet2.next()) {
                    orderId = resultSet2.getInt(1);
                    order.setOrderId(orderId);
                }
            }
            System.out.println("====2====");
            //2
            setOrderForUser.setInt(1, orderId);
            setOrderForUser.setInt(2, order.getCarId());
            if (setOrderForUser.executeUpdate() > 0) {
                result = true;
            }

            System.out.println("====3====");
            if (processPayment) {

                //3
                getUserBalance.setLong(1, order.getUserId());
                System.out.println("user id " + order.getUserId() + " " + order.getUserLogin());
                resultSet = getUserBalance.executeQuery();
                if (resultSet.next()) {
                    balance = resultSet.getDouble("balance");
                    userBalance.set(balance);
                    System.out.println("balance " + balance);
                }
                System.out.println("====4====");
                Double res = userBalance.get();
                double value = res - orderAtomicReference.get().getReceipt();
                System.out.println("value " + value);
                if (value <= 0) {
                    logger.warn("NOT ENOUGH BALANCE ON CARD");
                    throw new SQLException("NOT ENOUGH BALANCE ON YOUR CARD");
                }
                System.out.println("====5====");
                userBalance.compareAndSet(res, value);
                changeUserBalance.setDouble(1, userBalance.get());
                changeUserBalance.setString(2, order.getUserLogin());
                if (changeUserBalance.executeUpdate() > 0) {
                    result = true;
                }
            }
            System.out.println("====6====");
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.fatal("TRANSACTION FAILED CANNOT PROCESS THE PAYMENT");
                throw new DaoException("TRANSACTION FAILED ");
            }
            logger.fatal("SOME PROBLEM CANT PROCESS THE USER PAYMENT");
            throw new DaoException("CANT PROCESS THE PAYMENT IN OrderDaoImpl class");
        } finally {
            connectManager.closeConnection(connection);
        }
        System.out.println("order receipt " + order.getReceipt() + " order id " + order.getOrderId());
        return order;
    }

    @Override
    public double getDriverRentPrice(int id) throws DaoException {
        connection = connectManager.getConnection();
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
            connectManager.closeConnection(connection);
        }
        return rentalPrice;
    }

}
