package com.myproject.command.userCommand;

import com.myproject.command.Command;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.service.CarOrderService;
import com.myproject.service.impl.CarOrderServiceImpl;
import com.myproject.validation.ValidateInput;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CountTotalReceiptCommand implements Command {
    private final CarOrderService carOrderService = new CarOrderServiceImpl();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ValidateInput.DATE_TIME_PATTERN);

    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        long orderDurationInHours = 0;

        String carRentPrice = request.getParameter("carRentPrice");
        String userPassport = request.getParameter("userPassport");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String withDriver = request.getParameter("flexRadioDefault");
        String userBalance = request.getParameter("userBalance");
        String userLogin = request.getParameter("userLogin");

        System.out.println("\nCount receipt!!!!  "
        + "passport " +userPassport + " fromDate" + fromDate + " toDAte" + toDate
         + "carPrice " + carRentPrice + " userBalance "+ userBalance + "userLogin " + userLogin);

       ValidateInput.validatePassport(userPassport,request);

       if (fromDate.isEmpty() || toDate.isEmpty()){
           setInformMessageIfErrorOccur("Please enter correct date and time",14,request);
           throw new CommandException("/WEB-INF/view/user/bookCar.jsp");
       }

         BigDecimal totalPrice;
//        if (request.getParameter("fromDate") != null && request.getParameter("toDate") != null) {

            try {

                LocalDateTime dateTime1 = LocalDateTime.parse(fromDate, formatter);
                LocalDateTime dateTime2 = LocalDateTime.parse(toDate, formatter);

                if (ValidateInput.validateDatesAndTime(dateTime1, dateTime2,request)) {

                    orderDurationInHours = calculateHoursFromGivenDates(dateTime1, dateTime2);
                }

                if ((orderDurationInHours > 0) && Double.parseDouble(carRentPrice) > 0) {

                    totalPrice = carOrderService.countReceipt(orderDurationInHours,
                            Double.parseDouble(carRentPrice), Boolean.parseBoolean(withDriver));
                } else {
                    setInformMessageIfErrorOccur("Please enter date and time properly!",10,request);
                    throw new CommandException("/WEB-INF/view/user/bookCar.jsp");
                }
                if (Boolean.parseBoolean(withDriver)) {

                    request.getSession().getServletContext().setAttribute("withDriver", "Y");
                } else {

                    request.getSession().getServletContext().setAttribute("withDriver", "N");
                }
                request.getSession().getServletContext().setAttribute("userLogin",request.getParameter("userLoginReq") );
                request.getSession().getServletContext().setAttribute("rentPriceReq",carRentPrice);
                request.getSession().getServletContext().setAttribute("passport", userPassport);
                request.getSession().getServletContext().setAttribute("fromDate", fromDate);
                request.getSession().getServletContext().setAttribute("toDate", toDate);
                request.getSession().getServletContext().setAttribute("totalPrice", String.format("%.2f", totalPrice));

                if (BigDecimal.valueOf(Double.parseDouble(userBalance)).compareTo(totalPrice) > 0) {

                    request.getSession().setAttribute("resultIfBalanceOk", "You have enough balance for booking!");

                } else {

                 request.getSession().setAttribute("resultIfBalanceOk", null);

                }
            } catch (ServiceException e) {

                setInformMessageIfErrorOccur("You don`t have enough charge for booking. Top up your balance and try again!",11,request);
                 throw new CommandException("/WEB-INF/view/user/bookCar.jsp");
            }
//        }

        route.setPathOfThePage(ConstantPage.CONFIRM_RECEIPT_PAGE);
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }

    private long calculateHoursFromGivenDates(LocalDateTime dateFrom, LocalDateTime dateTo) {
        long diffInMinutes = Duration.between(dateFrom, dateTo).toMinutes();
        return diffInMinutes / 60;
    }
}
