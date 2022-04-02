package com.myproject.command;

import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.Car;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.CarService;
import org.apache.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import org.apache.log4j.Logger;

/**
 * The FindAllCarsCommand class implements Command interface.
 * Gets all registered cars and returns it to the admin page
 */

public class FindAllCarsCommand implements Command {
    private final CarService<Car> carService = new AbstractFactoryImpl().getFactory().getServiceFactory().getCarService();
    private static final Logger logger = LogManager.getLogger(FindAllCarsCommand.class);

    @Override
    public Route execute(HttpServletRequest request,
                         HttpServletResponse response) throws CommandException {
        Route route = new Route();
        String page = request.getParameter("page");
        int noOfRecords = Integer.parseInt(request.getParameter("noOfRecords"));
        int currPage;

        currPage = (page != null) ? Integer.parseInt(page) : 1;

        try {
            carService.getAllCars(currPage, noOfRecords).ifPresent(allCars -> {
                request.getSession().setAttribute(GeneralConstant.Util.ALL_USERS, null);
                request.setAttribute(GeneralConstant.Util.ALL_CARS, new ArrayList<>(allCars.keySet()).get(0));
                request.setAttribute("amountOfRecords", allCars.values().stream().findFirst().orElse(5));
                request.setAttribute("currentPage", currPage);
                request.setAttribute("noOfRecords", noOfRecords);
                request.getSession().getServletContext().setAttribute(GeneralConstant.Util.ALL_CARS, allCars);
                route.setPathOfThePage(ConstantPage.WEB_INF_FULL_PATH_TO_ADMIN);
                route.setRoute(Route.RouteType.FORWARD);
            });
        } catch (ServiceException e) {
            logger.error("PROBLEM IN FindAllCarsCommand class");
            throw new CommandException("CANT GET ALL CARS IN COMMAND METHOD", e);
        }
        return route;
    }
}
