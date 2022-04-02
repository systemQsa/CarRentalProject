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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The SearchCommand class implements Command interface.
 * Represents class to work with searching process
 */
public class SearchCommand implements Command {
    private final UserService userService =
            new AbstractFactoryImpl().getFactory().getServiceFactory().getUserService();
    private final CarService<Car> carService =
            new AbstractFactoryImpl().getFactory().getServiceFactory().getCarService();

    /**
     * The method retrieves desired arguments
     * depending on search result execute different commands
     *
     * @param request  - gets request from the client
     * @param response - sends the response to the browser
     * @return return route where the result will be sent
     * @throws CommandException    in case some problem occurred in SearchCommand class
     * @throws ValidationException in case the input validation was failed
     */
    @Override
    public Route execute(HttpServletRequest request,
                         HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        String role = (String) request.getSession().getAttribute("role");
        String search = request.getParameter("search");

        try {
            if (role == null) {

                carService.getCar(search.trim()).ifPresent(searchedCar -> {
                    request.setAttribute("searchCommand", "searchingCar");
                    request.setAttribute("searchedCars", searchedCar);
                    route.setPathOfThePage("/index.jsp");
                });

            } else {

                 if (role.equals("admin") && search.contains("@")) {
                     userService.getUser(search.trim()).ifPresent(searchedUser -> {
                        request.setAttribute("searchCommand", "searchingUser");
                        request.setAttribute("searchedUser", searchedUser);
                        route.setPathOfThePage(ConstantPage.WEB_INF_FULL_PATH_TO_ADMIN);
                    });

                } else {

                    carService.getCar(search.trim()).ifPresent(searchedCars -> {
                        request.setAttribute("searchCommand", "searchingCar");
                        request.setAttribute("searchedCars", searchedCars);
                        if (Objects.equals(request.getSession().getAttribute("role"), "admin")) {
                            route.setPathOfThePage(ConstantPage.WEB_INF_FULL_PATH_TO_ADMIN);
                        } else if (Objects.equals(request.getSession().getAttribute("role"), "user")) {
                            route.setPathOfThePage(ConstantPage.WEB_INF_FULL_PATH_TO_USER);
                        }
                    });

                }
            }
            route.setRoute(Route.RouteType.FORWARD);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
        return route;
    }
}
