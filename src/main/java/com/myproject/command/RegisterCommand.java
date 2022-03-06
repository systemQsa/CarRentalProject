package com.myproject.command;

import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
import com.myproject.command.util.Route;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.UserService;
import com.myproject.service.impl.UserServiceImpl;
import com.myproject.validation.ValidateInput;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegisterCommand.class);
    final ValidateInput validateInput = new ValidateInput();
    private final UserService userService = new AbstractFactoryImpl().getFactory().getServiceFactory().getUserService();

    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        //todo validate fields add to DB data
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String phoneNumber = request.getParameter("phone");
        String login = request.getParameter(GeneralConstant.LOGIN);
        char[] password = request.getParameter(GeneralConstant.PASSWORD).toCharArray();
        Route route = new Route();

         try {
            validateInput.validateInputNameSurname(name, surname, request);
        } catch (ValidationException e) {
            setInformMessageIfErrorOccur(GeneralConstant.ErrorMSG.INVALID_NAME_SURNAME, 4, request);
              throw new CommandException(ConstantPage.REGISTER_PAGE);
        }

        try {
            validateInput.validateLogin(login, request, response);
        } catch (ValidationException e) {
            setInformMessageIfErrorOccur(GeneralConstant.ErrorMSG.INVALID_USERNAME_LOGIN, 5, request);
             throw new CommandException(ConstantPage.REGISTER_PAGE);
        }
        try {
            validateInput.validatePassword(password, request);
        } catch (ValidationException e) {
            setInformMessageIfErrorOccur(GeneralConstant.ErrorMSG.INVALID_PASS, 6, request);
             throw new CommandException(ConstantPage.REGISTER_PAGE);
        }
        try {
            validateInput.validatePhoneNumber(phoneNumber, request);
        } catch (ValidationException e) {
            setInformMessageIfErrorOccur(GeneralConstant.ErrorMSG.INVALID_PHONE, 7, request);
            throw new CommandException(ConstantPage.REGISTER_PAGE);
        }

        try {
            userService.registerNewUser(name, surname, login, password, phoneNumber);
        } catch (ServiceException e) {
            logger.error("CANT REGISTER USER");
            setInformMessageIfErrorOccur(GeneralConstant.ErrorMSG.NOT_UNIQUE_LOGIN, 8, request);
            throw new CommandException(ConstantPage.REGISTER_PAGE);
        }


        route.setPathOfThePage(ConstantPage.LOG_IN_PAGE);
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}