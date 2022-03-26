package com.myproject.command.adminCommad;

import com.myproject.command.Command;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.UserRole;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The SetRoleForUserCommand class implements the Command interface.
 * The class after getting the request from the client defines
 * the user role and redirects to the allowed ulr home page
 */
public class SetRoleForUserCommand implements Command {
    private final UserService userService = new AbstractFactoryImpl().getFactory().getServiceFactory().getUserService();
    private static final Logger logger = LogManager.getLogger(SetRoleForUserCommand.class);
    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        String action = request.getParameter("action");
        String userLogin = request.getParameter("userLogin");
        Route route = new Route();
        try {
            if (action.equals(GeneralConstant.Util.SET_MANAGER)) {
                userService.updateUserStatus(userLogin, UserRole.MANAGER);
                route.setPathOfThePage(ConstantPage.ADMIN_HOME_PAGE);
            } else if (action.equals(GeneralConstant.Util.UNSET_MANAGER)) {
                route.setPathOfThePage(ConstantPage.ADMIN_HOME_PAGE);
                userService.updateUserStatus(userLogin,UserRole.USER);
            }
        }catch (ServiceException e){
            logger.error("IMPOSSIBLE TO CHANGE ROLE FOR USER IN SetRoleForUserCommand class");
            throw new CommandException("CANT CHANGE USER STATUS",e);
        }
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}
