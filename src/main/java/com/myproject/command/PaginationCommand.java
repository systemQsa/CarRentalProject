package com.myproject.command;

import com.myproject.command.facade.PaginationCommandFacade;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.Car;
import com.myproject.dao.entity.OrderViewForUserRequest;
import com.myproject.dao.entity.UserRole;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.CarService;
import com.myproject.service.OrderViewService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

/**
 * The PaginationCommand class implements Command interface.
 * Represents command to paginate cars,orders
 */
public class PaginationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(PaginationCommand.class);
    private final PaginationCommandFacade paginationCommandFacade;

    public PaginationCommand(){
        paginationCommandFacade = new PaginationCommandFacade();
    }

    public PaginationCommand(PaginationCommandFacade paginationCommandFacade){
        this.paginationCommandFacade = paginationCommandFacade;
    }

    /**
     * The method do pagination of cars and orders
     * for gust,user,manager
     *
     * @param request  - gets the request of required view entity(car/order)
     *                 number of the records to be seen
     *                 current page from web page
     *                 user role
     *                 user login
     * @param response - sends the response for the given request
     * @return the route where the result will be sent
     * @throws CommandException    in case some problems occur during the processing the request in PaginationCommand class
     * @throws ValidationException in case the input data failed the validation
     */
    @Override
    public Route execute(HttpServletRequest request,
                         HttpServletResponse response) throws CommandException, ValidationException {
        Route route;
        String pageId = request.getParameter("page");
        String userRole = (String) request.getSession().getAttribute("role");
        String requiredResult = request.getParameter("required");
        String userLogin = (String) request.getSession().getAttribute("userName");
        int currentPage = Integer.parseInt(pageId);
        String viewSuchOrders = request.getParameter("viewSuchOrders");
        int noOfRecords = Integer.parseInt(request.getParameter("noOfRecords"));

        try {
            route = paginationCommandFacade
                    .getAllCarsForGuest(currentPage,noOfRecords,request);

        } catch (ServiceException e) {
            logger.warn("Cannot get all required cars from DB in PaginationCommand class");
            throw new CommandException(e.getMessage());
        }

        if (userRole == null) {
            route.setPathOfThePage(ConstantPage.HOME_PAGE);

        } else {
            switch (userRole) {
                case "user": route = paginationCommandFacade
                            .getAllPersonalOrdersForUser(requiredResult,userLogin,currentPage,noOfRecords,request);
                            break;

                case "manager": route = paginationCommandFacade
                              .getAllOrdersForManager(request,requiredResult,currentPage,viewSuchOrders,noOfRecords);
                              break;
            }
        }

        localeDateTime(request);
        return route;
    }
}
