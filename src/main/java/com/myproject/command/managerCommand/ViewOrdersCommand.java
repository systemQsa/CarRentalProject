package com.myproject.command.managerCommand;

import com.myproject.command.Command;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.OrderViewService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The ViewOrdersCommand class implements the Command interface.
 * Represents class that get all approved or all declined orders for the manager
 */
public class ViewOrdersCommand implements Command {
    private final OrderViewService orderViewService =
            new AbstractFactoryImpl().getFactory().getServiceFactory().getCarViewService();
    private static final Logger logger = LogManager.getLogger(ViewOrdersCommand.class);

    @Override
    public Route execute(HttpServletRequest request,
                         HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        String viewSuchOrders = request.getParameter("viewSuchOrders");
        int startPage = Integer.parseInt(request.getParameter("page"));
        int noOfRecords = Integer.parseInt(request.getParameter("noOfRecords"));
        try {
            caseViewApprovedOrders(request, route, viewSuchOrders, startPage, noOfRecords);
            caseViewDeclinedOrders(request, route, viewSuchOrders, startPage, noOfRecords);

        } catch (ServiceException e) {
            logger.warn("command to see all users approved or not approved orders failed!");
            setInformMessageIfErrorOccur("err.cannot_get_all_orders", 29, request);
            throw new CommandException(ConstantPage.WEB_INF_FULL_PATH_TO_MANAGER);
        }
        localeDateTime(request);
        route.setRoute(Route.RouteType.FORWARD);
        return route;
    }

    private void caseViewDeclinedOrders(HttpServletRequest request, Route route, String viewSuchOrders, int startPage, int noOfRecords) throws ServiceException {
        if (viewSuchOrders.equals("declined")) {
            orderViewService.getOrders(viewSuchOrders, startPage, noOfRecords)
                    .ifPresent(result -> {
                request.getSession().setAttribute("listOrders", result);
                route.setPathOfThePage(ConstantPage.VIEW_ALL_ORDERS);
            });
        }
    }

    private void caseViewApprovedOrders(HttpServletRequest request, Route route, String viewSuchOrders, int startPage, int noOfRecords) throws ServiceException {
        if (viewSuchOrders.equals("approved")) {
            orderViewService.getOrders(viewSuchOrders, startPage, noOfRecords)
                    .ifPresent(result -> {
                        request.getSession().setAttribute("listOrders", result);
                        route.setPathOfThePage(ConstantPage.VIEW_ALL_ORDERS);
                    });
        }
    }

}
