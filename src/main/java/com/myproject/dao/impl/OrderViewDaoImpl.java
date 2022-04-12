package com.myproject.dao.impl;

import com.myproject.dao.OrderViewDao;
import com.myproject.dao.connection.ConnectManager;
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

/**
 * The OrderViewDaoImpl class represents the class to work with OrderViewForUserRequest entity and provides necessary methods for it
 */
public class OrderViewDaoImpl implements OrderViewDao {
    private Connection connection = null;
    private static final Logger logger = LogManager.getLogger(OrderViewDaoImpl.class);
    private ConnectManager connectManager;

    public OrderViewDaoImpl() {
        connectManager = ConnectionPool.getInstance();
    }

    public OrderViewDaoImpl(ConnectManager connect) {
        this.connectManager = connect;
    }

    @Override
    public void setConnection(ConnectManager connectManager) {
        this.connectManager = connectManager;
    }

    /**
     * The method gets all orders that were made by given user (all approved and declined)
     *
     * @param login       - gets user login
     * @param startPage   - gets current web page
     * @param noOfRecords - gets number of records
     * @return all founded orders
     * @throws DaoException in case cannot find orders
     */
    @Override
    public List<OrderViewForUserRequest> getOrdersForUser(String login,
                                                          int startPage, int noOfRecords) throws DaoException {
        connection = connectManager.getConnection();
        ResultSet resultSet;
        ResultSet totalTableRecords;
        int start = startPage * noOfRecords - noOfRecords;
        int temp = 0;
        List<OrderViewForUserRequest> list = new ArrayList<>();

        try (PreparedStatement statement =
                     connection.prepareStatement("SELECT " + QuerySQL.ALL_ORDERS_USER_VIEW + "LIMIT ?,?")) {
            PreparedStatement countTotalRecordsInTable =
                    connection.prepareStatement("SELECT COUNT(user_id) AS records," + QuerySQL.ALL_ORDERS_USER_VIEW);
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            countTotalRecordsInTable.setString(1, login);
            totalTableRecords = countTotalRecordsInTable.executeQuery();
            if (totalTableRecords.next()) {
                temp = totalTableRecords.getInt("records");
            }
            statement.setString(1, login);
            statement.setInt(2, start);
            statement.setInt(3, noOfRecords);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                OrderViewForUserRequest.OrderViewBuilder orderViewBuilder =
                        new OrderViewForUserRequest.OrderViewBuilder();
                Car.CarBuilder carBuilder = new Car.CarBuilder();
                Order.OrderBuilder order = new Order.OrderBuilder();

                OrderViewForUserRequest res = orderViewBuilder
                        .setAmountOfRecords(temp)
                        .setOrder(order.setPassport(resultSet.getString("passport"))
                                .setDateFrom(resultSet.getTimestamp("from_date").toLocalDateTime())
                                .setDateTo(resultSet.getTimestamp("to_date").toLocalDateTime())
                                .setWithDriver(resultSet.getString("with_driver"))
                                .setReceipt(resultSet.getDouble("receipt")).build())
                        .setCar(carBuilder.setName(resultSet.getString("name"))
                                .setCarClass(resultSet.getString("carClass"))
                                .setBrand(resultSet.getString("brand")).build())
                        .setFeedback(resultSet.getString("feedback"))
                        .setApproved(resultSet.getString("approved")).build();

                list.add(res);
            }
            connection.commit();
            countTotalRecordsInTable.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException(" Oops! Cant get all your orders.");
            }
            logger.warn("Some problem cant get all user orders");
            throw new DaoException(" Oops! Cant get all your orders.");
        } finally {
            connectManager.closeConnection(connection);
        }
        return list;
    }

    /**
     * The method returns all orders for manager
     *
     * @param approved    - gets desired order status
     * @param startPage   - gets current web page
     * @param noOfRecords - gets number of records to be found
     * @return all founded orders
     * @throws DaoException in case cannot find orders
     */
    @Override
    public List<OrderViewForUserRequest> getOrdersForManager(String approved, int startPage, int noOfRecords) throws DaoException {
        connection = connectManager.getConnection();
        int temp = 0;
        int start = startPage * noOfRecords - noOfRecords;
        ResultSet totalRecords;
        ResultSet resultSet;
        List<OrderViewForUserRequest> list = new ArrayList<>();
        try (PreparedStatement getRequiredAmountOfRecordsStatement =
                     connection.prepareStatement("SELECT " + QuerySQL.VIEW_ALL_APPROVED_OR_NOT_APPROVED_ORDERS_BY_MANAGER + " LIMIT ?,?")) {
            PreparedStatement availableRecordsStatement =
                    connection.prepareStatement("SELECT COUNT(user_id) as records," + QuerySQL.VIEW_ALL_APPROVED_OR_NOT_APPROVED_ORDERS_BY_MANAGER);
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            availableRecordsStatement.setString(1, approved);
            totalRecords = availableRecordsStatement.executeQuery();
            logger.info("Total records are were given in OrderViewDaoImpl class");
            if (totalRecords.next()) {
                temp = totalRecords.getInt("records");
            }

            getRequiredAmountOfRecordsStatement.setString(1, approved);
            getRequiredAmountOfRecordsStatement.setInt(2, start);
            getRequiredAmountOfRecordsStatement.setInt(3, noOfRecords);
            resultSet = getRequiredAmountOfRecordsStatement.executeQuery();
            while (resultSet.next()) {

                OrderViewForUserRequest.OrderViewBuilder viewOrderBuilder =
                        new OrderViewForUserRequest.OrderViewBuilder();
                Order.OrderBuilder order = new Order.OrderBuilder();
                Car.CarBuilder car = new Car.CarBuilder();
                OrderViewForUserRequest res =
                        viewOrderBuilder.setAmountOfRecords(temp)
                                .setLogin(resultSet.getString("login"))
                                .setOrder(order.setPassport(resultSet.getString("passport"))
                                        .setReceipt(resultSet.getDouble("receipt"))
                                        .setDateFrom(resultSet.getTimestamp("from_date").toLocalDateTime())
                                        .setDateTo(resultSet.getTimestamp("to_date").toLocalDateTime())
                                        .setWithDriver(resultSet.getString("with_driver")).build())
                                .setCar(car.setName(resultSet.getString("name"))
                                        .setCarClass(resultSet.getString("carClass"))
                                        .setBrand(resultSet.getString("brand")).build()).build();

                list.add(res);
            }

            availableRecordsStatement.close();
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException("Cant find all Orders for Users", e);
            }
            logger.warn("some problem occur manager cant get all users orders approved or not approved!");
            throw new DaoException("Cant find all Orders for Users", e);
        } finally {
            connectManager.closeConnection(connection);
        }
        return list;
    }
}
