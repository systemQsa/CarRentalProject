package com.myproject.command;

import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
import com.myproject.command.util.Route;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.UserService;
import com.myproject.validation.Validate;
import com.myproject.validation.ValidateInput;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The RegisterCommand class implements Command interface.
 * Represents class to register new users
 */
public class RegisterCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegisterCommand.class);
    private final Validate validateInput;
    private final UserService userService;

    public RegisterCommand() {
        userService = new AbstractFactoryImpl().getFactory().getServiceFactory().getUserService();
        validateInput = new ValidateInput();
    }

    public RegisterCommand(UserService userService) {
        this.userService = userService;
        validateInput = new ValidateInput(userService);
    }

    /**
     * The method retrieves desired date from the request, validates the input date and register new user
     *
     * @param request  - gets request from the client
     * @param response - sends the response depending on the result
     * @return the route to where the result will be sent
     * @throws CommandException in case some problems occur in RegisterCommand class
     */
    @Override
    public Route execute(HttpServletRequest request,
                         HttpServletResponse response) throws CommandException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String phoneNumber = request.getParameter("phone");
        String login = request.getParameter(GeneralConstant.LOGIN);
        char[] password = request.getParameter(GeneralConstant.PASSWORD).toCharArray();
        Route route = new Route();

        try {
            validateInput.nameSurnameValidate(name, surname);
        } catch (ValidationException e) {
            setInformMessageIfErrorOccur("err.name_surname", 4, request);
            throw new CommandException(ConstantPage.REGISTER_PAGE);
        }

        try {
            validateInput.loginValidate(login);
        } catch (ValidationException e) {
            setInformMessageIfErrorOccur("err.login", 5, request);
            throw new CommandException(ConstantPage.REGISTER_PAGE);
        }

        try {
            validateInput.phoneValidate(phoneNumber);
        } catch (ValidationException e) {
            setInformMessageIfErrorOccur("err.phone", 7, request);
            throw new CommandException(ConstantPage.REGISTER_PAGE);
        }

        try {
            validateInput.passwordValidate(password);
        } catch (ValidationException e) {
            setInformMessageIfErrorOccur("err.password", 6, request);
            throw new CommandException(ConstantPage.REGISTER_PAGE);
        }

        try {
            userService.registerNewUser(name, surname, login, password, phoneNumber);
        } catch (ServiceException e) {
            logger.error("CANT REGISTER USER");
            setInformMessageIfErrorOccur("err.not_unique_login", 8, request);
            throw new CommandException(ConstantPage.REGISTER_PAGE);
        }

        route.setPathOfThePage(ConstantPage.LOG_IN_PAGE);
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}