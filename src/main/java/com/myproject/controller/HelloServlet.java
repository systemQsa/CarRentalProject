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


@WebServlet("/helloServlet")
public class HelloServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(HelloServlet.class);


    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        servletConfig.getServletContext().setAttribute(GeneralConstant.LOGGED_USERS, new HashSet<>());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        try {
            processTheRequest(request, response);
        } catch (ServletException | IOException | ControllerException e) {
           logger.fatal("GET METHOD FAILED IN SERVLET");
            throw new ServletException(e.getMessage());
        }
        logger.info("GET METHOD WORK");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        try {
            processTheRequest(req, resp);
        } catch (ServletException | IOException | ControllerException e) {
            logger.fatal("POST METHOD FAILED IN SERVLET");
            throw new ServletException(e.getMessage());
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
            throw new ControllerException(e.getMessage());
        }
         String path = route.getPathOfThePage();
        System.out.println("PATH " + path);
        if (route.getRoute().equals(Route.RouteType.FORWARD)) {
            System.gc();
            request.getRequestDispatcher(route.getPathOfThePage()).forward(request, response);
        } else if (path.contains(GeneralConstant.REDIRECT)) {
            System.gc();
            response.sendRedirect(path.replaceAll(GeneralConstant.REDIRECT, GeneralConstant.CAR));
        } else {
            System.gc();
            response.sendRedirect(str + path);
        }


//        if (route.getPathOfThePage().equals("")) {
//            System.out.println("HEREEEEE");
//        }
    }
}
