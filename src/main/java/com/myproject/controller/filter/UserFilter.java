package com.myproject.controller.filter;

import com.myproject.command.OrderStorage;
import com.myproject.command.util.CommandUtil;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
import com.myproject.dao.entity.Car;
import com.myproject.exception.ServiceException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.CarService;
import com.myproject.service.DriverService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * The UserFilter class implements Filter interface.
 * Represents the class that catch user actions.
 * If user login loads the start page with all available cars
 * if user clicks on back arrow without any previous actions
 * it`s automatically logout the user and redirects to the guest page
 */
public class UserFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(UserFilter.class);
    private final DriverService driverService = new AbstractFactoryImpl().getFactory()
            .getServiceFactory().getDriverService();
    private final CarService<Car> carService = new AbstractFactoryImpl().getFactory()
            .getServiceFactory().getCarService();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader(GeneralConstant.CACHE_CONTROL, GeneralConstant.NO_STORE_MUST_REVALIDATE);
        response.setHeader(GeneralConstant.PRAGMA, GeneralConstant.NO_CACHE);
        response.setDateHeader(GeneralConstant.EXPIRES, 0);
        String userRole = (String) request.getSession().getAttribute(GeneralConstant.ROLE);
        String urlPath = request.getRequestURI();
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext().getAttribute(GeneralConstant.LOGGED_USERS);
        Object userName = request.getSession().getAttribute("userName");


        if ((urlPath.contains(GeneralConstant.LOGIN)
                || urlPath.contains(GeneralConstant.REGISTER))
                && ((Objects.equals(userRole, GeneralConstant.ADMIN)
                || Objects.equals(userRole, GeneralConstant.USER)
                || Objects.equals(userRole, GeneralConstant.MANAGER)))) {
            loggedUsers.remove((String) request.getSession().getServletContext().getAttribute("userName"));
            CommandUtil.setRoleForUser(request, null, null);
            request.getServletContext().setAttribute(GeneralConstant.USER_NAME, null);
            request.getSession().setAttribute(GeneralConstant.ROLE, null);
            request.getSession().getServletContext().setAttribute(GeneralConstant.LOGGED_USERS, loggedUsers);

            response.sendRedirect("/car/index.jsp");
            logger.info("UserFilter working");
        }

       //???
//        if (Objects.equals(request.getSession().getAttribute(GeneralConstant.ROLE), "admin") && request.getRequestURI().contains("/login.jsp")
//                || request.getRequestURI().contains("/register.jsp")) {
//            // System.out.println("LOGGED USERS  FILTER 2" + request.getSession().getServletContext().getAttribute(GeneralConstant.LOGGED_USERS));
//            // System.out.println("userName Context 2"+ request.getSession().getServletContext().getAttribute("userName"));
//
//            // System.out.println("User already logged ================= " + request.getSession().getAttribute(GeneralConstant.ROLE));
//
//            // response.sendRedirect("/car" + ConstantPage.ADMIN_PAGE);
//            // request.getRequestDispatcher("redirect:/view/admin/admin.jsp").forward(request,response);
//        }

        if (request.getRequestURI().contains(GeneralConstant.ADMIN)
                && request.getSession().getAttribute(GeneralConstant.ROLE).equals("admin")
                && (request.getSession().getAttribute(GeneralConstant.ROLE) != null)) {
            try {
                double driverRentalPrice = driverService.getDriverRentalPrice();
                request.getSession().setAttribute("driverRentalPrice",driverRentalPrice);
            } catch (ServiceException e) {
                request.setAttribute("errMSG","Cant get Driver Price");
                request.setAttribute("err",23);
            }

            request.getRequestDispatcher(ConstantPage.WEB_INF_FULL_PATH_TO_ADMIN).forward(request, response);
            filterChain.doFilter(request, response);
            return;
        } else if (request.getServletPath().contains("admin") && request.getSession().getAttribute("userName") == null) {
            response.sendRedirect("/car/login.jsp");
            return;
        }


        if ((request.getRequestURI().contains(GeneralConstant.USER)
                && request.getSession().getAttribute(GeneralConstant.ROLE).equals("user")
                && (request.getSession().getAttribute(GeneralConstant.ROLE) != null))
                || (request.getSession().getAttribute(GeneralConstant.ROLE) == null)) {

            if (request.getParameter("action") == null) {

                try {
                    int noOfPages;
                    Optional<HashMap<List<Car>, Integer>> allCars = carService.getAllCars(1, 5);
                    HashMap<List<Car>, Integer> res = allCars.get();
                    allCars.ifPresent(cars -> request.setAttribute("allCars", new ArrayList<>(cars.keySet()).get(0)));
                    allCars.ifPresent(val -> request.getSession().setAttribute("amountOfRecordsTotal", val.values().stream().findFirst().orElse(5)));
                    allCars.ifPresent(v -> request.getSession().setAttribute("records", v.values().stream().findFirst().get()));
                    request.setAttribute("currentPage", 1);
                    request.setAttribute("noOfRecords", 5);
                    Collection<Integer> values = res.values();
                    noOfPages = (values.stream().findFirst().orElse(5)) / 5;
                    if (noOfPages % 5 > 0) {
                        noOfPages++;
                    }
                    request.setAttribute("noOfPages", noOfPages);
                    request.getSession().setAttribute("totalPages", noOfPages);
                } catch (ServiceException e) {
                    request.setAttribute("errMSG","Cant get all cars");
                    request.setAttribute("err",24);
                }
            }
            if (request.getRequestURI().contains(GeneralConstant.USER)) {
                request.getRequestDispatcher(ConstantPage.WEB_INF_FULL_PATH_TO_USER).forward(request, response);

            }
            filterChain.doFilter(request, response);
            return;
        }


        if ((urlPath.contains(GeneralConstant.ADMIN)) && userRole == null) {
            response.sendRedirect("/car" + ConstantPage.LOG_IN_PAGE);
            filterChain.doFilter(request, response);
            return;
        } else if (request.getSession().getAttribute("userName") == null && request.getRequestURI().contains(GeneralConstant.ADMIN)) {
            response.sendRedirect("/car/login.jsp");
            filterChain.doFilter(request, response);
            return;
        }


        if (request.getRequestURI().contains(GeneralConstant.MANAGER)
                && request.getSession().getAttribute(GeneralConstant.ROLE).equals("manager")
                && (request.getSession().getAttribute(GeneralConstant.ROLE) != null)) {
            request.getSession().getServletContext().setAttribute("orderList", OrderStorage.getOrders());
            request.getRequestDispatcher(ConstantPage.WEB_INF_FULL_PATH_TO_MANAGER).forward(request, response);
            return;
        }

        logger.info("UserFilter WORKING");
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}