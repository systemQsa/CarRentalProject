package com.myproject.command;

import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.Car;
import com.myproject.dao.entity.OrderViewForUserRequest;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.CarService;
import com.myproject.service.OrderViewService;
import com.myproject.service.impl.CarServiceImpl;
import com.myproject.service.impl.ViewOrderServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class PaginationCommand implements Command{
    private final OrderViewService orderViewService = new AbstractFactoryImpl().getFactory().getServiceFactory().getCarViewService();
    private final CarService<Car> carService = new AbstractFactoryImpl().getFactory().getServiceFactory().getCarService();
    private static final Logger logger = LogManager.getLogger(PaginationCommand.class);
    private int noOfPages;
    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        String pageId = request.getParameter("page");
        String userRole = (String) request.getSession().getAttribute("role");
        String requiredResult = request.getParameter("required");
        String userLogin = (String) request.getSession().getAttribute("userName");
        Integer currentPage = Integer.parseInt(pageId);
        String viewSuchOrders = request.getParameter("viewSuchOrders");

        System.out.println("\nGET PAGINATION  # " + request.getParameter("page") + "\n" + "Action " + request.getParameter("action")
         + " Role " + request.getSession().getAttribute("role") + " Required " + requiredResult + " viewSuchOrders " + viewSuchOrders);

        Optional<List<OrderViewForUserRequest>> requiredList;
        List<OrderViewForUserRequest> listOfRequiredItems;
        List<Car> listCars;
        Optional<HashMap<List<Car>, Integer>>  allCars;
        try {
            allCars = carService.getAllCars(currentPage);
            if (allCars.isPresent()){

                request.setAttribute("currentPage",currentPage);
                allCars.ifPresent(cars -> request.setAttribute("allCars", new ArrayList<>(cars.keySet()).get(0)));
                allCars.ifPresent(val -> request.setAttribute("amountOfRecords", val.values().stream().findFirst()));
                HashMap<List<Car>, Integer> map = allCars.get();
                Collection<Integer> values = map.values();
                noOfPages = (values.stream().findFirst().orElse(5))/5;
                if (noOfPages % 5 > 0){
                    noOfPages++;
                }
                request.setAttribute("noOfPages",noOfPages);
            }
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
        if (userRole == null){
               route.setPathOfThePage(ConstantPage.HOME_PAGE);

        }else {

            if (userRole.equals("user")){
                if (requiredResult.equals("viewMyOrders")){
                    try {
                        System.out.println("GEREEEEEEE 1\n");
                        requiredList = orderViewService.getAllUserPersonalOrders(userLogin, currentPage);
                        if (requiredList.isPresent()) {
                            listOfRequiredItems = requiredList.get();
                             request.setAttribute("myPersonalOrders", listOfRequiredItems);
                            request.setAttribute("amountOfRecords",listOfRequiredItems.stream()
                                    .map(OrderViewForUserRequest::getAmountOfRecords)
                                    .findFirst());
                            request.setAttribute("currentPage",currentPage);
                            route.setPathOfThePage(ConstantPage.VIEW_MY_ORDERS);
                            System.out.println("GEREEEEEEE 2\n");
                        }
                    } catch (ServiceException e) {
                        logger.warn("Cant get all personal user orders in PaginationCommand");
                        throw new CommandException(e.getMessage());
                    }
                }else if (requiredResult.equals("viewCars")){
                    route.setPathOfThePage(ConstantPage.WEB_INF_FULL_PATH_TO_USER);
                }
            }

            if (userRole.equals("manager")){
                try{

                    if (requiredResult.equals("viewOrders") && (viewSuchOrders.equals("approved"))
                            || viewSuchOrders.equals("declined")){
                        requiredList =  orderViewService.getOrders(viewSuchOrders,currentPage);
                        if (requiredList.isPresent()){
                            listOfRequiredItems = requiredList.get();
                            request.setAttribute("listOrders",listOfRequiredItems);
                            request.setAttribute("amountOfRecords",listOfRequiredItems.stream()
                                    .map(OrderViewForUserRequest::getAmountOfRecords)
                                    .findFirst());
                            request.setAttribute("currentPage",currentPage);
                            System.out.println("\n\nPagination manager");
                            route.setPathOfThePage(ConstantPage.VIEW_ALL_APPROVED_ORDERS);

                        }
                    }
                }catch (ServiceException e){
                    throw new CommandException(e.getMessage());
                }
            }
        }
        route.setRoute(Route.RouteType.FORWARD);
        return route;
    }
}
