package com.myproject.command;

import com.myproject.command.util.*;
import com.myproject.dao.entity.User;
import com.myproject.exception.*;
import com.myproject.factory.impl.AbstractFactoryImpl;
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
    private User user;
    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext().getAttribute(GeneralConstant.LOGGED_USERS);
        //todo print error messages on jsp
        Route route = new Route();
        String login = request.getParameter(GeneralConstant.LOGIN);
        char[]password = request.getParameter("password").toCharArray();

        if (validateInput.validateUserIsBlocked(login,request)){
            logger.warn("Blocked user tried to log in");
            setInformMessageIfErrorOccur(GeneralConstant.ErrorMSG.BLOCKED_USER,1,request);
             route.setPathOfThePage(ConstantPage.LOG_IN_PAGE);
            route.setRoute(Route.RouteType.REDIRECT);
           throw new ValidationException("");

        }

        logger.info("USER LOGIN  == " + login);

        if (CommandUtil.userIsLogged(request)) {
            String userRole = CommandUtil.getUserRoleFromPage(request);
            route.setPathOfThePage(DefineRouteForUser.getPagePathDependOnUserRole(userRole));
            logger.info("user is logged");
        }else{
            userService = new AbstractFactoryImpl().getFactory().getServiceFactory().getUserService();
            validateInput.validateLogin(login,request,response);
            validateInput.validatePassword(password,request);

             String role;

            try {
                role = userService.logInValidation(login,password,request);
            } catch (ServiceException e) {
                throw new CommandException(ConstantPage.LOG_IN_PAGE);
            }

            if (role != null){
                try {
                    user = userService.getUserByLoginAndPass(login,password);
                } catch (ServiceException e) {
                    throw new CommandException(e.getMessage());
                }
            }else {
                setInformMessageIfErrorOccur(GeneralConstant.ErrorMSG.INVALID_PASS,3,request);
                throw new ValidationException(ConstantPage.LOG_IN_PAGE);
            }



            CommandUtil.setRoleForUser(request,role,login);
            route.setPathOfThePage(DefineRouteForUser.getPagePathDependOnUserRole(role));
            request.getSession().getServletContext().setAttribute(GeneralConstant.LOGGED_USERS,loggedUsers);
            try {
                request.getSession().setAttribute(GeneralConstant.Util.USER_BALANCE,userService.getBalance(login));
                request.getSession().setAttribute(GeneralConstant.Util.USER_ID_BY_LOGIN,user.getUserId());
                request.getSession().setAttribute(GeneralConstant.Util.USER_LOGIN,user.getLogin());
            } catch (ServiceException e) {
                logger.error("USER CANT LOGIN SOMETHING WENT WRONG");
                  throw new CommandException(ConstantPage.LOG_IN_PAGE);
            }
            logger.info("user NOT logged!");

        }

        route.setRoute(Route.RouteType.REDIRECT);

        loggedUsers.add(login);
        request.getSession().getServletContext().setAttribute(GeneralConstant.Util.LOGGED_USERS, loggedUsers);
         return route;
    }
}
