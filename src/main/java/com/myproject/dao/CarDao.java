package com.myproject.dao;

import com.myproject.dao.connection.ConnectManager;
import com.myproject.dao.entity.Car;
import com.myproject.exception.DaoException;

import java.util.HashMap;
import java.util.List;

public interface CarDao {

    HashMap<List<Car>, Integer> findAll(int currPage,int noOfRecords) throws DaoException;

    void setConnection(ConnectManager connectManager) throws DaoException;

    Car findById(int id) throws DaoException;

    boolean deleteById(int id) throws DaoException;

    boolean update(Car car) throws DaoException;

    Car addRecordToTable(Car car) throws DaoException;

    List<Car> getSortedCars(String neededQuery, int currPage,int noOfRecords) throws DaoException;

    List<Car> searchCarsByName(String name) throws DaoException;
}
