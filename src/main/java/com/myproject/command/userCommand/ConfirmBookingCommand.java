package com.myproject.command.userCommand;

import com.myproject.command.Command;
import com.myproject.command.OrderStorage;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.Order;
import com.myproject.exception.CommandException;
import com.myproject.exception.ValidationException;
import com.myproject.service.CarOrderService;
import com.myproject.service.impl.CarOrderServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConfirmBookingCommand implements Command {
    private final CarOrderService carOrderService = new CarOrderServiceImpl();
    private static final Logger logger = LogManager.getLogger(ConfirmBookingCommand.class);

    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        boolean isSuccess;
//        try {
//            isSuccess = carOrderService.setOrder(request.getParameter("userPassport"), request.getParameter("fromDate"),
//                    request.getParameter("toDate"), request.getParameter("withDriver"),
//                    Double.parseDouble(request.getParameter("totalPrice")), Integer.parseInt(request.getParameter("userIdByLogin")),
//                    (String) request.getSession().getServletContext().getAttribute("userName"),
//                    Integer.parseInt(request.getParameter("carId")));
//            if (isSuccess) {
//                route.setPathOfThePage(ConstantPage.USER_HOME_PAGE);
//            } else {
//                route.setPathOfThePage("/error.jsp");
//            }
//        } catch (ServiceException e) {
//            logger.fatal("CONFIRMING RECEIPT PAYMENT ERROR");
//            throw new CommandException("CANT PAY THE RECEIPT SOME PROBLEM", e);
//        }

        List<Order> orderList = new ArrayList<>();
        List<Order> orders = Collections.synchronizedList(orderList);
        route.setPathOfThePage(ConstantPage.USER_HOME_PAGE);
        String passport = request.getParameter("userPassport");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String withDriver = request.getParameter("withDriver");
        String totalPrice = request.getParameter("totalPrice");
        String userIdByLogin = request.getParameter("userIdByLogin");
        System.out.println("\n User login " + request.getParameter("userLogin") + "\n");
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

        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}
