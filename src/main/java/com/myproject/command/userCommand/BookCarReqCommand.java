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
import javax.servlet.http.HttpSession;


public class BookCarReqCommand implements Command {
    private static final Logger logger = LogManager.getLogger(BookCarReqCommand.class);
    private final CarOrderService carBookingService = new CarOrderServiceImpl();

    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        boolean isSuccessfullyBooked;
        HttpSession session = request.getSession();
        try {
            isSuccessfullyBooked = carBookingService.bookTheCar(request, response);
            if (isSuccessfullyBooked) {
                String carId = request.getParameter("carId");
                String rentPrice = request.getParameter("rentPrice");
                Object userLogin = request.getSession().getServletContext().getAttribute("userName");
                 session.setAttribute("carIdReq",request.getParameter("carId"));
                session.setAttribute("carNameReq",request.getParameter("carName"));
                session.setAttribute("carClassReq",request.getParameter("carClass"));
                 session.setAttribute("carBrandReq",request.getParameter("carBrand"));
                session.setAttribute("rentPriceReq",request.getParameter("rentPrice"));
                session.setAttribute("userLoginReq",request.getSession().getServletContext().getAttribute("userName"));
                route.setPathOfThePage(ConstantPage.USER_CREATE_BOOKING_PAGE);
            }else {
                route.setPathOfThePage("/car/error.jsp");
            }
        } catch (ServiceException e) {
            logger.error("SOMETHING WENT WRONG CANT BOOK THE ORDER IN BookCarReqCommand class");
            throw new CommandException("CANT BOOK THE ORDER", e);
        }
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}
