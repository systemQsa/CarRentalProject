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
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Route route = new Route();
        System.out.println("\nFind All Cars Working");
        String page = request.getParameter("page");
        int noOfRecords = Integer.parseInt(request.getParameter("noOfRecords"));
        int currPage;
        if (page != null) {
            currPage = Integer.parseInt(page);
        } else {
            currPage = 1;
        }

        try {
            Optional<HashMap<List<Car>, Integer>> allCars = carService.getAllCars(currPage, noOfRecords);
            if (allCars.isPresent()) {
                request.getSession().setAttribute(GeneralConstant.Util.ALL_USERS, null);
                HashMap<List<Car>, Integer> carList = allCars.get();
                request.setAttribute(GeneralConstant.Util.ALL_CARS, new ArrayList<>(carList.keySet()).get(0));
                request.setAttribute("amountOfRecords", carList.values().stream().findFirst().orElse(5));
                request.setAttribute("currentPage", currPage);
                request.setAttribute("noOfRecords", noOfRecords);
                request.getSession().getServletContext().setAttribute(GeneralConstant.Util.ALL_CARS, allCars.get());
                route.setPathOfThePage(ConstantPage.WEB_INF_FULL_PATH_TO_ADMIN);
                route.setRoute(Route.RouteType.FORWARD);
            }
        } catch (ServiceException e) {
            logger.error("PROBLEM IN FindAllCarsCommand class");
            throw new CommandException("CANT GET ALL CARS IN COMMAND METHOD", e);
        }
        return route;
    }
}
