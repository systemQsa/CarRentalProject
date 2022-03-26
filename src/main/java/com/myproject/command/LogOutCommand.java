package com.myproject.command;

import com.myproject.command.util.CommandUtil;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
import com.myproject.command.util.Route;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

/**
 * The LogOutCommand class implements Command interface.
 * The class process the logout command
 */
public class LogOutCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LogOutCommand.class);
    private final CommandUtil commandUtil = new CommandUtil();

    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) {
        Route route = new Route();
        HttpSession session = request.getSession();
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext().getAttribute(GeneralConstant.LOGGED_USERS);

        Object userName = request.getSession().getAttribute(GeneralConstant.USER_NAME);
        if (commandUtil.userIsLogged(request)) {
            CommandUtil.setRoleForUser(request, null, null);
            session.setAttribute(GeneralConstant.ROLE, null);
            session.setAttribute(GeneralConstant.USER_NAME, null);
            request.setAttribute(GeneralConstant.LOGIN, null);
            loggedUsers.remove((String) userName);
            request.getSession().getServletContext().setAttribute(GeneralConstant.LOGGED_USERS, loggedUsers);
            System.out.println(request.getSession().getServletContext().getAttribute(GeneralConstant.LOGGED_USERS));
            logger.info("User is logged out");
            route.setPathOfThePage(ConstantPage.HOME_PAGE);
            route.setRoute(Route.RouteType.REDIRECT);
            return route;
        }
        return route;
    }
}