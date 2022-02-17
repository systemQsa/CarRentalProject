package com.myproject.controller.filter;

import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
import com.myproject.exception.ServiceException;
import com.myproject.service.UserService;
import com.myproject.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class CatchPageFilter implements Filter {
    private static final Logger logger = Logger.getLogger(CatchPageFilter.class.getName());
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       // Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        Object userRole = httpRequest.getSession().getAttribute(GeneralConstant.ROLE);
        httpResponse.setHeader(GeneralConstant.CACHE_CONTROL, GeneralConstant.NO_STORE_MUST_REVALIDATE);
        httpResponse.setHeader(GeneralConstant.PRAGMA, GeneralConstant.NO_CACHE);
        httpResponse.setDateHeader(GeneralConstant.EXPIRES, 0);
//        System.out.println("USER ROLE "+ userRole);
//        System.out.println("REQUEST URL "+  httpRequest.getRequestURI());

        if (httpRequest.getRequestURI().contains("/addNewCar.jsp")){
            httpRequest.getRequestDispatcher(ConstantPage.ADD_CAR_PAGE).forward(httpRequest,httpResponse);
        }
        if (httpRequest.getRequestURI().contains("/updateCar.jsp")){
            httpRequest.getRequestDispatcher(ConstantPage.UPDATE_CAR_PAGE).forward(httpRequest,httpResponse);
        }
        if (httpRequest.getRequestURI().contains("/user.jsp")){  try {
            UserService userService = new UserServiceImpl();
            httpRequest.getSession().setAttribute("userBalance",
                    userService.getBalance((String) httpRequest.getSession().getServletContext().
                            getAttribute(GeneralConstant.USER_NAME)));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
           // httpRequest.getRequestDispatcher("redirect:/WEB-INF/view/user/user.jsp").forward(httpRequest,httpResponse);
        }

        logger.info("CatchPageFilter WORKING");
        filterChain.doFilter(httpRequest,httpResponse);

    }

    @Override
    public void destroy() {
       // Filter.super.destroy();
    }
}
