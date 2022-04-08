package com.myproject.controller.filter.facade;

import com.myproject.command.OrderStorage;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
import com.myproject.dao.entity.Car;
import com.myproject.exception.ServiceException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.CarService;
import com.myproject.service.DriverService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class UserFilterFacade {
    private final DriverService driverService;
    private final CarService<Car> carService;

    public UserFilterFacade(){
        driverService = new AbstractFactoryImpl().getFactory().getServiceFactory().getDriverService();
        carService = new AbstractFactoryImpl().getFactory().getServiceFactory().getCarService();
    }

    public UserFilterFacade(DriverService driverService,CarService<Car> carService){
        this.driverService = driverService;
        this.carService = carService;
    }

    public void setDriverRentalPriceForAdminPage(HttpServletRequest request){
        try {
            double driverRentalPrice = driverService.getDriverRentalPrice();
            request.getSession().setAttribute("driverRentalPrice", driverRentalPrice);
        } catch (ServiceException e) {
            request.setAttribute("errMSG", "Cant get Driver Price");
            request.setAttribute("err", 23);
        }
    }

    public boolean setInitialDataForUser(FilterChain filterChain, HttpServletRequest request,
                                          HttpServletResponse response) throws ServletException, IOException {
        if ((request.getRequestURI().contains(GeneralConstant.USER)
                && request.getSession().getAttribute(GeneralConstant.ROLE).equals("user")
                && (request.getSession().getAttribute(GeneralConstant.ROLE) != null))
                || (request.getSession().getAttribute(GeneralConstant.ROLE) == null)) {

            if (request.getParameter("action") == null) {

                try {
                    AtomicInteger noOfPages = new AtomicInteger();
                    carService.getAllCars(1, 5)
                            .ifPresent(allFoundCars -> {
                                request.setAttribute("allCars", new ArrayList<>(allFoundCars.keySet()).get(0));
                                request.getSession().setAttribute("amountOfRecordsTotal",
                                        allFoundCars.values().stream().findFirst().orElse(5));
                                request.getSession().setAttribute("records",
                                        allFoundCars.values().stream().findFirst().orElse(5));
                                request.setAttribute("currentPage", 1);
                                request.setAttribute("noOfRecords", 5);
                                noOfPages.set(allFoundCars.values().stream().findFirst().orElse(5) / 5);
                                if (noOfPages.get() % 5 > 0) noOfPages.getAndIncrement();
                                request.setAttribute("noOfPages", noOfPages);
                                request.getSession().setAttribute("totalPages", noOfPages);
                            });
                } catch (ServiceException e) {
                    request.setAttribute("errMSG", "Cant get all cars");
                    request.setAttribute("err", 24);
                }
            }
            if (request.getRequestURI().contains(GeneralConstant.USER)) {
                request.getRequestDispatcher(ConstantPage.WEB_INF_FULL_PATH_TO_USER).forward(request, response);

            }
            filterChain.doFilter(request, response);
            return true;
        }
        return false;
    }

    public boolean setInitialDataForManagerAllUnacceptedOrders(HttpServletRequest request,
                                                                HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestURI().contains(GeneralConstant.MANAGER)
                && request.getSession().getAttribute(GeneralConstant.ROLE).equals("manager")
                && (request.getSession().getAttribute(GeneralConstant.ROLE) != null)) {
            request.getSession().getServletContext().setAttribute("orderList", OrderStorage.getOrders());
            request.getRequestDispatcher(ConstantPage.WEB_INF_FULL_PATH_TO_MANAGER).forward(request, response);
            return true;
        }
        return false;
    }
}
