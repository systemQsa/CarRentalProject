package com.myproject.command.adminCommad;

import com.myproject.command.Command;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
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
 * The UpdateCarCommand implements the Command interface.
 * Retrieves all required data from the request about the car and update changes after returns to the admin home page
 */
public class UpdateCarCommand implements Command {
    private final CarService<Car> carService = new AbstractFactoryImpl().getFactory().getServiceFactory().getCarService();
    private static final Logger logger = LogManager.getLogger(UpdateCarCommand.class);

    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        Car.CarBuilder updatedCar = new Car.CarBuilder();
        Route route = new Route();

        try {
            carService.updateCar(
                    updatedCar.setCarId(Integer.parseInt(request.getParameter(GeneralConstant.CarConstants.CAR_ID)))
                            .setName(request.getParameter(GeneralConstant.CarConstants.CAR_NAME))
                            .setCarClass(request.getParameter(GeneralConstant.CarConstants.CAR_CLASS))
                            .setBrand(request.getParameter(GeneralConstant.CarConstants.CAR_BRAND))
                            .setRentalPrice(Double.parseDouble(request.getParameter(GeneralConstant.CarConstants.CAR_RENTAL_PRICE)))
                            .setPhoto(request.getParameter("carPhoto")).build()
            );
            route.setPathOfThePage(ConstantPage.ADMIN_HOME_PAGE);
        } catch (ServiceException e) {
            logger.error("IMPOSSIBLE UPDATE THE GIVEN CAR SOME PROBLEM IN UpdateCarCommand class");
            throw new CommandException("CANT UPDATE CAR COMMAND ", e);
        }
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}
