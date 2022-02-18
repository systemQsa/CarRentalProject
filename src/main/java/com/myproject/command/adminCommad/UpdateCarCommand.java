package com.myproject.command.adminCommad;

import com.myproject.command.Command;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.Car;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.service.CarService;
import com.myproject.service.impl.CarServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateCarCommand implements Command {
    private final CarService<Car> carService = new CarServiceImpl();
    private static final Logger logger = LogManager.getLogger(UpdateCarCommand.class);

    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        Car.CarBuilder updatedCar = new Car.CarBuilder();
        Route route = new Route();

         try {
            carService.updateCar(
                    updatedCar.setCarId(Integer.parseInt(request.getParameter("carId")))
                            .setName(request.getParameter("carName"))
                            .setCarClass(request.getParameter("carClass"))
                            .setBrand(request.getParameter("brand"))
                            .setRentalPrice(Double.parseDouble(request.getParameter("rentalPrice")))
                            .setPhoto(request.getParameter("carPhoto")).build()
            );
            route.setPathOfThePage(ConstantPage.ADMIN_HOME_PAGE);
        } catch (ServiceException e) {
            logger.error("IMPOSSIBLE UPDATE THE GIVEN CAR SOME PROBLEM IN UpdateCarCommand class");
            throw new CommandException("CANT UPDATE CAR COMMAND ",e);
        }
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}
