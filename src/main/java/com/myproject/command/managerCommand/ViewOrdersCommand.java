package com.myproject.command.managerCommand;

import com.myproject.command.Command;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.OrderViewForUserRequest;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.OrderViewService;
import com.myproject.service.impl.ViewOrderServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ViewOrdersCommand implements Command {
    private final OrderViewService orderViewService = new AbstractFactoryImpl().getFactory().getServiceFactory().getCarViewService();
    private static final Logger logger = LogManager.getLogger(ViewOrdersCommand.class);

    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        String viewSuchOrders = request.getParameter("viewSuchOrders");
        Optional<List<OrderViewForUserRequest>> resultForRequest;
        int startPage = Integer.parseInt(request.getParameter("page"));

        try {

            if (viewSuchOrders.equals("approved")){
                resultForRequest =  orderViewService.getOrders(viewSuchOrders,startPage);
                if (resultForRequest.isPresent()){
                    List<OrderViewForUserRequest> approvedOrders = resultForRequest.get();
                    System.out.println("\n\norders" + approvedOrders);
                    request.getSession().setAttribute("listOrders",approvedOrders);
                    route.setPathOfThePage(ConstantPage.VIEW_ALL_APPROVED_ORDERS);
                }
            }

            if (viewSuchOrders.equals("declined")){
                resultForRequest =  orderViewService.getOrders(viewSuchOrders,startPage);
                if (resultForRequest.isPresent()){
                    List<OrderViewForUserRequest> declinedOrders = resultForRequest.get();
                    request.getSession().setAttribute("listOrders",declinedOrders);
                    route.setPathOfThePage(ConstantPage.VIEW_ALL_APPROVED_ORDERS);
                }
            }
        } catch (ServiceException e) {
            logger.warn("command to see all users approved or not approved orders failed!");
            throw new CommandException(e.getMessage());
        }
        route.setRoute(Route.RouteType.FORWARD);
        return route;
    }
}
