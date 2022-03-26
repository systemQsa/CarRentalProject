package com.myproject.command;

import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.Car;
import com.myproject.dao.entity.OrderViewForUserRequest;
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
import java.util.*;

/**
 * The PaginationCommand class implements Command interface.
 * Represents command to paginate cars,orders
 */
public class PaginationCommand implements Command {
    private final OrderViewService orderViewService = new AbstractFactoryImpl().getFactory().getServiceFactory().getCarViewService();
    private final CarService<Car> carService = new AbstractFactoryImpl().getFactory().getServiceFactory().getCarService();
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
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        String pageId = request.getParameter("page");
        String userRole = (String) request.getSession().getAttribute("role");
        String requiredResult = request.getParameter("required");
        String userLogin = (String) request.getSession().getAttribute("userName");
        int currentPage = Integer.parseInt(pageId);
        String viewSuchOrders = request.getParameter("viewSuchOrders");
        int noOfRecords = Integer.parseInt(request.getParameter("noOfRecords"));

        System.out.println("\nGET PAGINATION  # " + request.getParameter("page") + "\n" + "Action " + request.getParameter("action")
                + " Role " + request.getSession().getAttribute("role") + " Required " + requiredResult + " viewSuchOrders " + viewSuchOrders +
                " noOfRecords = " + noOfRecords);

        Optional<List<OrderViewForUserRequest>> requiredList;
        List<OrderViewForUserRequest> listOfRequiredItems;
        List<Car> listCars;
        Optional<HashMap<List<Car>, Integer>> allCars;
        try {
            allCars = carService.getAllCars(currentPage, noOfRecords);
            if (allCars.isPresent()) {
                paginateCars(request, currentPage, noOfRecords, allCars);
            }
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
        if (userRole == null) {
            route.setPathOfThePage(ConstantPage.HOME_PAGE);

        } else {

            if (userRole.equals("user")) {
                if (requiredResult.equals("viewMyOrders")) {
                    paginateOrdersForUser(request, route, userLogin, currentPage, noOfRecords);
                } else if (requiredResult.equals("viewCars")) {
                    route.setPathOfThePage(ConstantPage.WEB_INF_FULL_PATH_TO_USER);
                }
            }

            if (userRole.equals("manager")) {
                paginateOrdersForManager(request, route, requiredResult, currentPage, viewSuchOrders, noOfRecords);
            }
        }
        route.setRoute(Route.RouteType.FORWARD);
        return route;
    }

    private void paginateOrdersForManager(HttpServletRequest request, Route route, String requiredResult, int currentPage, String viewSuchOrders, int noOfRecords) throws CommandException {
        Optional<List<OrderViewForUserRequest>> requiredList;
        List<OrderViewForUserRequest> listOfRequiredItems;
        try {

            if (requiredResult.equals("viewOrders") && (viewSuchOrders.equals("approved"))
                    || viewSuchOrders.equals("declined")) {
                requiredList = orderViewService.getOrders(viewSuchOrders, currentPage, noOfRecords);
                if (requiredList.isPresent()) {

                    listOfRequiredItems = requiredList.get();

                    request.setAttribute("amountOfRecordsTotal", getAmountOfAllRecords(listOfRequiredItems));

                    int noOfPages = countNoOfRequiredPagesForPage(
                            getNoOfPages(listOfRequiredItems, noOfRecords), noOfRecords);

                    request.setAttribute("currentPage", getCurrentPage(noOfPages, currentPage));

                    request.setAttribute("listOrders", listOfRequiredItems);

                    request.setAttribute("noOfPages", noOfPages);

                    request.setAttribute("noOfRecords", noOfRecords);

                    request.setAttribute("viewSuchOrders", viewSuchOrders);

                    route.setPathOfThePage(ConstantPage.VIEW_ALL_ORDERS);
                }
            }
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }


    private void paginateOrdersForUser(HttpServletRequest request, Route route, String userLogin, int currentPage, int noOfRecords) throws CommandException {
        Optional<List<OrderViewForUserRequest>> requiredList;
        List<OrderViewForUserRequest> listOfRequiredItems;
        try {
            requiredList = orderViewService.getAllUserPersonalOrders(userLogin, currentPage, noOfRecords);
            if (requiredList.isPresent()) {
                listOfRequiredItems = requiredList.get();
                request.setAttribute("myPersonalOrders", listOfRequiredItems);

                request.setAttribute("amountOfRecordsTotal", getAmountOfAllRecords(listOfRequiredItems));


                int noOfPages = countNoOfRequiredPagesForPage(
                        getNoOfPages(listOfRequiredItems, noOfRecords), noOfRecords);

                request.setAttribute("noOfPages", noOfPages);

                request.setAttribute("currentPage", getCurrentPage(noOfPages, currentPage));
                request.setAttribute("noOfRecords", noOfRecords);
                route.setPathOfThePage(ConstantPage.VIEW_MY_ORDERS);
            }
        } catch (ServiceException e) {
            logger.warn("Cant get all personal user orders in PaginationCommand");
            throw new CommandException(e.getMessage());
        }
    }


    private void paginateCars(HttpServletRequest request, int currentPage, int noOfRecords, Optional<HashMap<List<Car>, Integer>> allCars) {
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("noOfRecords", noOfRecords);
        allCars.ifPresent(cars -> request.setAttribute("allCars", new ArrayList<>(cars.keySet()).get(0)));
        allCars.ifPresent(val -> request.setAttribute("amountOfRecordsTotal", val.values().stream().findFirst()));
        request.getSession().setAttribute("some", allCars.get().values().size());
        HashMap<List<Car>, Integer> map = allCars.get();
        Collection<Integer> values = map.values();

        request.setAttribute("noOfPages",
                countNoOfRequiredPagesForPage(allCars.get().values().stream().findFirst().orElse(0), noOfRecords));
    }

    private int getAmountOfAllRecords(List<OrderViewForUserRequest> listOfRequiredItems) {
        return listOfRequiredItems.stream()
                .map(OrderViewForUserRequest::getAmountOfRecords)
                .findFirst()
                .orElse(5);
    }

    private int getCurrentPage(int pages, int currentPage) {
        if (pages == 0) {
            return 1;
        }
        return currentPage;
    }

    private int countNoOfRequiredPagesForPage(int totalRecords, int noOfRecordsPerPage) {
        int result = (totalRecords / noOfRecordsPerPage);
        if (result % noOfRecordsPerPage > 0) {
            result++;
        }
        System.out.println("Number of pages " + result);
        return result;
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
