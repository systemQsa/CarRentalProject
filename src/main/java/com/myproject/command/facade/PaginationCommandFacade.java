package com.myproject.command.facade;

import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.Car;
import com.myproject.dao.entity.OrderViewForUserRequest;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.CarService;
import com.myproject.service.OrderViewService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaginationCommandFacade {
    private final OrderViewService orderViewService;
    private final CarService<Car> carService;
    private static final Logger logger = LogManager.getLogger(PaginationCommandFacade.class);


    public PaginationCommandFacade() {
        orderViewService = new AbstractFactoryImpl().getFactory().getServiceFactory().getCarViewService();
        carService = new AbstractFactoryImpl().getFactory().getServiceFactory().getCarService();
    }

    public PaginationCommandFacade(OrderViewService orderViewService, CarService<Car> carService) {
        this.orderViewService = orderViewService;
        this.carService = carService;
    }

    public Route getAllCarsForGuest(int currentPage, int noOfRecords, HttpServletRequest request) throws ServiceException {
        Route route = new Route();
        carService.getAllCars(currentPage, noOfRecords)
                .ifPresent(cars -> paginateCars(request, currentPage, noOfRecords, cars));
        route.setRoute(Route.RouteType.FORWARD);
        return route;
    }

    public Route getAllPersonalOrdersForUser(String requiredResult, String userLogin, int currentPage,
                                             int noOfRecords, HttpServletRequest request) throws CommandException {
        Route route = new Route();

        if (requiredResult.equals("viewMyOrders")) {
            paginateOrdersForUser(request, route, userLogin, currentPage, noOfRecords);
        } else if (requiredResult.equals("viewCars")) {
            route.setPathOfThePage(ConstantPage.WEB_INF_FULL_PATH_TO_USER);
        }
        route.setRoute(Route.RouteType.FORWARD);
        return route;
    }

    public Route getAllOrdersForManager(HttpServletRequest request, String requiredResult,
                                        int currentPage, String viewSuchOrders, int noOfRecords) throws CommandException {
        Route route = new Route();
        paginateOrdersForManager(request, route, requiredResult, currentPage, viewSuchOrders, noOfRecords);
        route.setRoute(Route.RouteType.FORWARD);
        return route;
    }

    private void paginateCars(HttpServletRequest request, int currentPage,
                              int noOfRecords, HashMap<List<Car>, Integer> allCars) {
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("noOfRecords", noOfRecords);
        request.setAttribute("allCars", new ArrayList<>(allCars.keySet()).get(0));
        request.setAttribute("amountOfRecordsTotal", allCars.values().stream().findFirst());
        request.setAttribute("noOfPages",
                countNoOfRequiredPagesForPage(allCars.values().stream().findFirst().orElse(0), noOfRecords));

    }

    private void paginateOrdersForUser(HttpServletRequest request, Route route,
                                       String userLogin, int currentPage, int noOfRecords) throws CommandException {
        try {
            orderViewService.getAllUserPersonalOrders(userLogin, currentPage, noOfRecords)
                    .ifPresent(orders -> {
                        request.setAttribute("myPersonalOrders", orders);
                        int noOfPages = countNoOfRequiredPagesForPage(
                                getNoOfPages(orders, noOfRecords), noOfRecords);
                        request.setAttribute("noOfPages", noOfPages);
                        request.setAttribute("currentPage", getCurrentPage(noOfPages, currentPage));
                        request.setAttribute("noOfRecords", noOfRecords);
                        route.setPathOfThePage(ConstantPage.VIEW_MY_ORDERS);
                    });
        } catch (ServiceException e) {
            logger.warn("Cant get all personal user orders in PaginationCommand");
            throw new CommandException(e.getMessage());
        }
    }

    private void paginateOrdersForManager(HttpServletRequest request, Route route,
                                          String requiredResult, int currentPage, String viewSuchOrders,
                                          int noOfRecords) throws CommandException {
        try {
            if (requiredResult.equals("viewOrders") && (viewSuchOrders.equals("approved"))
                    || viewSuchOrders.equals("declined")) {

                orderViewService.getOrders(viewSuchOrders, currentPage, noOfRecords)
                        .ifPresent(list -> {
                            request.setAttribute("amountOfRecordsTotal", getAmountOfAllRecords(list));
                            int noOfPages = countNoOfRequiredPagesForPage(
                                    getNoOfPages(list, noOfRecords), noOfRecords);
                            request.setAttribute("currentPage", getCurrentPage(noOfPages, currentPage));
                            request.setAttribute("listOrders", list);
                            request.setAttribute("noOfPages", noOfPages);
                            request.setAttribute("noOfRecords", noOfRecords);
                            request.setAttribute("viewSuchOrders", viewSuchOrders);
                            route.setPathOfThePage(ConstantPage.VIEW_ALL_ORDERS);

                        });
            }
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }

    private int getAmountOfAllRecords(List<OrderViewForUserRequest> listOfRequiredItems) {
        return listOfRequiredItems.stream()
                .map(OrderViewForUserRequest::getAmountOfRecords)
                .findFirst()
                .orElse(5);
    }

    private int getCurrentPage(int pages, int currentPage) {
        return (pages == 0) ? 1 : currentPage;
    }

    private int countNoOfRequiredPagesForPage(int totalRecords, int noOfRecordsPerPage) {
        int result = (totalRecords / noOfRecordsPerPage);
        return (result % noOfRecordsPerPage > 0)
                ? (totalRecords / noOfRecordsPerPage) + 1 : (totalRecords / noOfRecordsPerPage);
    }

    private int getNoOfPages(List<OrderViewForUserRequest> list, int noOfRecords) {
        return list.stream()
                .map(OrderViewForUserRequest::getAmountOfRecords)
                .findFirst()
                .orElseGet(() -> {
                    if (noOfRecords == 5) {
                        return 5;
                    }
                    return 1;
                });
    }
}
