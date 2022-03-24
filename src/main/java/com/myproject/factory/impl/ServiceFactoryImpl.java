package com.myproject.factory.impl;


import com.myproject.dao.entity.Car;
import com.myproject.factory.ServiceFactory;
import com.myproject.service.*;
import com.myproject.service.impl.*;

public class ServiceFactoryImpl implements ServiceFactory {

    @Override
    public CarOrderService getCarOrderService() {
        return new CarOrderServiceImpl();
    }

    @Override
    public CarService<Car> getCarService() {
        return new CarServiceImpl();
    }

    @Override
    public OrderViewService getCarViewService() {
        return new ViewOrderServiceImpl();
    }

    @Override
    public UserService getUserService() {
        return new UserServiceImpl();
    }

    @Override
    public DriverService getDriverService() {
        return new DriverServiceImpl();
    }
}
