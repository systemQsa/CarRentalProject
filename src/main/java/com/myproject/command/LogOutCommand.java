package com.myproject.command;

import com.myproject.command.util.CommandUtil;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
import com.myproject.command.util.Route;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

public class LogOutCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LogOutCommand.class);

    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) {
        Route route = new Route();
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        // ToDo delete current user (context & session)
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext().getAttribute(GeneralConstant.LOGGED_USERS);

        Object userName = context.getAttribute(GeneralConstant.USER_NAME);
        System.out.println("Login logout " + request.getAttribute(GeneralConstant.LOGIN));
        if (CommandUtil.userIsLogged(request)) {
            CommandUtil.setRoleForUser(request, null, null);
            context.setAttribute(GeneralConstant.USER_NAME, null);
            session.setAttribute(GeneralConstant.ROLE, null);
            request.setAttribute(GeneralConstant.LOGIN, null);
            System.out.println("DELETED USER NAME " + userName);
            loggedUsers.remove((String) userName);
            request.getSession().getServletContext().setAttribute(GeneralConstant.LOGGED_USERS, loggedUsers);
            System.out.println(request.getSession().getServletContext().getAttribute(GeneralConstant.LOGGED_USERS));
            logger.info("USER LOGGED OUT");
            route.setPathOfThePage(ConstantPage.HOME_PAGE);
            route.setRoute(Route.RouteType.REDIRECT);
            return route;
        }
       // System.out.println("LOGGED USERS LOG OUT!");
       // logger.info("USER LOGGED OUT");
       // request.getSession().getServletContext().setAttribute(GeneralConstant.LOGGED_USERS, loggedUsers);
       // route.setPathOfThePage(ConstantPage.HOME_PAGE);
       // route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}