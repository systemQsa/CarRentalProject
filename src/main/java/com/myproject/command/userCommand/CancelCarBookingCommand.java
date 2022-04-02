package com.myproject.command.userCommand;

import com.myproject.command.Command;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.exception.CommandException;
import com.myproject.exception.ValidationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The CancelCarBookingCommand class  implements the Command interface.
 * Represents the class that process the cancelling of the booking
 */

public class CancelCarBookingCommand implements Command {
    private static final Logger logger = LogManager.getLogger(CancelCarBookingCommand.class);

    @Override
    public Route execute(HttpServletRequest request,
                         HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        request.getSession().setAttribute(request.getParameter("userPassport"), null);
        request.getSession().setAttribute(request.getParameter("fromDate"), null);
        request.getSession().setAttribute(request.getParameter("toDate"), null);
        request.getSession().setAttribute(request.getParameter("withDriver"), null);
        request.getSession().setAttribute(request.getParameter("totalPrice"), null);
        route.setPathOfThePage(ConstantPage.USER_HOME_PAGE);
        route.setRoute(Route.RouteType.REDIRECT);
        logger.info("ORDER WAS CANCELED");
        return route;
    }
}
