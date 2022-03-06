package com.myproject.command;

import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.Car;
import com.myproject.dao.entity.User;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.CarService;
import com.myproject.service.UserService;
import com.myproject.service.impl.CarServiceImpl;
import com.myproject.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

public class SearchCommand implements  Command{
    private final UserService userService = new AbstractFactoryImpl().getFactory().getServiceFactory().getUserService();
    private final CarService<Car> carService = new AbstractFactoryImpl().getFactory().getServiceFactory().getCarService();
    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        String role = (String) request.getSession().getAttribute("role");
        String search = request.getParameter("search");

        System.out.println("\n search " + request.getRequestURL() + " " + request.getRequestURI());
        User searchUser;
        Car searchCar;
        Optional<User> user;
        Optional<Car> car;
        try {
            if (role == null){
                car = carService.getCar(search);
                if (car.isPresent()){
                    searchCar = car.get();
                    System.out.println("serach "  + searchCar);
                    request.setAttribute("searchedCar",searchCar);
                    route.setPathOfThePage("/index.jsp");
                }

            }else {
            if (role.equals("admin") && search.contains("@")){
                user = userService.getUser(search);
                if (user.isPresent()){
                    searchUser = user.get();
                    System.out.println("serach "  + searchUser);
                    request.setAttribute("searchedUser",searchUser);
                    route.setPathOfThePage(ConstantPage.WEB_INF_FULL_PATH_TO_ADMIN);
                }
            }else {
                     car = carService.getCar(search);
                     if (car.isPresent()) {
                        searchCar = car.get();
                        System.out.println("serach "  + searchCar);
                        request.setAttribute("searchedCar", searchCar);
                        if (Objects.equals(request.getSession().getAttribute("role"),"admin")){
                          route.setPathOfThePage(ConstantPage.WEB_INF_FULL_PATH_TO_ADMIN);
                        } else if (Objects.equals(request.getSession().getAttribute("role"),"user")){
                            route.setPathOfThePage(ConstantPage.WEB_INF_FULL_PATH_TO_USER);
                        }

                    }

                 }
            }
            route.setRoute(Route.RouteType.FORWARD);
        }catch (ServiceException e){
            throw new CommandException(e.getMessage());
        }
        return route;
    }
}
