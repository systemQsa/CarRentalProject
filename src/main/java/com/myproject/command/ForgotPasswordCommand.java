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
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * The ForgotPasswordCommand implements Command interface.
 * In case the user forgot the password the class change the old password to new one
 *
 */
public class ForgotPasswordCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ForgotPasswordCommand.class);
    private final UserService userService = new UserServiceImpl();
    private final SendEmail sendEmail = new SendEmail();
    private int secretCode;
    private String userLogin;


    /**
     * The method check if the given user is registered already. In case user is registered already
     * Sends secret code to the user email after verify the user response with desired result
     * if the verification passed user can change old pass to new one else user gets warning info
     * @param request - gets the request from the client
     * @param response - gets the response where the result will be stored after processing the request
     * @return route to where the result will be sent
     * @throws CommandException in case some problems occur during processing the request
     * @throws ValidationException in case the input data validation failed
     */
    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        String userEmail = request.getParameter("login");
        String secretNumbers = request.getParameter("secretNumbers");
        String setCommand = request.getParameter("setCommand");
         Route route = new Route();

        if (setCommand.equals("checkSecretCode")){
            request.getSession().setAttribute("userLogin",userLogin);
            if (secretCode == Integer.parseInt(secretNumbers)) {
                route.setPathOfThePage(ConstantPage.UPDATE_PASS_PAGE);
                route.setRoute(Route.RouteType.REDIRECT);
            }else {
                setInformMessageIfErrorOccur("err.password_not_match",26,request);
                logger.warn("Cannot update an new password for the user. Verification code are not match in ForgotPasswordCommand class");
                throw new CommandException(ConstantPage.UPDATE_PASS_PAGE);
            }

            return route;
        }

          if (setCommand.equals("checkUser")){
            try {
                Optional<User> user = userService.getUser(userEmail);
                if (user.isPresent()) {

                    String login = user.get().getLogin();
                    if (login == null){
                        setInformMessageIfErrorOccur("err.no_such_user",27,request);
                        throw new ServiceException(ConstantPage.FORGOT_PASS_PAGE);
                    }
                    userLogin = login;
                    request.getSession().setAttribute("userLogin",userLogin);
                    secretCode = sendEmail.sendEmailToRecipient(login);

                    route.setPathOfThePage(ConstantPage.FORGOT_PASS_PAGE);

                }
            } catch (ServiceException e) {
                setInformMessageIfErrorOccur("err.no_such_user", 16, request);
                logger.warn("Impossible to find such user " + userLogin + " in ForgotPasswordCommand class");
                throw new CommandException(ConstantPage.FORGOT_PASS_PAGE);
            }
        }

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            logger.warn("Cannot put to sleep the current thread in ForgotPasswordCommand class");
            e.printStackTrace();
        }

        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}
