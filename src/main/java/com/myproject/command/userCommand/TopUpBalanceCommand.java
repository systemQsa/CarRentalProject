package com.myproject.command.userCommand;

import com.myproject.command.Command;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
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
 * The TopUpBalanceCommand class implements the Command interface.
 * Represents class that process the request from user to top up the balance
 */

public class TopUpBalanceCommand implements Command {
    private final UserService userService;
    private static final Logger logger = LogManager.getLogger(TopUpBalanceCommand.class);

    public TopUpBalanceCommand(){
        userService = new AbstractFactoryImpl().getFactory().getServiceFactory().getUserService();
    }

    public TopUpBalanceCommand(UserService userService){
        this.userService = userService;
    }

    /**
     * The method retrieves the amount  which should be replenished the user card
     * @param request - gets the request with desired data for the process
     * @param response - sends after the process was made
     * @return route to the user page
     * @throws CommandException in case some problems occur during retrieving the data from the request
     * @throws ValidationException in case data validation was failed
     */
    @Override
    public Route execute(HttpServletRequest request,
                         HttpServletResponse response) throws CommandException, ValidationException {
       Route route = new Route();
       String userLogin = request.getParameter("userLogin");
       String topUpBalance = request.getParameter("topUpBalance");
        String login = (String) request.getSession().getServletContext().getAttribute(GeneralConstant.USER_NAME);

        try {
             if (userService.updateUserBalance(Double.parseDouble(topUpBalance),userLogin)){
                 route.setPathOfThePage(ConstantPage.USER_HOME_PAGE);
                 request.getSession().setAttribute("userBalance",userService.getBalance(login));
             }else {
                 route.setPathOfThePage(ConstantPage.ERROR_PAGE);
             }
        } catch (ServiceException e) {
            setInformMessageIfErrorOccur("err.top_up_balance",28,request);
            logger.error("PROBLEM IN TopUpBalanceCommand class CANT TOP UP USER BALANCE");
           throw new CommandException(ConstantPage.USER_HOME_PAGE);
        }
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}
