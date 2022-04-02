package com.myproject.command;

import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.Car;
import com.myproject.dao.entity.OrderViewForUserRequest;
import com.myproject.dao.entity.UserRole;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.CarService;
import com.myproject.service.OrderViewService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

/**
 * The PaginationCommand class implements Command interface.
 * Represents command to paginate cars,orders
 */
public class PaginationCommand implements Command {

    private final OrderViewService orderViewService =
            new AbstractFactoryImpl().getFactory().getServiceFactory().getCarViewService();

    private final CarService<Car> carService =
            new AbstractFactoryImpl().getFactory().getServiceFactory().getCarService();

    private static final Logger logger = LogManager.getLogger(PaginationCommand.class);


    /**
     * The method do pagination of cars and orders
     * for gust,user,manager
     *
     * @param request  - gets the request of required view entity(car/order)
     *                 number of the records to be seen
     *                 current page from web page
     *                 user role
     *                 user login
     * @param response - sends the response for the given request
     * @return the route where the result will be sent
     * @throws CommandException    in case some problems occur during the processing the request in PaginationCommand class
     * @throws ValidationException in case the input data failed the validation
     */
    @Override
    public Route execute(HttpServletRequest request,
                         HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        String pageId = request.getParameter("page");
        String userRole = (String) request.getSession().getAttribute("role");
        String requiredResult = request.getParameter("required");
        String userLogin = (String) request.getSession().getAttribute("userName");
        int currentPage = Integer.parseInt(pageId);
        String viewSuchOrders = request.getParameter("viewSuchOrders");
        int noOfRecords = Integer.parseInt(request.getParameter("noOfRecords"));

        try {
            carService.getAllCars(currentPage, noOfRecords)
                    .ifPresent(cars -> paginateCars(request, currentPage, noOfRecords, cars));

        } catch (ServiceException e) {
            logger.warn("Cannot get all required cars from DB in PaginationCommand class");
            throw new CommandException(e.getMessage());
        }

        if (userRole == null) {
            route.setPathOfThePage(ConstantPage.HOME_PAGE);

        } else {
            switch (userRole) {
                case "user":
                    if (requiredResult.equals("viewMyOrders")) {
                        paginateOrdersForUser(request, route, userLogin, currentPage, noOfRecords);
                    } else if (requiredResult.equals("viewCars")) {
                        route.setPathOfThePage(ConstantPage.WEB_INF_FULL_PATH_TO_USER);
                    }
                    break;

                case "manager":
                    paginateOrdersForManager(request, route, requiredResult, currentPage, viewSuchOrders, noOfRecords);

            }
        }

        localeDateTime(request);
        route.setRoute(Route.RouteType.FORWARD);
        return route;
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


    private void paginateCars(HttpServletRequest request, int currentPage,
                              int noOfRecords, HashMap<List<Car>, Integer> allCars) {
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("noOfRecords", noOfRecords);
        request.setAttribute("allCars", new ArrayList<>(allCars.keySet()).get(0));
        request.setAttribute("amountOfRecordsTotal", allCars.values().stream().findFirst());
        request.setAttribute("noOfPages",
                countNoOfRequiredPagesForPage(allCars.values().stream().findFirst().orElse(0), noOfRecords));

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
