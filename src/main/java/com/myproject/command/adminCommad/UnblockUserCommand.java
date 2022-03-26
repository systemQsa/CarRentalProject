package com.myproject.command.adminCommad;

import com.myproject.command.Command;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
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
 * The UnblockUserCommand class implements the Command interface.
 * Represents class that  unblocks the user by admin. Returns to the admin home page
 */
public class UnblockUserCommand implements Command {
    private final UserService userService = new AbstractFactoryImpl().getFactory().getServiceFactory().getUserService();
    private static final Logger logger = LogManager.getLogger(UnblockUserCommand.class);
    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        String userLogin = request.getParameter("userLogin");
        Route route = new Route();
        boolean isSuccess;
        try {
           isSuccess =  userService.unblockUser(userLogin);
           //todo else go to error page if cant unblock user
           if (isSuccess){
               route.setPathOfThePage(ConstantPage.ADMIN_HOME_PAGE);
           }else{
               setInformMessageIfErrorOccur("err.block_unblock_user",20,request);
               throw new CommandException(ConstantPage.ADMIN_HOME_PAGE);
           }
        } catch (ServiceException e) {
            logger.error("IMPOSSIBLE UNBLOCK USER IN UnblockUserCommand class");
           throw new CommandException("CANT UNBLOCK USER SOME PROBLEMS OCCUR",e);
        }
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}
