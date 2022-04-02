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

/**
 * The CommandUtil class register the user in the system.
 * Check if user is logged in already.
 * Sets role for the user
 * Gets the role from income page
 */

public class CommandUtil {
    private static final Logger logger = LogManager.getLogger(TopUpBalanceCommand.class);
    private static final HashMap<String, String> loggedUsers = new HashMap<>();

    /**
     * The method  sets role user login into the system
     *
     * @param req   - gets the request from web page
     * @param role  - gets defined user role
     * @param login - gets user role
     */
    public static void setRoleForUser(HttpServletRequest req, String role, String login) {
        HttpSession session = req.getSession();
        ServletContext context = req.getServletContext();
        context.setAttribute(GeneralConstant.USER_NAME, login);
        session.setAttribute(GeneralConstant.ROLE, role);
        session.setAttribute(GeneralConstant.USER_NAME, login);
        req.setAttribute(GeneralConstant.LOGIN, login);
        logger.info("setRoleForUser() method works");
    }

    /**
     * The method checks if user already logged into the system
     *
     * @param request - gets the request with required data from the client
     * @return if the user is logged into the system returns true else returns false
     */
    public boolean userIsLogged(HttpServletRequest request) {
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext().getAttribute(GeneralConstant.LOGGED_USERS);
        String userName = (String) request.getSession().getAttribute(GeneralConstant.USER_NAME);
        String login = request.getParameter("login");
        boolean response = false;

        switch (request.getParameter("action")) {
            case "login": response = loggedUsers.contains(login); break;

            case "logout": response =  ((loggedUsers.contains(userName))
                    && (Objects.equals(request.getSession()
                    .getAttribute(GeneralConstant.ROLE), GeneralConstant.ADMIN)
                    || Objects.equals(request.getSession()
                    .getAttribute(GeneralConstant.ROLE), GeneralConstant.USER)
                    || Objects.equals(request.getSession()
                    .getAttribute(GeneralConstant.ROLE), GeneralConstant.MANAGER))); break;
        }

       return response;
    }

    /**
     * The method defines the user role from the page
     *
     * @param request - gets the request with all required data
     * @return user role
     */
    public static String getUserRoleFromPage(HttpServletRequest request) {
        String role = request.getSession().getAttribute("role").toString();
        if (role != null) {
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

