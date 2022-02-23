package com.myproject.command.managerCommand;

import com.myproject.command.Command;
import com.myproject.command.OrderStorage;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.Order;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.service.CarOrderService;
import com.myproject.service.impl.CarOrderServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

public class AcceptOrderCommand implements Command {
    private final CarOrderService carOrderService = new CarOrderServiceImpl();
    private static final Logger logger = LogManager.getLogger(AcceptOrderCommand.class);

    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        String order = request.getParameter("acceptUserOrder");
        Order acceptOrder = parseIncomeOrder(request.getParameter("acceptUserOrder"));
        boolean isSuccess = false;
        HashMap<String, Boolean> orderHashMap = new HashMap<>();
        try {
            Order resultOrder = carOrderService.setOrder(acceptOrder.getPassport(), acceptOrder.getFromDate(),
                    acceptOrder.getToDate(), acceptOrder.getWithDriver(),
                    acceptOrder.getReceipt(), acceptOrder.getUserId(),
                    acceptOrder.getUserLogin(),
                    acceptOrder.getCarId(), true);

            if (resultOrder != null) {

                carOrderService.updateOrderByManager(
                        (String) (request.getSession().getAttribute("userName")),
                        resultOrder.getOrderId(), request.getParameter("approved"),
                        request.getParameter("feedback"));
                List<Order> orders = OrderStorage.getOrders();
               // System.out.println("\n OrDER  Accept" + acceptOrder + "\n");
                 orders.remove(acceptOrder);
                 request.getSession().getServletContext().setAttribute("orderList", orders);
                route.setPathOfThePage(ConstantPage.MANAGER_HOME_PAGE);
            } else {
                  route.setPathOfThePage("/error.jsp");
            }
        } catch (ServiceException e) {
            logger.fatal("CONFIRMING RECEIPT PAYMENT ERROR");
            throw new CommandException(e.getMessage());
        }
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }


}
