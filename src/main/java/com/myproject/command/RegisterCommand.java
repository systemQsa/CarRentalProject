package com.myproject.command;

import com.myproject.command.util.CommandUtil;
import com.myproject.command.util.DefineRouteForUser;
import com.myproject.command.util.GeneralConstant;
import com.myproject.command.util.Route;
import com.myproject.exception.CommandException;
import com.myproject.exception.DaoException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.service.UserService;
import com.myproject.service.impl.UserServiceImpl;
import com.myproject.validation.ValidateInput;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

public class RegisterCommand implements Command {
    private static final Logger logger = Logger.getLogger(RegisterCommand.class.getName());
    private final ValidateInput validateInput = new ValidateInput();
    private final UserService userService = new UserServiceImpl();

    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        //todo validate fields add to DB data
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String phoneNumber = request.getParameter("phone");
        String login = request.getParameter(GeneralConstant.LOGIN);
        char[] password = request.getParameter("password").toCharArray();
        System.out.println("CREDENTIALS  " + login + " " + phoneNumber);
        try {
            validateInput.validateInputNameSurname(name, surname);
            validateInput.validateLogin(login);
            validateInput.validatePassword(password);
            validateInput.validatePhoneNumber(phoneNumber);
            userService.registerNewUser(name, surname, login, password, phoneNumber);

        } catch (ServiceException | ValidationException e) {
            logger.warning("CANT REGISTER USER");
            throw new CommandException("COMMAND EXCEPTION CAN`T REGISTER USER!", e);
        }

        CommandUtil.setRoleForUser(request, GeneralConstant.USER, request.getParameter(GeneralConstant.LOGIN));
        //Users.addUser(request.getParameter(GeneralConstant.LOGIN),GeneralConstant.USER);
        Route route = new Route();
        route.setPathOfThePage(DefineRouteForUser.getPagePathDependOnUserRole(GeneralConstant.USER));
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}