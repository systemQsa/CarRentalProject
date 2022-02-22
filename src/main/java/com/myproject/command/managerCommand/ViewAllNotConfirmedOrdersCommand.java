package com.myproject.command.managerCommand;

import com.myproject.command.Command;
import com.myproject.command.OrderStorage;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.Order;
import com.myproject.exception.CommandException;
import com.myproject.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewAllNotConfirmedOrdersCommand implements Command {
    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        route.setPathOfThePage(ConstantPage.VIEW_ALL_NOT_ACCEPTED_ORDERS);
        List<Order> orders = OrderStorage.getOrders();
        request.getSession().getServletContext().setAttribute("orderList",orders);
        route.setRoute(Route.RouteType.FORWARD);
        return route;
    }
}
