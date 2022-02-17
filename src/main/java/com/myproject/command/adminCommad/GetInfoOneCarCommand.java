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
import javax.servlet.http.HttpSession;

public class GetInfoOneCarCommand implements Command {
    private final CarService<Car> carService = new CarServiceImpl();
    private static final Logger logger = LogManager.getLogger(GetInfoOneCarCommand.class);

    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        try {
            Car car = carService.getOneCar(Integer.parseInt(request.getParameter("carId")));
            System.out.println("carService CAR NAME " + car.getName());
            HttpSession session = request.getSession();
            session.setAttribute("carId",car.getCarId());
            session.setAttribute("carName", car.getName());
            session.setAttribute("carClass",car.getCarClass());
            session.setAttribute("brand",car.getBrand());
            session.setAttribute("rentalPrice",car.getRentalPrice());
            session.setAttribute("carPhoto",car.getPhoto());
            route.setPathOfThePage(ConstantPage.UPDATE_CAR_PAGE_REDIRECT);
        } catch (ServiceException e) {
            logger.error("CANT GET INFO ABOUT GIVEN CAR CHECK IF THIS CAR EXISTS");
            throw new CommandException("CANT FIND CAR", e);
        }
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}
