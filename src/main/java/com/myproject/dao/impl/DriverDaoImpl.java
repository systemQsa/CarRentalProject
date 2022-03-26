package com.myproject.dao.impl;

import com.myproject.dao.DriverDao;
import com.myproject.dao.connection.ConnectManager;
import com.myproject.dao.connection.ConnectionPool;
import com.myproject.dao.query.QuerySQL;
import com.myproject.exception.DaoException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The DriverDaoImpl class represents class to work with Driver entity
 */
public class DriverDaoImpl implements DriverDao {
    private final ConnectManager connectManager;

    private Connection connection;
    private static final Logger logger = LogManager.getLogger(DriverDaoImpl.class);

    public DriverDaoImpl() {
        connectManager = ConnectionPool.getInstance();
    }

    public DriverDaoImpl(ConnectManager connectManager) {
        this.connectManager = connectManager;
    }

    /**
     * The method updates old rental driver price to new one
     *
     * @param price - gets new desired price
     * @return if the new price were successfully updated
     * @throws DaoException in case cannot update the rental driver price
     */
    @Override
    public boolean updateDriverPrice(double price) throws DaoException {
        connection = connectManager.getConnection();
        boolean response = false;
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.UPDATE_DRIVER_PRICE)) {
            statement.setDouble(1, price);
            if (statement.executeUpdate() > 0){
                response = true;
            }
        } catch (SQLException e) {
            logger.warn("Something went wrong can`t update price for driver");
            throw new DaoException("Cannot update driver price");
        } finally {
            connectManager.closeConnection(connection);
        }
        return response;
    }

    /**
     * The method gets driver rental price from DB
     *
     * @return driver rental price
     * @throws DaoException in case cannot get driver rental price
     */
    @Override
    public double getDriverPrice() throws DaoException {
        connection = connectManager.getConnection();
        double price = 0;
        ResultSet resultSet;
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.GET_DRIVER_PRICE)) {
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                price = resultSet.getDouble("price");
            }

        } catch (SQLException e) {
            logger.warn("Something went wrong cannot get Driver Rent price");
            throw new DaoException("Cant get Driver Rental price");
        } finally {
            connectManager.closeConnection(connection);
        }
        return price;
    }
}
