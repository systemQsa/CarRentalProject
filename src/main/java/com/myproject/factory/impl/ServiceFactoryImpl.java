package com.myproject.factory.impl;


import com.myproject.dao.entity.Car;
import com.myproject.factory.ServiceFactory;
import com.myproject.service.CarOrderService;
import com.myproject.service.CarService;
import com.myproject.service.OrderViewService;
import com.myproject.service.UserService;
import com.myproject.service.impl.CarOrderServiceImpl;
import com.myproject.service.impl.CarServiceImpl;
import com.myproject.service.impl.UserServiceImpl;
import com.myproject.service.impl.ViewOrderServiceImpl;

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
}
