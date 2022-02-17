package com.myproject.dao.impl;

import com.myproject.dao.CarDao;
import com.myproject.dao.connection.ConnectionPool;
import com.myproject.dao.entity.Car;
import com.myproject.dao.query.QuerySQL;
import com.myproject.exception.DaoException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {
    private  Connection connection = null;
    private static final Logger logger = LogManager.getLogger(CarDaoImpl.class);

    @Override
    public List<Car> findAll() throws DaoException {
        List<Car> carList = new ArrayList<>();
        try{
            connection = ConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QuerySQL.GET_ALL_CARS);

            while (resultSet.next()){
                Car.CarBuilder carBuilder = new Car.CarBuilder();
                carBuilder.setCarId(resultSet.getInt("id_car"))
                        .setName(resultSet.getString("name"))
                        .setBrand(resultSet.getString("carClass"))
                        .setCarClass(resultSet.getString("brand"))
                        .setRentalPrice(resultSet.getDouble("rent_price"));
                      carList.add(carBuilder.build());
            }

        }catch (SQLException e){
            logger.error("CANT GET ALL CARS FROM DB");
           throw new DaoException("CANT GET ALL CARS",e);
        }finally {
            ConnectionPool.closeConnection(connection);
        }
        logger.info("ALL CARS WAS FOUND IN DB SUCCESSFULLY");
        return carList;
    }

    @Override
    public Car findById(int carId) throws DaoException {
        connection = ConnectionPool.getInstance().getConnection();
        Car.CarBuilder carBuilder = new Car.CarBuilder();
        ResultSet resultSet;
        System.out.println("car id " + carId);
        try(PreparedStatement statement = connection.prepareStatement(QuerySQL.GET_ONE_CAR_BY_ID)){
            statement.setInt(1,carId);
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                carBuilder.setCarId(carId)
                        .setName(resultSet.getString("name"))
                        .setCarClass(resultSet.getString("carClass"))
                        .setBrand(resultSet.getString("brand"))
                        .setRentalPrice(Double.parseDouble(resultSet.getString("rent_price")))
                        .setPhoto(resultSet.getString("photo"));
            }
        }catch (SQLException e){
            logger.error("CANT FIND CAR BY GIVEN ID " + carId);
            throw new DaoException("CANT FIND CAR BY GIVEN ID",e);
        }finally {
            ConnectionPool.closeConnection(connection);
        }
        logger.info("CAR WAS FOUND IN DB BY GIVEN ID "+ carId + " SUCCESSFULLY");
        return carBuilder.build();
    }

    @Override
    public boolean deleteById(int carId) throws DaoException {
        boolean success = false;
        connection = ConnectionPool.getInstance().getConnection();
        try(PreparedStatement statement = connection.prepareStatement(QuerySQL.DELETE_CAR)){
            statement.setInt(1,carId);
            if (statement.executeUpdate() > 0){
                success = true;
            }
        }catch (SQLException e){
            logger.error("CANT DELETE CAR BY GIVEN ID " + carId);
            throw new DaoException("CANT DELETE CAR",e);
        }finally {
            ConnectionPool.closeConnection(connection);
        }
        logger.info("THE CAR DELETED SUCCESSFULLY");
        return success;
    }

    @Override
    public boolean update(Car car) throws DaoException{
        boolean response = false;
         connection = ConnectionPool.getInstance().getConnection();
         try(PreparedStatement statement = connection.prepareStatement(QuerySQL.UPDATE_CAR)){
              statement.setString(1, car.getName());
              statement.setString(2, car.getCarClass());
              statement.setString(3, car.getCarClass());
              statement.setDouble(4,car.getRentalPrice());
              statement.setString(5, car.getPhoto());
              statement.setInt(6,car.getCarId());
              if (statement.executeUpdate() > 0){
                  response = true;
              }
         }catch (SQLException e){
             logger.error("CANT UPDATE CAR SOME PROBLEM OCCUR");
             throw new DaoException("CANT UPDATE CAR ",e);
         }finally {
             ConnectionPool.closeConnection(connection);
         }
        logger.info("THE CAR UPDATED SUCCESSFULLY");
        return response;
    }

    @Override
    public Car addRecordToTable(Car car) throws DaoException {
         ResultSet resultSet;
         Car.CarBuilder carBuilder = new Car.CarBuilder();
         connection = ConnectionPool.getInstance().getConnection();
         try(PreparedStatement statement = connection.prepareStatement(QuerySQL.ADD_CAR,Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1,car.getName());
            statement.setString(2, car.getCarClass());
            statement.setString(3, car.getBrand());
            statement.setDouble(4,car.getRentalPrice());
            statement.setString(5,car.getPhoto());
            if (statement.executeUpdate() > 0){
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()){
                    int id = resultSet.getInt(1);
                    carBuilder.setCarId(id)
                            .setName(car.getName())
                            .setBrand(car.getBrand())
                            .setCarClass(car.getCarClass())
                            .setRentalPrice(car.getRentalPrice())
                            .setPhoto(car.getPhoto());
                }
            }
         }catch (SQLException e){
             logger.error("CANT ADD A NEW CAR TO DB");
            throw new DaoException("CANT ADD A NEW CAR TO DATA",e);
         }finally {
             ConnectionPool.closeConnection(connection);
         }
         logger.info("A NEW CAR ADDED TO DB SUCCESS");
        return carBuilder.build();
    }

    @Override
    public List<Car> getSortedCars(String neededQuery) throws DaoException{
        connection = ConnectionPool.getInstance().getConnection();
        ResultSet resultSet;
        List<Car>carList = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(neededQuery)){
            resultSet = statement.executeQuery();
           while (resultSet.next()){
               Car.CarBuilder car = new Car.CarBuilder();
               car.setCarId(resultSet.getInt("id_car"))
                       .setName(resultSet.getString("name"))
                       .setCarClass(resultSet.getString("carClass"))
                       .setBrand(resultSet.getString("brand"))
                       .setRentalPrice(resultSet.getDouble("rent_price"));
               carList.add(car.build());
           }

        }catch (SQLException e){
            logger.error("CANT SORT CARS BY GIVEN QUERY OR SOME OTHER PROBLEM HAPPENED");
            throw new DaoException("CANT  SORTED CARS BY GIVEN QUERY",e);
        }finally {
            ConnectionPool.closeConnection(connection);
        }
        logger.info("CARS WERE SORTED SUCCESSFULLY");
        return carList;
    }

    @Override   //todo check if this method needed
    public List<Car> getSortedCarsByCarClass() throws DaoException{
        connection = ConnectionPool.getInstance().getConnection();
        ResultSet resultSet;
        List<Car> carList = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(QuerySQL.GET_ALL_CARS_SORT_BY_CAR_CLASS)){
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                Car.CarBuilder car = new Car.CarBuilder();
                car.setName(resultSet.getString("name"))
                        .setCarClass(resultSet.getString("carClass"))
                        .setBrand(resultSet.getString("brand"))
                        .setRentalPrice(resultSet.getDouble("rent_price"));
                carList.add(car.build());
            }

        }catch (SQLException e){
            throw new DaoException("CANT GET ALL SORTED CARS BY CAR CLASS",e);
        }finally {
            ConnectionPool.closeConnection(connection);
        }
        return carList;
    }

    @Override
    public List<Car> getSortedCarsByRentPrice() {
        return null;
    }

    @Override
    public List<Car> getSortedCarsByBrand() {
        return null;
    }
}
