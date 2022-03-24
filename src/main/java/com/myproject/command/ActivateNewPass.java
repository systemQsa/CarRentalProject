package com.myproject.command;

import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.service.UserService;
import com.myproject.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActivateNewPass implements Command{
    private UserService userService = new UserServiceImpl();

    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        boolean isPassUpdatedSuccessfully;
        String userLogin = request.getParameter("userLogin");
        String newPass = request.getParameter("password");
        System.out.println("Activate new Pass");

        try {
            isPassUpdatedSuccessfully = userService.resetPassword(userLogin,newPass.toCharArray());
            if (isPassUpdatedSuccessfully){
                route.setPathOfThePage(ConstantPage.LOG_IN_PAGE);
            }else {
                route.setPathOfThePage(ConstantPage.HOME_PAGE);
            }
        } catch (ServiceException e) {
            throw new CommandException(ConstantPage.LOG_IN_PAGE);
        }

        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}
