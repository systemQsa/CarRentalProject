package com.myproject.command.userCommand;

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

public class SortCarsByWantedOrderCommand implements Command {
    private final CarService<Car> carService = new AbstractFactoryImpl().getFactory().getServiceFactory().getCarService();
    private static final Logger logger = LogManager.getLogger(SortCarsByWantedOrderCommand.class);

    @Override
    public Route execute(HttpServletRequest request,
                         HttpServletResponse response) throws CommandException, ValidationException {
        String wantedOrder = request.getParameter("order");
        String pageId = request.getParameter("page");
        String role = (String) request.getSession().getAttribute("role");
        String  records = request.getParameter("noOrRecordsSorted");
        System.out.println("Amount of records all " + request.getSession().getAttribute("records"));
        Integer amountOfTotalRecords = (Integer) request.getSession().getAttribute("records");
        int currPage;
        int noOfRecords;
        Route route = new Route();

        if (pageId != null && records != null){
            currPage = Integer.parseInt(pageId);
            noOfRecords = Integer.parseInt(records);
        }else {
            currPage = 1;
            noOfRecords = 5;
        }

        try {
            request.setAttribute("order",wantedOrder);
            request.setAttribute("currentPage",currPage);
            request.setAttribute("noOrRecordsSorted",noOfRecords);
            carService.getSortedCars(wantedOrder,currPage,noOfRecords)
                    .ifPresent(cars -> request.setAttribute("sortedCars", cars));

            int result = (amountOfTotalRecords / noOfRecords);
            if (result % noOfRecords > 0) result++;
            request.setAttribute("noOfPages",result);
            if (role == null) route.setPathOfThePage(ConstantPage.HOME_PAGE);
            route.setPathOfThePage(ConstantPage.WEB_INF_FULL_PATH_TO_USER);

        } catch (ServiceException e) {
            logger.warn("SOME PROBLEM OCCUR CANT SORT CAR FRO USER IN SortCarsByWantedOrderCommand class");
            throw new CommandException("CANT GET SORTED CARS IN COMMAND",e);
        }
        route.setRoute(Route.RouteType.FORWARD);
        return route;
    }
}
