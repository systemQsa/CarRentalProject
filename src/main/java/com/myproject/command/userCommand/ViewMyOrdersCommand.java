package com.myproject.command.userCommand;

import com.myproject.command.Command;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.OrderViewForUserRequest;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.service.OrderViewService;
import com.myproject.service.impl.ViewOrderServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The ViewMyOrdersCommand class implements the Command interface.
 * Gets all personal orders for the user
 */

public class ViewMyOrdersCommand implements Command {
    private final OrderViewService orderViewService = new ViewOrderServiceImpl();
    private static final Logger logger = LogManager.getLogger(ViewMyOrdersCommand.class);

    @Override
    public Route execute(HttpServletRequest request,
                         HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        String login = (String) request.getSession().getAttribute("userName");
        Optional<List<OrderViewForUserRequest>> resultOfMyPersonalOrders;

        try {
            resultOfMyPersonalOrders = orderViewService.getAllUserPersonalOrders(login, 0, 5);

            resultOfMyPersonalOrders.ifPresent(orders -> {
                request.getSession().setAttribute("myPersonalOrders", orders);
                request.setAttribute("dateTimeFormatter",
                        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                        .withLocale(((ResourceBundle)request.getSession().getAttribute("language")).getLocale()));
                route.setPathOfThePage(ConstantPage.VIEW_MY_ORDERS);
            });

        } catch (ServiceException e) {
            logger.warn("something went wrong with sending user his orders in ViewMyOrdersCommand class");
            setInformMessageIfErrorOccur("You don`t have orders yet", 9, request);
            throw new CommandException("/WEB-INF/view/user/viewMyOrders.jsp");
        }
        route.setRoute(Route.RouteType.FORWARD);
        return route;
    }
}
