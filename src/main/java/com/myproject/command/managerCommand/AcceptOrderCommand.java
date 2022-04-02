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
 * The AcceptOrderCommand class implements the Command interface.
 * Represents class that process the booking order.
 * Manager accepts the order after it registers in the system and withdraw a certain amount from user card
 */
public class AcceptOrderCommand implements Command {
    private final CarOrderService carOrderService =
            new AbstractFactoryImpl().getFactory().getServiceFactory().getCarOrderService();
    private static final Logger logger = LogManager.getLogger(AcceptOrderCommand.class);

    @Override
    public Route execute(HttpServletRequest request,
                         HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        Order acceptOrder = parseIncomeOrder(request.getParameter(GeneralConstant.Util.ACCEPT_USER_ORDER));

        try {
            if(!carOrderService.checkOrderPresenceInDb(acceptOrder)){
                 try {
                    Order resultOrder = carOrderService.setOrder(acceptOrder, true);
                     acceptOrderByManager(request, route, acceptOrder, resultOrder);
                 } catch (ServiceException e) {
                    logger.fatal("CONFIRMING RECEIPT PAYMENT ERROR");
                    setInformMessageIfErrorOccur("err.balance",18,request);
                    throw new CommandException(ConstantPage.WEB_INF_FULL_PATH_TO_MANAGER);
                }
            }
        } catch (ServiceException e) {
            setInformMessageIfErrorOccur("err.order_present",20,request);
            throw new CommandException(ConstantPage.WEB_INF_FULL_PATH_TO_MANAGER);
        }

        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }

    private void acceptOrderByManager(HttpServletRequest request,
                                      Route route, Order acceptOrder, Order resultOrder) throws ServiceException {
        if (resultOrder != null) {
            carOrderService.updateOrderByManager(
                    (String) (request.getSession().getAttribute(GeneralConstant.Util.USER_NAME)),
                    resultOrder.getOrderId(), request.getParameter(GeneralConstant.Util.APPROVED),
                    request.getParameter("feedback"));
            List<Order> orders = OrderStorage.getOrders();
            orders.remove(acceptOrder);
            request.getSession().getServletContext().setAttribute("orderList", orders);
            route.setPathOfThePage(ConstantPage.MANAGER_HOME_PAGE);
        } else {
            route.setPathOfThePage(ConstantPage.ERROR_PAGE);
        }
    }



}
