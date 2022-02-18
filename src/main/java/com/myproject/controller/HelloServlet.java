package com.myproject.controller;

import com.myproject.command.Command;
import com.myproject.command.PageAction;
import com.myproject.command.util.GeneralConstant;
import com.myproject.command.util.Route;
import com.myproject.controller.filter.UserFilter;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@WebServlet("/helloServlet")
public class HelloServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(HelloServlet.class);


    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        servletConfig.getServletContext().setAttribute(GeneralConstant.LOGGED_USERS, new HashSet<>());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            processTheRequest(request, response);
        } catch (ServletException | IOException | ControllerException e) {
           logger.fatal("GET METHOD FAILED IN SERVLET");
        }
        logger.info("GET METHOD WORK");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            processTheRequest(req, resp);
        } catch (ServletException | IOException | ControllerException e) {
            logger.fatal("POST METHOD FAILED IN SERVLET");
            e.printStackTrace();
        }
        logger.info("POST METHOD WORK");
    }

    @Override
    public void destroy() {
    }

    private void processTheRequest(HttpServletRequest request, HttpServletResponse response) throws ControllerException, IOException, ServletException {
        String action = request.getParameter(GeneralConstant.ACTION);
        logger.info("Action " + action + "  " + request.getParameter("action"));
        logger.info("Role " + request.getSession().getAttribute("login"));


          String str = request.getContextPath();
        Command command = PageAction.getCommand(action);

        assert command != null;
        Route route;
        try {
            route = command.execute(request, response);
        } catch (CommandException | ValidationException e) {
            logger.warn("CONTROLLER EXCEPTION");
            throw new ControllerException("CONTROLLER FAILED", e);
        }
        logger.info("route " + route.getPathOfThePage());
        String path = route.getPathOfThePage();
        System.out.println("PATH " + path);
        if (route.getRoute().equals(Route.RouteType.FORWARD)) {
            request.getRequestDispatcher(route.getPathOfThePage()).forward(request, response);
        } else if (path.contains(GeneralConstant.REDIRECT)) {
            response.sendRedirect(path.replaceAll(GeneralConstant.REDIRECT, GeneralConstant.CAR));
        } else {
            response.sendRedirect(str + path);
        }

        if (route.getPathOfThePage().equals("")) {
            System.out.println("HEREEEEE");
        }
    }
}
