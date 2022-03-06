package com.myproject.command.userCommand;

import com.myproject.command.Command;
import com.myproject.command.adminCommad.SetRoleForUserCommand;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.Car;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.CarService;
import com.myproject.service.impl.CarServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class SortCarsByWantedOrderCommand implements Command {
    private final CarService<Car> carService = new AbstractFactoryImpl().getFactory().getServiceFactory().getCarService();
    private static final Logger logger = LogManager.getLogger(SortCarsByWantedOrderCommand.class);

    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        String wantedOrder = request.getParameter("order");
        System.out.println("WANTED ORDER " + wantedOrder);
        Route route = new Route();
        try {
            Optional<List<Car>> sortedCars = carService.getSortedCars(wantedOrder);
            sortedCars.ifPresent(cars -> request.setAttribute("allCars", cars));
            route.setPathOfThePage(ConstantPage.WEB_INF_FULL_PATH_TO_USER);

        } catch (ServiceException e) {
            logger.warn("SOME PROBLEM OCCUR CANT SORT CAR FRO USER IN SortCarsByWantedOrderCommand class");
            throw new CommandException("CANT GET SORTED CARS IN COMMAND",e);
        }
        route.setRoute(Route.RouteType.FORWARD);
        return route;
    }
}
