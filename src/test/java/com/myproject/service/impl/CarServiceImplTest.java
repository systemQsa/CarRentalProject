package com.myproject.service.impl;

import com.myproject.dao.CarDao;
import com.myproject.dao.connection.DBManager;
import com.myproject.dao.entity.Car;
import com.myproject.dao.impl.CarDaoImpl;
 import com.myproject.exception.ServiceException;
import com.myproject.service.CarService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class CarServiceImplTest {
    private DBManager dbManager;

    @BeforeClass
    public static void beforeTesting() {
        DBManager.getInstance().loadScript();
    }

    @Test
    public void getOneCar() {
        dbManager = DBManager.getInstance();
        CarDao carDao = new CarDaoImpl(dbManager);
        CarService<Car> carService = new CarServiceImpl(carDao);

        try {
             carService.getOneCar(1).ifPresent(oneCar-> assertEquals("porsche", oneCar.getName()));
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getCarByName() {
        dbManager = DBManager.getInstance();
        CarDao carDao = new CarDaoImpl(dbManager);
        CarService<Car> carService = new CarServiceImpl(carDao);

        try {
            Optional<List<Car>> car = carService.getCar("infinity");
            car.ifPresent(value -> assertEquals("A", value.get(0).getCarClass()));
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getSortedCars() {
        dbManager = DBManager.getInstance();
        CarDao carDao = new CarDaoImpl(dbManager);
        CarService<Car> carService = new CarServiceImpl(carDao);

        try {
            Optional<List<Car>> res = carService.getSortedCars("sortCarsByName", 1,2);
            res.ifPresent(val->assertEquals("infinity",val.get(0).getName()));
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void updateCar() {
        dbManager = DBManager.getInstance();
        CarDao carDao = new CarDaoImpl(dbManager);
        CarService<Car> carService = new CarServiceImpl(carDao);
        Car.CarBuilder car = new Car.CarBuilder();
        car.setName("mazda")
                .setCarClass("C")
                .setBrand("mazda")
                .setRentalPrice(25.5)
                .setCarId(3);
        try {
            assertTrue(carService.updateCar(car.build()));
        } catch (ServiceException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void getAllCars() {
        dbManager = DBManager.getInstance();
        CarDao carDao = new CarDaoImpl(dbManager);
        CarService<Car> carService = new CarServiceImpl(carDao);
        List<Car> carList = null;
        try {
            Optional<HashMap<List<Car>, Integer>> allCars = carService.getAllCars(1,2);
            if (allCars.isPresent()){
                carList = allCars.get().keySet().stream().findFirst().orElse(null);

            }
            assertTrue(carList.size() != 0);
            assertEquals(2,carList.size());
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void deleteCar() {
        dbManager = DBManager.getInstance();
        CarDao carDao = new CarDaoImpl(dbManager);
        CarService<Car> carService = new CarServiceImpl(carDao);
        try {
            assertTrue(carService.deleteCar(3));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

}
