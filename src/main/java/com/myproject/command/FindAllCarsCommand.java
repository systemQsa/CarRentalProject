package com.myproject.command;

import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.Car;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.CarService;
import com.myproject.service.impl.CarServiceImpl;
import org.apache.log4j.LogManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;

public class FindAllCarsCommand implements Command{
    private final CarService<Car> carService = new AbstractFactoryImpl().getFactory().getServiceFactory().getCarService();
    private static final Logger logger = LogManager.getLogger(FindAllCarsCommand.class);

    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Route route = new Route();

        try {
            int currPage = 0;
            Optional<List<Car>> allCars = carService.getAllCars(currPage);
            if (allCars.isPresent()){
                request.getSession().setAttribute(GeneralConstant.Util.ALL_USERS,null);
                List<Car> carList = allCars.get();
                request.setAttribute(GeneralConstant.Util.ALL_CARS,carList);
                request.getSession().getServletContext().setAttribute(GeneralConstant.Util.ALL_CARS,allCars.get());
                route.setPathOfThePage(ConstantPage.WEB_INF_FULL_PATH_TO_ADMIN);
                route.setRoute(Route.RouteType.FORWARD);
            }
        } catch (ServiceException e) {
            logger.error("PROBLEM IN FindAllCarsCommand class");
            throw new CommandException("CANT GET ALL CARS IN COMMAND METHOD",e);
        }
        return route;
    }
}
