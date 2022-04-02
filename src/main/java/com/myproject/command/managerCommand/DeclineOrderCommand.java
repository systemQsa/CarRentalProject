package com.myproject.command.managerCommand;

import com.myproject.command.Command;
import com.myproject.command.OrderStorage;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.Order;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.CarOrderService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The DeclineOrderCommand class implements the Command interface.
 * Represents class that process the order booking.
 * The manager decides if the order will be approved or not.
 * In case some problems occur during the process the manager will be informed therefore the booking will be canceled
 * the user will be informed that the order were canceled
 */

public class DeclineOrderCommand implements Command {
    private final CarOrderService carOrderService =
            new AbstractFactoryImpl().getFactory().getServiceFactory().getCarOrderService();
    private static final Logger logger = LogManager.getLogger(DeclineOrderCommand.class);


    @Override
    public Route execute(HttpServletRequest request,
                         HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();

        String order = request.getParameter("declineUserOrder");
        boolean declineOrderResult;
        Order resultOrder = parseIncomeOrder(order);

        try {
            Order declinedOrder = carOrderService.setOrder(resultOrder, false);

            if (declinedOrder != null) {
                    declineOrderResult = carOrderService.updateOrderByManager(
                        (String) request.getSession().getAttribute(GeneralConstant.Util.USER_NAME),
                        declinedOrder.getOrderId(), request.getParameter(GeneralConstant.Util.APPROVED),
                        request.getParameter("feedback")
                );
                    setDeclineOrderResult(request, declineOrderResult, resultOrder);
            }

        } catch (ServiceException e) {
            logger.warn("Something went wrong cant cancel the user order");
            throw new CommandException(e.getMessage());
        }


        route.setPathOfThePage(ConstantPage.MANAGER_HOME_PAGE);
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }

    private void setDeclineOrderResult(HttpServletRequest request, boolean declineOrderResult, Order resultOrder) {
        if (declineOrderResult) {
            List<Order> orders = OrderStorage.getOrders();
            orders.remove(resultOrder);
            request.getSession().getServletContext().setAttribute("orderList", orders);
        }
    }
}
