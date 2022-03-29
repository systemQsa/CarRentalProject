package com.myproject.dao.impl;

import com.myproject.dao.CarDao;
import com.myproject.dao.connection.ConnectManager;
import com.myproject.dao.connection.ConnectionPool;
import com.myproject.dao.entity.Car;
import com.myproject.dao.query.QuerySQL;
import com.myproject.exception.DaoException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The CarDaoImpl class represents class to work with Car entity and provides all necessary methods for work
 */
public class CarDaoImpl implements CarDao {
    private Connection connection = null;
    private static final Logger logger = LogManager.getLogger(CarDaoImpl.class);
    private ConnectManager connectManager;


    public CarDaoImpl() {
        connectManager = ConnectionPool.getInstance();
    }

    @Override
    public void setConnection(ConnectManager connectManager) {
        this.connectManager = connectManager;
    }

    public CarDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public CarDaoImpl(ConnectManager connect) {
        this.connectManager = connect;
    }


    /**
     * The method search car(s) by given name
     * @param name - gets the desired car name
     * @return all founded cars
     * @throws DaoException in case cannot get all cars
     */
    @Override
    public List<Car> searchCarsByName(String name) throws DaoException {
        connection = connectManager.getConnection();
        ResultSet resultSet;
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.SEARCH_CAR_BY_NAME)) {
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Car.CarBuilder car = new Car.CarBuilder();
                logger.info("connection successfully got process the searching the car");
                car.setCarId(resultSet.getInt("id_car"))
                        .setName(resultSet.getString("name"))
                        .setCarClass(resultSet.getString("carClass"))
                        .setBrand(resultSet.getString("brand"))
                        .setRentalPrice(resultSet.getDouble("rent_price"));
                cars.add(car.build());
            }

        } catch (SQLException e) {
            throw new DaoException("Cant find Car by given name");
        } finally {
            connectManager.closeConnection(connection);
        }
        return cars;
    }

    /**
     * The method get all the cars from DB and amount of them
     * @param currPage - gets the current web page
     * @param noOfRecords - gets desired amount of records
     * @return all cars that was found in DB
     * @throws DaoException in case cannot get all cars
     */
    @Override
    public HashMap<List<Car>, Integer> findAll(int currPage,int noOfRecords) throws DaoException {
        List<Car> carList = new ArrayList<>();
        HashMap<List<Car>, Integer> map = new HashMap<>();
        ResultSet resultOfRecords;
        int records = 0;
        int start = currPage * noOfRecords - noOfRecords;
        connection = connectManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.GET_ALL_CARS)) {
            PreparedStatement statement2 = connection.prepareStatement(QuerySQL.GET_CARS_TOTAL_RECORDS);
            connection.setAutoCommit(false);
            resultOfRecords = statement2.executeQuery();
            if (resultOfRecords.next()) {
                records = resultOfRecords.getInt("records");
            }
            statement.setInt(1, start);
            statement.setInt(2, noOfRecords);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Car.CarBuilder carBuilder = new Car.CarBuilder();
                logger.info("connected to the db getting all the cars");
                carBuilder.setCarId(resultSet.getInt("id_car"))
                        .setName(resultSet.getString("name"))
                        .setCarClass(resultSet.getString("carClass"))
                        .setBrand(resultSet.getString("brand"))
                        .setRentalPrice(resultSet.getDouble("rent_price"));
                carList.add(carBuilder.build());
            }
            connection.commit();

        } catch (SQLException e) {
            logger.error("CANT GET ALL CARS FROM DB");
            throw new DaoException("CANT GET ALL CARS", e);
        } finally {
            connectManager.closeConnection(connection);
        }
        map.put(carList, records);
        logger.info("ALL CARS WAS FOUND IN DB SUCCESSFULLY");
        return map;
    }

    /**
     * The method searches the car by given id
     * @param carId -gets car id
     * @return founded car
     * @throws DaoException in case cannot find the car by given id
     */
    @Override
    public Car findById(int carId) throws DaoException {
        connection = connectManager.getConnection();
        Car.CarBuilder carBuilder = new Car.CarBuilder();
        ResultSet resultSet;
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.GET_ONE_CAR_BY_ID)) {
            statement.setInt(1, carId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                carBuilder.setCarId(resultSet.getInt("id_car"))
                        .setName(resultSet.getString("name"))
                        .setCarClass(resultSet.getString("carClass"))
                        .setBrand(resultSet.getString("brand"))
                        .setRentalPrice(Double.parseDouble(resultSet.getString("rent_price")))
                        .setPhoto(resultSet.getString("photo"));
            }
        } catch (SQLException e) {
            logger.error("CANT FIND CAR BY GIVEN ID " + carId);
            throw new DaoException("CANT FIND CAR BY GIVEN ID", e);
        } finally {
            connectManager.closeConnection(connection);
        }
        logger.info("CAR WAS FOUND IN DB BY GIVEN ID " + carId + " SUCCESSFULLY");
        return carBuilder.build();
    }

    /**
     * The method deleted the car by given id
     * @param carId - gets car id
     * @return if the car was deleted returns true
     * @throws DaoException in case the car cannot be deleted
     */
    @Override
    public boolean deleteById(int carId) throws DaoException {
        boolean success = false;
        connection = connectManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.DELETE_CAR)) {
            statement.setInt(1, carId);
            if (statement.executeUpdate() > 0) {
                success = true;
            }
        } catch (SQLException e) {
            logger.error("CANT DELETE CAR BY GIVEN ID " + carId);
            throw new DaoException("CANT DELETE CAR", e);
        } finally {
            connectManager.closeConnection(connection);
        }
        logger.info("THE CAR DELETED SUCCESSFULLY");
        return success;
    }

    /**
     * The method updates given car
     * @param car - gets car with some updates
     * @return id car was updated returns true
     * @throws DaoException in case cannot update the given car
     */
    @Override
    public boolean update(Car car) throws DaoException {
        boolean response = false;
        connection = connectManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.UPDATE_CAR)) {
            statement.setString(1, car.getName());
            statement.setString(2, car.getCarClass());
            statement.setString(3, car.getBrand());
            statement.setDouble(4, car.getRentalPrice());
            statement.setString(5, car.getPhoto());
            statement.setInt(6, car.getCarId());
            if (statement.executeUpdate() > 0) {
                response = true;
            }
        } catch (SQLException e) {
            logger.error("CANT UPDATE CAR SOME PROBLEM OCCUR");
            throw new DaoException("CANT UPDATE CAR ", e);
        } finally {
            connectManager.closeConnection(connection);
        }
        logger.info("THE CAR UPDATED SUCCESSFULLY");
        return response;
    }

    /**
     * The method adds a new car to the table and checks if such car already exists in DB
     * @param car _ gets a new car
     * @return the added car
     * @throws DaoException in case car was not added to the table in DB
     */
    @Override
    public Car addRecordToTable(Car car) throws DaoException {
        ResultSet resultSet;
        ResultSet suchCarInDB;
        Car.CarBuilder carBuilder = new Car.CarBuilder();
        connection = connectManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.ADD_CAR, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement checkCarPresence = connection.prepareStatement(QuerySQL.CHECK_IF_CAR_ALREADY_PRESENT_IN_DB)) {

            connection.setAutoCommit(false);
            checkCarPresence.setString(1, car.getName());
            checkCarPresence.setString(2, car.getCarClass());
            checkCarPresence.setString(3, car.getBrand());
            checkCarPresence.setDouble(4, car.getRentalPrice());

            suchCarInDB = checkCarPresence.executeQuery();
            if (suchCarInDB.next()) {
                if ((suchCarInDB.getString("name") != null
                        && (suchCarInDB.getString("carClass") != null)
                        && (suchCarInDB.getString("brand") != null))) {
                    connection.rollback();
                        throw new DaoException("Car already exists in DataBase");

                }
            } else {
                System.out.println("NO SUCH CAR WAS FOUND!");
                statement.setString(1, car.getName());
                statement.setString(2, car.getCarClass());
                statement.setString(3, car.getBrand());
                statement.setDouble(4, car.getRentalPrice());
                statement.setString(5, car.getPhoto());
                if (statement.executeUpdate() > 0) {
                    resultSet = statement.getGeneratedKeys();
                    if (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        carBuilder.setCarId(id)
                                .setName(car.getName())
                                .setBrand(car.getBrand())
                                .setCarClass(car.getCarClass())
                                .setRentalPrice(car.getRentalPrice())
                                .setPhoto(car.getPhoto());
                    }
                }
            }

            connection.commit();
        } catch (SQLException e) {
            logger.error("CANT ADD A NEW CAR TO DB");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException("CANT ADD A NEW CAR TO DATA", e);
            }
            throw new DaoException("CANT ADD A NEW CAR TO DATA", e);
        } finally {
            connectManager.closeConnection(connection);
        }
        logger.info("A NEW CAR ADDED TO DB SUCCESS");
        return carBuilder.build();
    }

    /**
     * The method sorts cars by desired order
     * @param neededQuery - gets desired query that will be sort cars by desired order
     * @param currPage - gets current page  from web page
     * @param noOfRecords - gets desired amount of records
     * @return all founded sorted cars
     * @throws DaoException in case cannot sort or find cars
     */
    @Override
    public List<Car> getSortedCars(String neededQuery, int currPage,int noOfRecords) throws DaoException {
        connection = connectManager.getConnection();
        ResultSet resultSet;
        int start = currPage * noOfRecords - noOfRecords;
        List<Car> carList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(neededQuery)) {
            statement.setInt(1, start);
            statement.setInt(2, noOfRecords);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Car.CarBuilder car = new Car.CarBuilder();
                car.setCarId(resultSet.getInt("id_car"))
                        .setName(resultSet.getString("name"))
                        .setCarClass(resultSet.getString("carClass"))
                        .setBrand(resultSet.getString("brand"))
                        .setRentalPrice(resultSet.getDouble("rent_price"));
                carList.add(car.build());
            }

        } catch (SQLException e) {
            logger.error("CANT SORT CARS BY GIVEN QUERY OR SOME OTHER PROBLEM HAPPENED");
            throw new DaoException("CANT  SORTED CARS BY GIVEN QUERY", e);
        } finally {
            connectManager.closeConnection(connection);
        }
        logger.info("CARS WERE SORTED SUCCESSFULLY");
        return carList;
    }
}
