package com.myproject.command;

import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.User;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.service.UserService;
import com.myproject.service.impl.UserServiceImpl;
import com.myproject.util.SendEmail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ForgotPasswordCommand implements Command {
    private final UserService userService = new UserServiceImpl();
    private final SendEmail sendEmail = new SendEmail();
    private int secretCode;
    private String userLogin;
    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        String userEmail = request.getParameter("login");
        System.out.println("Login for recover " + userEmail);
        String secretNumbers = request.getParameter("secretNumbers");
        String setCommand = request.getParameter("setCommand");
         Route route = new Route();

        System.out.println("\ncommand setCommand == " + setCommand + "secret Nums  " + secretNumbers + "secret code " + secretCode);

        if (setCommand.equals("checkSecretCode")){
            request.getSession().setAttribute("userLogin",userLogin);
             System.out.println(secretCode == Integer.parseInt(secretNumbers));
            route.setPathOfThePage(ConstantPage.UPDATE_PASS_PAGE);
            route.setRoute(Route.RouteType.REDIRECT);
            return route;
        }

          if (setCommand.equals("checkUser")){
            try {
                Optional<User> user = userService.getUser(userEmail);
                if (user.isPresent()) {

                    String login = user.get().getLogin();
                    if (login == null){
                        throw new ServiceException("");
                    }
                    userLogin = login;
                    request.getSession().setAttribute("userLogin",userLogin);
                    secretCode = sendEmail.sendEmailToRecipient(login);

                    route.setPathOfThePage(ConstantPage.FORGOT_PASS_PAGE);

                }
            } catch (ServiceException e) {
                setInformMessageIfErrorOccur("There is no such user!", 16, request);
                throw new CommandException(ConstantPage.FORGOT_PASS_PAGE);
            }
        }

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}
