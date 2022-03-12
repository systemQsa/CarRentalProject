package com.myproject.controller.filter;

import com.myproject.command.PaginationCommand;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
import com.myproject.command.util.Route;
import com.myproject.exception.CommandException;
import com.myproject.exception.ValidationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;

public class RedirectFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(RedirectFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String userRole = (String) request.getSession().getAttribute(GeneralConstant.ROLE);
        response.setHeader(GeneralConstant.CACHE_CONTROL, GeneralConstant.NO_STORE_MUST_REVALIDATE);
        response.setHeader(GeneralConstant.PRAGMA, GeneralConstant.NO_CACHE);
        response.setDateHeader(GeneralConstant.EXPIRES, 0);
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext().getAttribute(GeneralConstant.LOGGED_USERS);
        String userName = (String) request.getSession().getAttribute("userName");
        System.out.println("PATH  " + request.getRequestURI() + " " + userRole + "  " + request.getSession().getAttribute("userName"));


        if (userRole != null && (Objects.equals(userRole, "admin") || Objects.equals(userRole, "user")
                || Objects.equals(userRole, "manager")) && (request.getRequestURI().contains("/login.jsp")
                || request.getRequestURI().contains("/register.jsp") || request.getRequestURI().contains("/index.jsp"))) {
            if (loggedUsers != null) {
                loggedUsers.remove(userName);
            }
            request.getSession().setAttribute(GeneralConstant.ROLE, null);
            request.getSession().setAttribute(GeneralConstant.USER_NAME, null);
           // System.out.println("Stop================= " + userRole);
           // System.out.println(loggedUsers);
            response.sendRedirect("/car");
        }

//        if ( loggedUsers != null && loggedUsers.contains(userRole) && request.getServletPath().contains("/car/")) {
//            System.out.println("user logged filter");
//           // request.getServletContext().setAttribute(GeneralConstant.USER_NAME, null);
//            request.getSession().setAttribute(GeneralConstant.ROLE, null);
//            response.sendRedirect("/car" + ConstantPage.HOME_PAGE);
//        }

//        if (request.getRequestURI().contains(GeneralConstant.ADMIN) && userRole.equals("admin")) {
//            System.out.println("User Role " + userRole);
//            request.getRequestDispatcher(ConstantPage.WEB_INF_FULL_PATH_TO_ADMIN).forward(request, response);
//        }
//        if (request.getRequestURI().contains(GeneralConstant.USER) && userRole.equals("user")) {
//            System.out.println("User Role " + userRole);
//            request.getRequestDispatcher(ConstantPage.WEB_INF_FULL_PATH_TO_USER).forward(request, response);
//        }
//        if ((request.getRequestURI().contains(GeneralConstant.USER)
//                || request.getRequestURI().contains(GeneralConstant.USER)) && userRole == null){
//            request.getRequestDispatcher(ConstantPage.HOME_PAGE).forward(request,response);
//        }

//        String urlPath = request.getRequestURI();
//        if ((urlPath.contains(GeneralConstant.LOGIN) || urlPath.contains(GeneralConstant.REGISTER))
//                && (Objects.equals(userRole, GeneralConstant.ADMIN))){
//            request.getRequestDispatcher(ConstantPage.WEB_INF_FULL_PATH_TO_ADMIN).forward(request,response);
//
//        }

        if (Objects.equals(request.getParameter("action"), "findAllCars")
                || Objects.equals(request.getParameter("action"), "findAllUsers")) {
            request.getRequestDispatcher("/helloServlet").forward(request, response);
            return;
        }

        if (request.getParameter("search") != null || request.getParameter("sort") != null) {
            request.getRequestDispatcher("/helloServlet").forward(request, response);
            return;
        }

//        System.out.println(request.getContextPath() + " context path");
//        System.out.println(request.getRequestURI() + " request URI");
//        System.out.println(request.getServletPath() + " servlet path");
//        System.out.println("Action " + request.getParameter("action"));

        if (Objects.equals(request.getParameter("action"), "pagination")) {
            try {
                Route route = new PaginationCommand().execute(request, response);
               // System.out.println("\n\\path back " + route.getPathOfThePage());
                request.getRequestDispatcher(route.getPathOfThePage()).forward(request, response);
                return;
            } catch (CommandException | ValidationException e) {
                throw new ServletException(e.getMessage());
            }
        }


        logger.info("REDIRECT FILTER");
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
