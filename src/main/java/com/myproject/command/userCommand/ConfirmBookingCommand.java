package com.myproject.command.userCommand;

import com.myproject.command.Command;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.service.CarOrderService;
import com.myproject.service.impl.CarOrderServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfirmBookingCommand implements Command {
    private final CarOrderService carOrderService = new CarOrderServiceImpl();
    private static final Logger logger = LogManager.getLogger(ConfirmBookingCommand.class);

    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        boolean isSuccess;
        String carId = request.getParameter("carId");
        String rentPrice = request.getParameter("rentPrice");
        String userLogin = (String) request.getSession().getServletContext().getAttribute("userName");
        String carName = request.getParameter("carName");
        String carClass = request.getParameter("carClass");
        String carBrand = request.getParameter("carBrand");
        String carRentPrice = request.getParameter("carRentPrice");
        String userPassport = request.getParameter("userPassport");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String withDriver = request.getParameter("withDriver");
        String userId = request.getParameter("userIdByLogin");
        String userBalance = request.getParameter("userBalance");
        String totalPrice = request.getParameter("totalPrice");

        System.out.println("carId " + carId + " " + carRentPrice + " + carRentPrice + " + userLogin + " + userLogin" +
                " userPassport " + userPassport + " fromDate " + fromDate + " toDate " + toDate + " " +
                userId + " " + userBalance + " withDriver " + withDriver + " totalPrice " + totalPrice);

        try {
            isSuccess = carOrderService.setOrder(userPassport, fromDate, toDate, withDriver,
                    Double.parseDouble(totalPrice), Integer.parseInt(userId),
                    userLogin, Integer.parseInt(carId));
            if (isSuccess) {
                route.setPathOfThePage(ConstantPage.USER_HOME_PAGE);
            } else {
                route.setPathOfThePage("/error.jsp");
            }
        } catch (ServiceException e) {
            logger.fatal("CONFIRMING RECEIPT PAYMENT ERROR");
            throw new CommandException("CANT PAY THE RECEIPT SOME PROBLEM", e);
        }
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}
