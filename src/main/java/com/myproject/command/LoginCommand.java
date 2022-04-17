package com.myproject.command;

import com.myproject.command.util.*;
import com.myproject.dao.entity.User;
import com.myproject.dao.impl.UserDaoImpl;
import com.myproject.exception.*;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.UserService;
import com.myproject.validation.Validate;
import com.myproject.validation.ValidateInput;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private final UserService userService;
    private final Validate validateInput;
    private User user;
    private final CommandUtil commandUtil = new CommandUtil();

    public LoginCommand(){
        userService = new AbstractFactoryImpl().getFactory().getServiceFactory().getUserService();
        validateInput = new ValidateInput();
    }

    public LoginCommand(UserService userService){
        this.userService = userService;
        validateInput = new ValidateInput(userService);
    }
    @Override
    public Route execute(HttpServletRequest request,
                         HttpServletResponse response) throws CommandException, ValidationException {
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext().getAttribute(GeneralConstant.LOGGED_USERS);
        Route route = new Route();
        String login = request.getParameter(GeneralConstant.LOGIN);
        char[]password = request.getParameter("password").toCharArray();
        String role;
        checkUserIsBlockedByManager(request, route, login);

        logger.info("USER LOGIN  == " + login);

            try{
                validateInput.loginValidate(login);
            }catch (ValidationException e){
                setInformMessageIfErrorOccur("err.login",2,request);
                throw new CommandException(ConstantPage.LOG_IN_PAGE);
            }


            try{
                validateInput.passwordValidate(password);
            }catch (ValidationException e){
                setInformMessageIfErrorOccur("err.password",3,request);
                throw new CommandException(ConstantPage.LOG_IN_PAGE);
            }

            try {
                 role = userService.logInValidation(login,password,request);
             } catch (ValidationException | ServiceException e) {
                setInformMessageIfErrorOccur("err.no_such_user",38,request);
                throw new CommandException(ConstantPage.LOG_IN_PAGE);
            }

            if (role != null){
                try {
                     user = userService.getUserByLoginAndPass(login,password);
                 } catch (ServiceException e) {
                    setInformMessageIfErrorOccur("err.no_such_user",38,request);
                    throw new CommandException(ConstantPage.LOG_IN_PAGE);
                }
            }else {
                setInformMessageIfErrorOccur("err.password",3,request);
                throw new ValidationException(ConstantPage.LOG_IN_PAGE);
            }

            if (commandUtil.userIsLogged(request)) {
                logger.info("user is logged");
                setInformMessageIfErrorOccur("err.user_already_logged",37,request);
                throw new CommandException(ConstantPage.LOG_IN_PAGE);
            }else{
                CommandUtil.setRoleForUser(request,role,login);
                route.setPathOfThePage(DefineRouteForUser.getPagePathDependOnUserRole(role));
                request.getSession().getServletContext().setAttribute(GeneralConstant.LOGGED_USERS,loggedUsers);
                try {
                    setPersonalUserDataToView(request, login);
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

    private void setPersonalUserDataToView(HttpServletRequest request, String login) throws ServiceException {
        request.getSession().setAttribute(GeneralConstant.Util.USER_BALANCE, userService.getBalance(login));
        request.getSession().setAttribute(GeneralConstant.Util.USER_ID_BY_LOGIN, user.getUserId());
        request.getSession().setAttribute(GeneralConstant.Util.USER_LOGIN, user.getLogin());
    }

    private void checkUserIsBlockedByManager(HttpServletRequest request,
                                             Route route, String login) throws ValidationException {
        if (validateInput.userIsBlockedValidate(login)){
           logger.warn("Blocked user tried to log in");
           setInformMessageIfErrorOccur("err.blocked_user",1, request);
            route.setPathOfThePage(ConstantPage.LOG_IN_PAGE);
           route.setRoute(Route.RouteType.REDIRECT);
          throw new ValidationException("/login.jsp");

       }
    }
}
