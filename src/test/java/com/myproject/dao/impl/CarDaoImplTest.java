package com.myproject.dao.impl;


import com.myproject.dao.connection.DBManager;
import com.myproject.dao.entity.Car;
import com.myproject.dao.query.QuerySQL;
import com.myproject.exception.DaoException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class CarDaoImplTest {
    private static DBManager dbManager;


    @BeforeClass
    public static void beforeTests() {
        DBManager.getInstance().loadScript();
    }

    @Test
    public void getCarByName() {
        dbManager = DBManager.getInstance();
        CarDaoImpl carDao = new CarDaoImpl(dbManager);

        try {
            assertEquals("A", carDao.searchCarsByName("infinity").get(0).getCarClass());
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void findAll() {
        dbManager = DBManager.getInstance();
        CarDaoImpl carDao = new CarDaoImpl(dbManager);
        try {
            HashMap<List<Car>, Integer> map = carDao.findAll(1,1);
            Optional<Integer> records = map.values().stream().findFirst();
            int amountOfRecords = 0;
            if (records.isPresent()) {
                amountOfRecords = records.get();
            }
            assertEquals(3, amountOfRecords);

        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findById() {
        dbManager = DBManager.getInstance();
        CarDaoImpl carDao = new CarDaoImpl(dbManager);

        try {
            assertEquals("toyota", carDao.findById(3).getName());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void update() {
        dbManager = DBManager.getInstance();
        CarDaoImpl carDao = new CarDaoImpl(dbManager);
        Car.CarBuilder car = new Car.CarBuilder();
        car.setCarId(3)
                .setName("toyota")
                .setCarClass("B")
                .setBrand("toyota")
                .setRentalPrice(15.5);
        try {
            assertTrue(carDao.update(car.build()));
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addRecordToTable() {

        Car.CarBuilder newCar = new Car.CarBuilder();
        newCar.setName("audi")
                .setCarClass("A")
                .setBrand("audi")
                .setRentalPrice(115.5);
        dbManager = DBManager.getInstance();
        CarDaoImpl carDao = new CarDaoImpl(dbManager);
        try {
            assertEquals("audi", carDao.addRecordToTable(newCar.build()).getBrand());
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getSortedCars() {
        dbManager = DBManager.getInstance();
        CarDaoImpl carDao = new CarDaoImpl(dbManager);

        try {
            List<Car> cars = carDao.getSortedCars(QuerySQL.GET_ALL_CARS_SORT_BY_RENT_PRICE, 1,3);
            assertEquals("porsche", cars.get(0).getName());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteById() {
        dbManager = DBManager.getInstance();
        CarDaoImpl carDao = new CarDaoImpl(dbManager);
        try {
            assertTrue(carDao.deleteById(2));
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
