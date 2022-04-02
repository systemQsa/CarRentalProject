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
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The DeleteCar class implement the Command interface.
 * Represents class that deletes current car from the system
 *
 */
public class DeleteCar implements Command {
    private final CarService<Car> carService;
    private static final Logger logger = LogManager.getLogger(DeleteCar.class);

    public DeleteCar(){
        carService = new AbstractFactoryImpl().getFactory().getServiceFactory().getCarService();
    }

    @Override
    public Route execute(HttpServletRequest request,
                         HttpServletResponse response) throws CommandException, ValidationException {
        int carId = Integer.parseInt(request.getParameter("carId"));
        Route route = new Route();
        try {
            //todo if car was successfully deleted back answer!
            boolean answer = carService.deleteCar(carId);
            setSuccessMessage("info.car_deleted",1,request);
        } catch (ServiceException e) {
            logger.error("CANT DELETE CAR IN DeleteCar class");
            throw new CommandException("/WEB-INF/view/admin/admin.jsp");
        }
        route.setPathOfThePage(ConstantPage.ADMIN_HOME_PAGE);
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}
