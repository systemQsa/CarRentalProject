package com.myproject.dao;

import com.myproject.dao.entity.Car;
import com.myproject.exception.DaoException;

import java.util.List;

public interface CarDao {
    List<Car> findAll() throws DaoException;
    Car findById(int id) throws DaoException;
    boolean deleteById(int id) throws DaoException;
    boolean update(Car car) throws DaoException;
    Car addRecordToTable(Car car) throws DaoException;
    List<Car> getSortedCars(String neededQuery) throws DaoException;
    List<Car> getSortedCarsByCarClass() throws DaoException;
    List<Car> getSortedCarsByRentPrice() throws DaoException;
    List<Car> getSortedCarsByBrand() throws DaoException;
}
