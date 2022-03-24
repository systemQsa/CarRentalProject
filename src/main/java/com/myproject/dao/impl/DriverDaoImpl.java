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

public class DriverDaoImpl implements DriverDao {
    private final ConnectManager connectManager;

    private Connection connection;
    private static final Logger logger = LogManager.getLogger(DriverDaoImpl.class);

    public DriverDaoImpl(){
        connectManager = ConnectionPool.getInstance();
    }

    public DriverDaoImpl(ConnectManager connectManager){
        this.connectManager = connectManager;
    }

    @Override
    public boolean updateDriverPrice(int price) throws DaoException {
        connection = connectManager.getConnection();
        boolean response;
        try(PreparedStatement statement = connection.prepareStatement(QuerySQL.UPDATE_DRIVER_PRICE)){
            statement.setInt(1,price);
            response = statement.execute();
        }catch (SQLException e){
            logger.warn("Something went wrong can`t update price for driver");
            throw new DaoException("Cannot update driver price");
        }finally {
            connectManager.closeConnection(connection);
        }
        return response;
    }

    @Override
    public double getDriverPrice() throws DaoException {
        connection = connectManager.getConnection();
        double price = 0;
        ResultSet resultSet;
        try(PreparedStatement statement = connection.prepareStatement(QuerySQL.GET_DRIVER_PRICE)){
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                price = resultSet.getDouble("price");
            }

        }catch (SQLException e){
            logger.warn("Something went wrong cannot get Driver Rent price");
            throw new DaoException("Cant get Driver Rental price");
        }finally {
            connectManager.closeConnection(connection);
        }
        return price;
    }
}
