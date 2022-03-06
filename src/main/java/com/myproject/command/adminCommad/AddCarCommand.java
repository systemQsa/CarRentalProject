package com.myproject.command.adminCommad;

import com.myproject.command.Command;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.Car;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.CarService;
import com.myproject.service.impl.CarServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddCarCommand implements Command {
    private CarService<Car> carService;
    private static final Logger logger = LogManager.getLogger(AddCarCommand.class);
    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
         Route route = new Route();
         carService = new AbstractFactoryImpl().getFactory().getServiceFactory().getCarService();
        try {
            Car car =  carService.addCar(request);
            route.setPathOfThePage(ConstantPage.ADMIN_HOME_PAGE);
        } catch (ServiceException e) {
            logger.error("CANT ADD NEW CAR IN AddCarCommand SOME PROBLEM");
            throw new CommandException("CANT ADD NEW CAR IN COMMAND",e);
        }
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}
