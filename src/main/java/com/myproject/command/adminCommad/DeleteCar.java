package com.myproject.command.adminCommad;

import com.myproject.command.Command;
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

public class DeleteCar implements Command {
    private final CarService<Car> carService = new AbstractFactoryImpl().getFactory().getServiceFactory().getCarService();
    private static final Logger logger = LogManager.getLogger(DeleteCar.class);
    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        int carId = Integer.parseInt(request.getParameter("carId"));
        Route route = new Route();
        try {
            //todo if car was successfully deleted back answer!
            boolean answer = carService.deleteCar(carId);
        } catch (ServiceException e) {
            logger.error("CANT DELETE CAR IN DeleteCar class");
            throw new CommandException("CANT DELETE CAR IN COMMAND",e);
        }
        route.setPathOfThePage(ConstantPage.ADMIN_HOME_PAGE);
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}
