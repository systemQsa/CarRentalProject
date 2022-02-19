package com.myproject.command.userCommand;

import com.myproject.command.Command;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.service.CarOrderService;
import com.myproject.service.impl.CarOrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CountTotalReceiptCommand implements Command {
    private final CarOrderService carOrderService = new CarOrderServiceImpl();

    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        //String carId = request.getParameter("carId");
        String rentPrice = request.getParameter("rentPrice");
        Object userLogin = request.getSession().getServletContext().getAttribute("userName");
        String carName = request.getParameter("carName");
        String carClass = request.getParameter("carClass");
        String carBrand = request.getParameter("carBrand");
        String carRentPrice = request.getParameter("carRentPrice");
        String userPassport = request.getParameter("userPassport");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String withDriver = request.getParameter("flexRadioDefault");
        String userId = request.getParameter("userIdByLogin");
        String userBalance = request.getParameter("userBalance");


        System.out.println("carRentPrice " + carRentPrice + " userLogin " + userLogin +  " " +
                carName + " carClass " + carClass + " carBrand " + carBrand + " userPass " + userPassport+ " "+
                  userId + " " + userBalance);

        BigDecimal totalPrice = null;
        if (request.getParameter("fromDate") != null && request.getParameter("toDate") != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date firstDate;
            try {
                firstDate = sdf.parse(request.getParameter("fromDate"));
                Date secondDate = sdf.parse(request.getParameter("toDate"));
                long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
                long diffDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                System.out.println("diffDays " + diffDays);
                //TODO check why internal.math.FloatingDecimal.readJavaFormatString(FloatingDecimal.java:1838)
                if (diffDays > 0 && Double.parseDouble(carRentPrice) > 0) {
                   totalPrice = carOrderService.countReceipt(diffDays,
                         Double.parseDouble(carRentPrice), Boolean.parseBoolean(withDriver));
                 }
                if (Boolean.parseBoolean(withDriver)) {
                    request.getSession().setAttribute("withDriver", "Y");
                } else {
                    request.getSession().setAttribute("withDriver", "N");
                }

                request.getSession().setAttribute("passport", userPassport);
                request.getSession().setAttribute("fromDate", fromDate);
                request.getSession().setAttribute("toDate", toDate);
                request.getSession().setAttribute("totalPrice", totalPrice);
                if (BigDecimal.valueOf(Double.parseDouble(userBalance)).compareTo(totalPrice) > 0){
                    request.getSession().setAttribute("resultIfBalanceOk","You have enough balance for booking!");
                }else{
                    request.getSession().setAttribute("resultIfBalanceOk",null);
                }
            } catch (ParseException | ServiceException e) {
                throw new CommandException("SOME PROBLEM CANT COUNT TOTAL ORDER PRICE", e);
            }
        }
        route.setPathOfThePage(ConstantPage.CONFIRM_RECEIPT_PAGE);
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}