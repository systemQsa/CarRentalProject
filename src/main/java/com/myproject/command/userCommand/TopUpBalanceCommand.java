package com.myproject.command.userCommand;

import com.myproject.command.Command;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
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

public class TopUpBalanceCommand implements Command {
    private final UserService userService = new UserServiceImpl();
    private static final Logger logger = LogManager.getLogger(TopUpBalanceCommand.class);

    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
       Route route = new Route();
       String userLogin = request.getParameter("userLogin");
       String topUpBalance = request.getParameter("topUpBalance");
        String login = (String) request.getSession().getServletContext().getAttribute(GeneralConstant.USER_NAME);
        try {
             boolean isTopUpBalanceWasSuccessful  = userService.updateUserBalance(Double.parseDouble(topUpBalance),userLogin);
             if (isTopUpBalanceWasSuccessful){
                 route.setPathOfThePage(ConstantPage.USER_HOME_PAGE);
                 request.getSession().setAttribute("userBalance",userService.getBalance(login));
             }else {
                 //todo route to error page
                 System.out.println("ERROR PAGE CANT TOP UP BALANCE");
             }
        } catch (ServiceException e) {
            logger.error("PROBLEM IN TopUpBalanceCommand class CANT TOP UP USER BALANCE");
           throw new CommandException("CANT TOP UP USER BALANCE IN COMMAND",e);
        }
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}
