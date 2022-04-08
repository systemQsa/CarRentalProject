package com.myproject.command.userCommand;

import com.myproject.command.Command;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.DriverService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The BookCarReqCommand class implements the Command interface.
 * Represents class that process the booking process
 * gets all required data about the order
 * redirect to the page where user pick dates and time
 * enters passport data and pick option(with/without) driver
 * after the system counts the total receipt price
 * user can confirm or decline the request
 */
public class BookCarReqCommand implements Command {
    private static final Logger logger = LogManager.getLogger(BookCarReqCommand.class);
    private final DriverService driverService = new AbstractFactoryImpl().getFactory().getServiceFactory().getDriverService();
    @Override
    public Route execute(HttpServletRequest request,
                         HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        HttpSession session = request.getSession();

        try {
                session.setAttribute("carIdReq", request.getParameter("carId"));
                session.setAttribute("carNameReq", request.getParameter("carName"));
                session.setAttribute("carClassReq", request.getParameter("carClass"));
                session.setAttribute("carBrandReq", request.getParameter("carBrand"));
                session.setAttribute("rentPriceReq", request.getParameter("rentPrice"));
                session.setAttribute("userLogin", request.getParameter("userLogin"));
                session.setAttribute("userLoginReq", request.getParameter("userLogin"));
                session.setAttribute("driverRentalPrice",driverService.getDriverRentalPrice());
                route.setPathOfThePage(ConstantPage.USER_CREATE_BOOKING_PAGE);

        } catch (ServiceException e) {
            logger.error("SOMETHING WENT WRONG CANT BOOK THE ORDER IN BookCarReqCommand class");
            throw new CommandException("CANT BOOK THE ORDER", e);
        }
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}
