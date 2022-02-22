package com.myproject.controller.filter;

 import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
import com.myproject.exception.ServiceException;
import com.myproject.service.UserService;
import com.myproject.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CatchPageFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(CatchPageFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
     }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        Object userRole = httpRequest.getSession().getAttribute(GeneralConstant.ROLE);
        httpResponse.setHeader(GeneralConstant.CACHE_CONTROL, GeneralConstant.NO_STORE_MUST_REVALIDATE);
        httpResponse.setHeader(GeneralConstant.PRAGMA, GeneralConstant.NO_CACHE);
        httpResponse.setDateHeader(GeneralConstant.EXPIRES, 0);

        if (httpRequest.getRequestURI().contains("/view/admin/addNewCar.jsp")) {
            httpRequest.getRequestDispatcher(ConstantPage.ADD_CAR_PAGE).forward(httpRequest, httpResponse);
            //httpResponse.sendRedirect("redirect:/view/admin/addNewCar.jsp");
           // filterChain.doFilter(httpRequest, httpResponse);
            return;
        }
        if (httpRequest.getRequestURI().contains("/view/admin/updateCar.jsp")) {
            httpRequest.getRequestDispatcher(ConstantPage.UPDATE_CAR_PAGE).forward(httpRequest, httpResponse);
            //filterChain.doFilter(httpRequest, httpResponse);
            return;
        }

        if (httpRequest.getRequestURI().contains("/view/user/bookCar.jsp")) {
            httpRequest.getRequestDispatcher(ConstantPage.FULL_PATH_USER_CREATE_BOOKING).forward(httpRequest, httpResponse);
            //filterChain.doFilter(httpRequest, httpResponse);
            return;
        }
        if (httpRequest.getRequestURI().contains("/view/user/confirmReceipt.jsp")){
            httpRequest.getRequestDispatcher(ConstantPage.CONFIRM_RECEIPT_FULL_PATH).forward(httpRequest,httpResponse);
            //filterChain.doFilter(httpRequest, httpResponse);
            return;
        }
        if (httpRequest.getRequestURI().contains("/view/user/user.jsp")) {
            try {
                System.out.println("\nCatch Page  " + httpRequest.getSession().getAttribute("userLogin") + "\n" );

                UserService userService = new UserServiceImpl();
                httpRequest.getSession().setAttribute("userBalance",
                        userService.getBalance((String) httpRequest.getSession().getAttribute(GeneralConstant.USER_NAME)));
            } catch (ServiceException e) {
                logger.warn("SOME PROBLEM IN CatchPageFilter filter");
            }
         }

        logger.info("CatchPageFilter WORKING GOOD");
        filterChain.doFilter(httpRequest, httpResponse);

    }

    @Override
    public void destroy() {}
}
