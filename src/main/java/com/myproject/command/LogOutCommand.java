package com.myproject.command;

import com.myproject.command.util.CommandUtil;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
import com.myproject.command.util.Route;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.HashSet;

public class LogOutCommand implements Command {

    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) {
        Route route = new Route();
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        // ToDo delete current user (context & session)
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext().getAttribute(GeneralConstant.LOGGED_USERS);
       // System.out.println("LOGGED USERS  LOGOUT" + request.getSession().getServletContext().getAttribute(GeneralConstant.LOGGED_USERS));

        Object userName = context.getAttribute(GeneralConstant.USER_NAME);
        System.out.println("Login logout " + request.getAttribute("login"));
       // System.out.println("userName Logout "+userName + " " + request.getSession().getAttribute(GeneralConstant.ROLE));
       // System.out.println(request.getSession().getAttribute("userName"));
        if (CommandUtil.userIsLogged(request)) {
            CommandUtil.setRoleForUser(request, null, null);
            context.setAttribute(GeneralConstant.USER_NAME, null);
            session.setAttribute(GeneralConstant.ROLE,null);
            request.setAttribute(GeneralConstant.LOGIN,null);
            System.out.println("DELETED USER NAME "+ userName);
            loggedUsers.remove((String) userName);
            request.getSession().getServletContext().setAttribute(GeneralConstant.LOGGED_USERS,loggedUsers);
            System.out.println(request.getSession().getServletContext().getAttribute(GeneralConstant.LOGGED_USERS));
            //session.invalidate();
            route.setPathOfThePage(ConstantPage.HOME_PAGE);
            route.setRoute(Route.RouteType.REDIRECT);
            return route;
        }
        System.out.println("LOGGED USERS LOG OUT!");
        //System.out.println(request.getSession().getServletContext().getAttribute(GeneralConstant.LOGGED_USERS));
       // loggedUsers.remove((String) userName);
        request.getSession().getServletContext().setAttribute(GeneralConstant.LOGGED_USERS,loggedUsers);
        route.setPathOfThePage(ConstantPage.HOME_PAGE);
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}