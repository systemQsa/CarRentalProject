package com.myproject.command.util;

import com.myproject.command.userCommand.TopUpBalanceCommand;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class CommandUtil {
    private static final Logger logger = LogManager.getLogger(TopUpBalanceCommand.class);
    private static final HashMap<String, String> loggedUsers = new HashMap<>();

    public static void setRoleForUser(HttpServletRequest req, String role, String login) {
        HttpSession session = req.getSession();
        ServletContext context = req.getServletContext();
        context.setAttribute(GeneralConstant.USER_NAME, login);
        session.setAttribute(GeneralConstant.ROLE, role);
        session.setAttribute(GeneralConstant.USER_NAME,login);
        req.setAttribute(GeneralConstant.LOGIN,login);
        logger.info("setRoleForUser() method wordks");
    }

    public static boolean userIsLogged(HttpServletRequest request) {
        //todo get all logged users!
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext().getAttribute(GeneralConstant.LOGGED_USERS);
        ServletContext context = request.getServletContext();
        String userName = (String) context.getAttribute("userName");
        //todo  create structure to store logged users / check if  user is logged!!!
        if (loggedUsers.contains(userName)
                && (Objects.equals(request.getSession().getAttribute(GeneralConstant.ROLE),GeneralConstant.ADMIN)
                || Objects.equals(request.getSession().getAttribute(GeneralConstant.ROLE),GeneralConstant.USER)
                || Objects.equals(request.getSession().getAttribute(GeneralConstant.ROLE),GeneralConstant.MANAGER))) {
            System.out.println("\n\nloggedUsers 1 " + loggedUsers + " userName " + userName + " Login" + request.getAttribute("login"));
             logger.info("userIsLogged() method");
            return true;
        }
        //loggedUsers.add(request.getParameter(GeneralConstant.LOGIN));
       // request.getSession().getServletContext().setAttribute(GeneralConstant.LOGGED_USERS, loggedUsers);
        logger.info("userIsLogged() method");
        return false;
    }
    public static String getUserRoleFromPage(HttpServletRequest request){
        String role = request.getSession().getAttribute("role").toString();
        if (role!= null){
            logger.info("getUserRoleFromPage() method");
            return role;
        }
        logger.info("getUserRoleFromPage() method");
        return GeneralConstant.EMPTY_STRING;
    }

    public static HashMap<String, String> getLoggedUsers() {
        return loggedUsers;
    }
}

