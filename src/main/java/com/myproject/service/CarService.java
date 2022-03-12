package com.myproject.service;

import com.myproject.dao.entity.Car;
import com.myproject.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface CarService<Car> {
    Car addCar(HttpServletRequest request) throws ServiceException;

    boolean deleteCar(int carId) throws ServiceException;

    Optional<HashMap<List<Car>,Integer>> getAllCars(int currentPage) throws ServiceException;

    Car getOneCar(int carId) throws ServiceException;

    boolean updateCar(Car car) throws ServiceException;

    Optional<List<Car>> getSortedCars(String sortOrder,int currPage) throws ServiceException;

    Optional<Car> getCar(String name) throws ServiceException;

}
