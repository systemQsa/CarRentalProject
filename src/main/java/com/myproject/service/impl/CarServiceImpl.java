package com.myproject.service.impl;

import com.myproject.dao.CarDao;
import com.myproject.dao.connection.ConnectManager;
import com.myproject.dao.entity.Car;
import com.myproject.dao.impl.CarDaoImpl;
import com.myproject.dao.query.QuerySQL;
import com.myproject.exception.DaoException;
import com.myproject.exception.ServiceException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.CarService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CarServiceImpl implements CarService<Car> {
    private final CarDao carDAO = new AbstractFactoryImpl().getFactory().getDaoFactory().getCarDao();
    private static final Logger logger = LogManager.getLogger(CarServiceImpl.class);

    public CarServiceImpl(){}

    @Override
    public Car addCar(HttpServletRequest request) throws ServiceException{
        Car car;
        try {
            car = carDAO.addRecordToTable(
                     new Car.CarBuilder()
                            .setName(request.getParameter("name"))
                            .setCarClass(request.getParameter("carClass"))
                            .setBrand(request.getParameter("brand"))
                            .setRentalPrice(Double.parseDouble(request.getParameter("rentalPrice")))
                            .setPhoto(request.getParameter("photo"))
                            .build());
        } catch (DaoException e) {
            logger.warn(" CANT ADD A NEW CAR IN CarServiceImpl class");
            throw new ServiceException("CANT ADD NEW CAR IN SERVICE",e);
        }
        return car;
    }

    @Override
    public boolean deleteCar(int carId) throws ServiceException{
        boolean response;
        try {
            response  = carDAO.deleteById(carId);
        } catch (DaoException e) {
            logger.warn("CANT DELETE GIVEN CAR IN CarServiceImpl class");
            throw new ServiceException("CANT DELETE CAR SERVICE",e);
        }
        return response;
    }

    @Override
    public Optional<HashMap<List<Car>,Integer>> getAllCars(int currentPage) throws ServiceException{
        try {
            return Optional.of(carDAO.findAll(currentPage));
        } catch (DaoException e) {
            logger.warn("CANT GET ALL CARS IN CarServiceImpl class");
            throw new ServiceException("CANT GET CARS IN SERVICE",e);
        }
    }

    @Override
    public boolean updateCar(Car car) throws ServiceException{
        boolean isUpdated;

        try {
          isUpdated = carDAO.update(car);
        } catch (DaoException e) {
            logger.warn("CANT UPDATE GIVEN CAR IN CarServiceImpl class");
           throw new ServiceException("CANT UPDATE CAR SERVICE",e);
        }
        return isUpdated;
    }

    @Override
    public Optional<List<Car>> getSortedCars(String sortOrderCommand,int currPage)throws ServiceException {
        String neededQuery;
        List<Car> carList;
        switch (sortOrderCommand){
            case "sortCarsByName": neededQuery = QuerySQL.GET_ALL_CARS_SORT_BY_NAME;break;
            case "sortCarsByClass": neededQuery = QuerySQL.GET_ALL_CARS_SORT_BY_CAR_CLASS;break;
            case "sortCarsByBrand": neededQuery = QuerySQL.GET_ALL_CARS_SORT_BY_BRAND;break;
            case "sortCarsByRentPrice": neededQuery = QuerySQL.GET_ALL_CARS_SORT_BY_RENT_PRICE;break;
            default:neededQuery = QuerySQL.GET_ALL_CARS;
        }
        try {
           carList = carDAO.getSortedCars(neededQuery,currPage);
        } catch (DaoException e) {
            logger.warn("CANT GET ALL NEEDED CARS FOR SELECTED ORDER IN CarServiceImpl class");
           throw new ServiceException("CANT GET ALL NEEDED CARS FOR SELECTED ORDER",e);
        }
        return Optional.of(carList);
    }

    @Override
    public Optional<Car> getCar(String name) throws ServiceException {
        try{
             return Optional.of(carDAO.getCarByName(name));
        }catch (DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Car getOneCar(int carId) throws ServiceException {
        Car car;
        try {
            car = carDAO.findById(carId);
            System.out.println("returned car  " + car.getName());
        } catch (DaoException e) {
            logger.warn("CANT FIND CAR BY GIVEN ID IN CarServiceImpl class");
            throw new ServiceException("CANT FIND CAR BY GIVEN ID",e);
        }
        return car;
    }
}
