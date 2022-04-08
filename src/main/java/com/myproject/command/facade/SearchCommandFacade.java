package com.myproject.command.facade;

import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.Car;
import com.myproject.exception.ServiceException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.CarService;
import com.myproject.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class SearchCommandFacade {
    private final UserService userService;
    private final CarService<Car> carService;

    public SearchCommandFacade() {
        userService = new AbstractFactoryImpl().getFactory().getServiceFactory().getUserService();
        carService = new AbstractFactoryImpl().getFactory().getServiceFactory().getCarService();
    }

    public SearchCommandFacade(UserService userService, CarService<Car> carService) {
        this.userService = userService;
        this.carService = carService;
    }

    public Route searchCarForGuest(String searchedCar, HttpServletRequest request) throws ServiceException {
        Route route = new Route();
        carService.getCar(searchedCar.trim()).ifPresent(car -> {
            request.setAttribute("searchCommand", "searchingCar");
            request.setAttribute("searchedCars", car);
            route.setPathOfThePage("/index.jsp");
        });
        route.setRoute(Route.RouteType.FORWARD);
        return route;
    }


    public Route searchUserForAdmin(String searchedUser, HttpServletRequest request) throws ServiceException {
        Route route = new Route();
        userService.getUser(searchedUser.trim()).ifPresent(user -> {
            request.setAttribute("searchCommand", "searchingUser");
            request.setAttribute("searchedUser", user);
            route.setPathOfThePage(ConstantPage.WEB_INF_FULL_PATH_TO_ADMIN);
        });
        route.setRoute(Route.RouteType.FORWARD);
        return route;
    }

    public Route searchCarForAdminOrUser(String searchedCar, HttpServletRequest request) throws ServiceException {
        Route route = new Route();
        carService.getCar(searchedCar.trim()).ifPresent(car -> {
            request.setAttribute("searchCommand", "searchingCar");
            request.setAttribute("searchedCars", car);
            if (Objects.equals(request.getSession().getAttribute("role"), "admin")) {
                route.setPathOfThePage(ConstantPage.WEB_INF_FULL_PATH_TO_ADMIN);
            } else if (Objects.equals(request.getSession().getAttribute("role"), "user")) {
                route.setPathOfThePage(ConstantPage.WEB_INF_FULL_PATH_TO_USER);
            }
        });
        route.setRoute(Route.RouteType.FORWARD);
        return route;
    }
}
