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
import com.myproject.service.impl.CarOrderServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DeclineOrderCommand implements Command {
    private final CarOrderService carOrderService = new AbstractFactoryImpl().getFactory().getServiceFactory().getCarOrderService();
    private static final Logger logger = LogManager.getLogger(DeclineOrderCommand.class);


    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        String order = request.getParameter("declineUserOrder");
        boolean declineOrderResult;
        Order resultOrder = parseIncomeOrder(order);

//        System.out.println("\n" + request.getSession().getAttribute("userName") + "  ===    User NAME " +
//                "order id " + resultOrder.getOrderId() + " " + request.getParameter("approved") +
//                " " + request.getParameter("feedback") + "\n");

        try {
            Order declinedOrder = carOrderService.setOrder(resultOrder, false);

            if (declinedOrder != null) {

                declineOrderResult = carOrderService.updateOrderByManager(
                        (String) request.getSession().getAttribute(GeneralConstant.Util.USER_NAME),
                        declinedOrder.getOrderId(), request.getParameter(GeneralConstant.Util.APPROVED),
                        request.getParameter("feedback")
                );

                if (declineOrderResult) {
                    List<Order> orders = OrderStorage.getOrders();

                    boolean remove = orders.remove(resultOrder);
                    System.out.println("\n OrDER  Declined" + resultOrder + " \n"+remove);

                    request.getSession().getServletContext().setAttribute("orderList", orders);
                }
            }

        } catch (ServiceException e) {
            logger.warn("Something went wrong cant cancel the user order");
            throw new CommandException(e.getMessage());
        }


        route.setPathOfThePage(ConstantPage.MANAGER_HOME_PAGE);
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}
