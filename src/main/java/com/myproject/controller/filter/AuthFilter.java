package com.myproject.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthFilter implements Filter {
    private final String ERROR_PAGE = "/error.jsp";
    private List<String> userAllowedUrls;
    private List<String> generalUrls;
    private List<String> adminAllowedUrls;
    private List<String> managerAllowedUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       userAllowedUrls = Arrays.asList("/view/user/user.jsp","/view/user/bookCar.jsp","/view/user/confirmReceipt.jsp",
                                       "/view/user/viewMyOrders.jsp","/helloServlet","/login.jsp","/js/date.js");
       generalUrls = Arrays.asList("/car/","/login.jsp","/index.jsp","/register.jsp","/helloServlet");
       adminAllowedUrls = Arrays.asList("/view/admin/admin.jsp","/view/admin/addNewCar.jsp","/view/admin/updateCar.jsp","/helloServlet");
       managerAllowedUrls = Arrays.asList("/view/manager/manager.jsp","/view/manager/allOrdersToAccept.jsp",
                                          "/view/manager/viewAllApprovedOrders.jsp","/helloServlet");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String role = (String) request.getSession().getAttribute("role");
        System.out.println("\n\nAuth filter" + request.getRequestURI() + " " + request.getServletPath());

        if (role == null) {
            if (!generalUrls.contains(request.getServletPath())) {
                request.getRequestDispatcher(ERROR_PAGE).forward(request,response);
             }
        }
        if (role != null){
            if ((userAllowedUrls.contains(request.getServletPath()) && role.equals("user"))){
                System.out.println("All Good 1 user allowed here\n\n");
            }else if (adminAllowedUrls.contains(request.getServletPath()) && role.equals("admin")){
                System.out.println("All Good 2 admin allowed here\n\n");
             }else if (managerAllowedUrls.contains(request.getServletPath()) && role.equals("manager")){
                System.out.println("All Good 3 manager allowed here\n\n");
            }else {
                request.getRequestDispatcher(ERROR_PAGE).forward(request,response);

               // System.out.println("\nNOT ALLOWED PATH Auth filter " + request.getRequestURI() + " " + request.getServletPath());

            }

        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        userAllowedUrls.clear();
        generalUrls.clear();
        adminAllowedUrls.clear();
        managerAllowedUrls.clear();
     }
}
