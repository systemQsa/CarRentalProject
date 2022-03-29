package com.myproject.command.userCommand;

import com.myproject.command.Command;
import com.myproject.command.OrderStorage;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.Order;
import com.myproject.exception.CommandException;
import com.myproject.exception.ValidationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The ConfirmBookingCommand class implements the Command interface.
 * Represents class that retrieves all data about the order
 * adds not approved order to the storage from where
 * the manager will see all not approved orders and will decide what to do with the order.
 * When user click to confirm the booking all the data comes here and redirects to the user page
 * but the funds re not withdraw from the balance and the order are not registered in the system
 */
public class ConfirmBookingCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ConfirmBookingCommand.class);


    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        System.out.println("CONFIRM BOOKING CLASS WORKING");
        List<Order> orderList = new ArrayList<>();
        List<Order> orders = Collections.synchronizedList(orderList);
        route.setPathOfThePage(ConstantPage.USER_HOME_PAGE);
        String passport = request.getParameter("userPassport");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String withDriver = request.getParameter("withDriver");
        String totalPrice = request.getParameter("totalPrice");
        String userIdByLogin = request.getParameter("userIdByLogin");
        String userLogin = request.getParameter("userLogin");
        String carId = request.getParameter("carId");


        Order.OrderBuilder newOrder = new Order.OrderBuilder();
        newOrder.setCar(Integer.parseInt(carId))
                .setUserLogin(userLogin)
                .setPassport(passport)
                .setFromDate(fromDate)
                .setToDate(toDate)
                .setWithDriver(withDriver)
                .setReceipt(Double.parseDouble(totalPrice))
                .setUserId(Integer.parseInt(userIdByLogin));
        orders.add(newOrder.build());

        OrderStorage.ddOrder(newOrder.build());
        logger.info("Confirming the payment");
        setSuccessMessage("info.order_send_to_process",6,request);
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}
