package com.myproject.factory;

import com.myproject.dao.entity.Car;
import com.myproject.service.CarOrderService;
import com.myproject.service.CarService;
import com.myproject.service.OrderViewService;
import com.myproject.service.UserService;

public interface ServiceFactory {
    CarOrderService getCarOrderService();
    CarService<Car> getCarService();
    OrderViewService getCarViewService();
    UserService getUserService();

}
