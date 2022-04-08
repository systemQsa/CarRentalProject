package com.myproject.command;

import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.service.UserService;
import com.myproject.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The ActivateNewPass class implements Command interface.
 * THe class sets and activates a new password for the user
 */

public class ActivateNewPass implements Command {
    private final UserService userService = new UserServiceImpl();
    private static final Logger logger = LogManager.getLogger(ActivateNewPass.class);


    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        String userLogin = request.getParameter("userLogin");
        String newPass = request.getParameter("password");

        try {
            if (userService.resetPassword(userLogin, newPass.toCharArray())) {
                route.setPathOfThePage(ConstantPage.LOG_IN_PAGE);
            } else {
                route.setPathOfThePage(ConstantPage.HOME_PAGE);
            }
        } catch (ServiceException e) {
            logger.warn("Some problem occur can`t activate a the password for the given user in ActivateNewPass class");
            throw new CommandException(ConstantPage.LOG_IN_PAGE);
        }

        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}
