package com.myproject.service.impl;

import com.myproject.dao.CarDao;
import com.myproject.dao.entity.Car;
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

/**
 * The CarServiceImpl represents special methods for work with representing and processing requests related to Car entity
 */
public class CarServiceImpl implements CarService<Car> {
    private final CarDao carDAO;
    private static final Logger logger = LogManager.getLogger(CarServiceImpl.class);

    public CarServiceImpl(){
        carDAO = new AbstractFactoryImpl().getFactory().getDaoFactory().getCarDao();
    }

    public CarServiceImpl(CarDao carDao){
        this.carDAO = carDao;
    }


    /**
     * The method adds a new car
     * @param request - gets Http request and retrieves all needed data
     * @return added new Car
     * @throws ServiceException in case if impossible add a new car to DataBase
     */
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
            throw new ServiceException(e);
        }
        return car;
    }

    /**
     * The method deletes car from DB by given id
     * @param carId - gets car id
     * @return in car was successfully deleted returns true
     * @throws ServiceException in case cannot delete given car from DB
     */
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

    /**
     * The method gets all cars depends on desired request
     * @param currentPage - gets current page from webpage
     * @param noOfRecords - gets desired number of records to be found
     * @return all found cars
     * @throws ServiceException in case cannot get all cars
     */
    @Override
    public Optional<HashMap<List<Car>,Integer>> getAllCars(int currentPage,int noOfRecords) throws ServiceException{
        try {
            return Optional.ofNullable(carDAO.findAll(currentPage,noOfRecords));
        } catch (DaoException e) {
            logger.warn("CANT GET ALL CARS IN CarServiceImpl class");
            throw new ServiceException("CANT GET CARS IN SERVICE",e);
        }
    }

    /**
     *
     * @param car
     * @return in car was successfully updated returns true
     * @throws ServiceException in case cannot update the given car
     */
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

    /**
     * The method sort cars by given order
     * @param sortOrderCommand - gets desired sort order
     * @param currPage - gets current page from web page
     * @param noOfRecords - gets desired number of records
     * @return all found cars
     * @throws ServiceException in case cannot find cars by given order
     */
    @Override
    public Optional<List<Car>> getSortedCars(String sortOrderCommand,int currPage,int noOfRecords)throws ServiceException {
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
           carList = carDAO.getSortedCars(neededQuery,currPage,noOfRecords);
        } catch (DaoException e) {
            logger.warn("CANT GET ALL NEEDED CARS FOR SELECTED ORDER IN CarServiceImpl class");
           throw new ServiceException("CANT GET ALL NEEDED CARS FOR SELECTED ORDER",e);
        }
        return Optional.ofNullable(carList);
    }

    /**
     * The method gets all cars by given name
     * @param name - gets car name
     * @return all found car(s)
     * @throws ServiceException in case cannot find car(s) by given name
     */
    @Override
    public Optional<List<Car>>getCar(String name) throws ServiceException {
        try{
             return Optional.ofNullable(carDAO.searchCarsByName(name));
        }catch (DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * The method gets car by given car id
     * @param carId - gets car id
     * @return found car
     * @throws ServiceException in case cannot find car by given id
     */
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
