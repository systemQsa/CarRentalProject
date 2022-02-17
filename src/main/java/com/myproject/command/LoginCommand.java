package com.myproject.command;

import com.myproject.command.util.*;
import com.myproject.exception.*;
import com.myproject.service.UserService;
import com.myproject.service.impl.UserServiceImpl;
import com.myproject.validation.ValidateInput;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private UserService userService;
    private final ValidateInput validateInput = new ValidateInput();

    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext().getAttribute(GeneralConstant.LOGGED_USERS);
        //todo validate fields
        Route route = new Route();
        String login = request.getParameter(GeneralConstant.LOGIN);
        char[]password = request.getParameter("password").toCharArray();
        if (validateInput.validateUserIsBlocked(login)){
            route.setPathOfThePage("/error.jsp");
            route.setRoute(Route.RouteType.REDIRECT);
            return route;
        }

        System.out.println(request.getParameter(GeneralConstant.PASSWORD));
        logger.info("USER LOGIN  == " + login);
         //todo inform the cause on WEBpage if login or password incorrect
//        if (login == null || login.equals(GeneralConstant.EMPTY_STRING)) {
//            route.setPathOfThePage(ConstantPage.HOME_PAGE);
//        }
        if (CommandUtil.userIsLogged(request)) {
            String userRole = CommandUtil.getUserRoleFromPage(request);
            route.setPathOfThePage(DefineRouteForUser.getPagePathDependOnUserRole(userRole));
            logger.info("user is logged");
        }else{
            userService = new UserServiceImpl();
            //todo redirect to error page if user is blocked
            validateInput.validateLogin(login);
            validateInput.validatePassword(password);
            System.out.println(userService.getClass().getName());
            String role;
            try {
                role = userService.logInValidation(login,password);
            } catch (ServiceException e) {
               throw new CommandException("INCORRECT LOGIN INPUT",e);
            }

          //  System.out.println("ROLE  " + role);
         //   System.out.println("all is good found user n DB");
            //String role = Users.getRole(login);
            CommandUtil.setRoleForUser(request,role,login);
            route.setPathOfThePage(DefineRouteForUser.getPagePathDependOnUserRole(role));
            request.getSession().getServletContext().setAttribute(GeneralConstant.LOGGED_USERS,loggedUsers);
            try {
                request.getSession().setAttribute("userBalance",userService.getBalance(login));
            } catch (ServiceException e) {
                logger.error("USER CANT LOGIN SOMETHING WENT WRONG");
                throw new CommandException("CANT GET USER BALANCE",e);
            }
            logger.info("user NOT logged!");

        }
        //todo: check login and role with DB

        route.setRoute(Route.RouteType.REDIRECT);
        //request.getSession().getServletContext().setAttribute("loggedUsers", login);
        return route;
    }
}
