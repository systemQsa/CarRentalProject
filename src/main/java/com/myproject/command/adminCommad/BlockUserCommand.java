package com.myproject.command.adminCommad;

import com.myproject.command.Command;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.service.UserService;
import com.myproject.service.impl.UserServiceImpl;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class BlockUserCommand implements Command {
    private final UserService userService = new UserServiceImpl();
    private static final Logger logger = LogManager.getLogger(AddCarCommand.class);

    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        String userLogin = request.getParameter("userLogin");
        System.out.println("COMMAND " + request.getParameter("action"));
        try {
            boolean isSuccess = userService.blockUser(userLogin);
            //todo else go to error page if cant block user
            if (isSuccess){
                route.setPathOfThePage(ConstantPage.ADMIN_HOME_PAGE);
            }
        } catch (ServiceException e){
            logger.error("CANT BLOCK USER IN BlockUserCommand SOME PROBLEM");
            throw new CommandException("CANT BLOCK USER IN COMMAND",e);
        }
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}
