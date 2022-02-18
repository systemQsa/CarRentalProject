package com.myproject.dao.impl;


import com.myproject.dao.OrderDao;
import com.myproject.dao.connection.ConnectionPool;
import com.myproject.dao.query.QuerySQL;
import com.myproject.exception.DaoException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDaoImpl implements OrderDao {
    private Connection connection = null;
    private static final Logger logger = LogManager.getLogger(OrderDaoImpl.class);

    @Override
    public boolean processTheBooking() throws DaoException {
        return false;
    }

    @Override
    public double getDriverRentPrice(int id) throws DaoException{
        connection = ConnectionPool.getInstance().getConnection();
        ResultSet resultSet;
        double rentalPrice = 0;
        try(PreparedStatement statement = connection.prepareStatement(QuerySQL.GET_DRIVER_RENT_PRICE)){
            statement.setInt(1,id);
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                rentalPrice = resultSet.getDouble("price");
            }
        }catch (SQLException e){
            logger.error("CANT GET RENTAL DRIVER PRICE");
            throw new DaoException("CANT GET RENTAL DRIVER PRICE",e);
        }finally {
            ConnectionPool.closeConnection(connection);
        }
        return rentalPrice;
    }
}
