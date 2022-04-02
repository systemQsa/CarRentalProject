package com.myproject.controller.filter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * The AuthFilter class implements Filter interface.
 * The class check the urls for users which are allowed for the certain user
 */
public class AuthFilter implements Filter {
    private List<String> userAllowedUrls;
    private List<String> generalUrls;
    private List<String> adminAllowedUrls;
    private List<String> managerAllowedUrls;
    private static final Logger logger = LogManager.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userAllowedUrls = Arrays.asList("/view/user/user.jsp", "/view/user/bookCar.jsp", "/view/user/confirmReceipt.jsp",
                "/view/user/viewMyOrders.jsp", "/helloServlet", "/login.jsp", "/js/date.js");
        generalUrls = Arrays.asList("/car/", "/login.jsp", "/index.jsp", "/register.jsp", "/helloServlet", "/forgotPass.jsp", "/newPass.jsp");
        adminAllowedUrls = Arrays.asList("/view/admin/admin.jsp", "/view/admin/addNewCar.jsp", "/view/admin/updateCar.jsp", "/helloServlet", "/login.jsp");
        managerAllowedUrls = Arrays.asList("/view/manager/manager.jsp", "/view/manager/allOrdersToAccept.jsp",
                "/view/manager/viewAllApprovedOrders.jsp", "/helloServlet", "/login.jsp");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String role = (String) request.getSession().getAttribute("role");

        String ERROR_PAGE = "/error404.jsp";
        if (role == null) {
            if (!generalUrls.contains(request.getServletPath())) {
                request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            }
        }
        if (role != null) {
            if ((userAllowedUrls.contains(request.getServletPath()) && role.equals("user"))) {
                logger.info("User url allowed here " + request.getServletPath());
            } else if (adminAllowedUrls.contains(request.getServletPath()) && role.equals("admin")) {
                logger.info("Admin url allowed here " + request.getServletPath());
            } else if (managerAllowedUrls.contains(request.getServletPath()) && role.equals("manager")) {
                logger.info("Manager url allowed here " + request.getServletPath());
            } else {
                logger.info("This url is not allowed!");
                request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            }

        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        userAllowedUrls.clear();
        generalUrls.clear();
        adminAllowedUrls.clear();
        managerAllowedUrls.clear();
    }
}
