package com.myproject.controller;

import com.myproject.command.Command;
import com.myproject.command.PageAction;
import com.myproject.command.util.GeneralConstant;
import com.myproject.command.util.Route;
import com.myproject.exception.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

/**
 * The HelloServlet class represents controller which accept client requests and command to be executed
 * redirects to the desired class which implements the desired command
 */
@WebServlet(name = "helloServlet", urlPatterns = "/helloServlet")
public class HelloServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(HelloServlet.class);


    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        servletConfig.getServletContext().setAttribute(GeneralConstant.LOGGED_USERS, new HashSet<>());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            processTheRequest(request, response);
        } catch (ServletException | IOException | ControllerException e) {
            logger.fatal("GET METHOD FAILED IN SERVLET");
            throw new ServletException(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            processTheRequest(req, resp);
        } catch (ServletException | IOException | ControllerException e) {
            logger.fatal("POST METHOD FAILED IN SERVLET");
            throw new ServletException(e.getMessage());
        }
    }

    private void processTheRequest(HttpServletRequest request,
                                   HttpServletResponse response) throws ControllerException, IOException, ServletException {

        String action = request.getParameter(GeneralConstant.ACTION);
        logger.info("Role " + request.getSession().getAttribute("login"));
        logger.info("Action " + request.getParameter("action"));
        if (request.getParameter("search") != null) {
            action = "search";
        }

        String str = request.getContextPath();
        Command command;
        if (action != null) {
            command = PageAction.getCommand(action);

            Route route;
            try {
                route = command.execute(request, response);
            } catch (CommandException | ValidationException e) {
                logger.warn("CONTROLLER EXCEPTION " + e.getMessage() + " URI = " + request.getRequestURI() + " URL = " + request.getRequestURL() + " SERVPATH = " + request.getServletPath());
                request.getRequestDispatcher(e.getMessage()).forward(request, response);
                throw new ControllerException(e.getMessage());
            }
            String path = route.getPathOfThePage();
            if (route.getRoute().equals(Route.RouteType.FORWARD)) {
                request.getRequestDispatcher(route.getPathOfThePage()).forward(request, response);
            } else if (path.contains(GeneralConstant.REDIRECT)) {
                response.sendRedirect(path.replaceAll(GeneralConstant.REDIRECT, GeneralConstant.CAR));
            } else {
                response.sendRedirect(str + path);
            }
        }
    }

    @Override
    public void destroy() {}
}
