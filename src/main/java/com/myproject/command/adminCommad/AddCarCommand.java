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
import com.myproject.validation.Validate;
import com.myproject.validation.ValidateInput;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The AddCarCommand class implements the Command interface.
 * Represents class that adds a new car to the system
 * Retrieves all required data about car and adds it to the table. Returns to the admin home page.
 *
 */
public class AddCarCommand implements Command {
    private final CarService<Car> carService;
    private static final Logger logger = LogManager.getLogger(AddCarCommand.class);
    private final Validate validate = new ValidateInput();
    public AddCarCommand() {
        carService = new AbstractFactoryImpl().getFactory().getServiceFactory().getCarService();
    }

    @Override
    public Route execute(HttpServletRequest request,
                         HttpServletResponse response) throws CommandException {
        Route route = new Route();

        try {
            validate.validateCarInput(request.getParameter("name").trim(),
                                      request.getParameter("carClass").trim(),
                                       request.getParameter("brand").trim());
        } catch (ValidationException e) {
            setInformMessageIfErrorOccur("err.car_input",41,request);
            throw new CommandException(ConstantPage.ADD_CAR_PAGE);
        }

        try {
            validate.validatePrice(request.getParameter("rentalPrice").trim());
        } catch (ValidationException e) {
            setInformMessageIfErrorOccur("err.price_input",40,request);
            throw new CommandException(ConstantPage.ADD_CAR_PAGE);
        }


        try {
            carService.addCar(request);
            route.setPathOfThePage(ConstantPage.ADMIN_HOME_PAGE);
            setSuccessMessage("info.car_added",2,request);
            route.setRoute(Route.RouteType.REDIRECT);
            return route;
        } catch (ServiceException e) {
            logger.error("CANT ADD NEW CAR IN AddCarCommand SOME PROBLEM");
            setInformMessageIfErrorOccur("err.car_already_added", 17, request);
            throw new CommandException(ConstantPage.ADD_CAR_PAGE);
        }

    }
}
