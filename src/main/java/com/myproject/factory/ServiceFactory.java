package com.myproject.factory;

import com.myproject.dao.entity.Car;
import com.myproject.service.*;

public interface ServiceFactory {
    CarOrderService getCarOrderService();
    CarService<Car> getCarService();
    OrderViewService getCarViewService();
    UserService getUserService();
    DriverService getDriverService();
}
