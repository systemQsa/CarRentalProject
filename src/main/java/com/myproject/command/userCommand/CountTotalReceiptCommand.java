package com.myproject.command.userCommand;

import com.myproject.command.Command;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
import com.myproject.command.util.Route;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.service.CarOrderService;
import com.myproject.service.impl.CarOrderServiceImpl;
import com.myproject.validation.Validate;
import com.myproject.validation.ValidateInput;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * The CountTotalReceiptCommand class implements the Command interface.
 * Represents class that count total amount of the receipt
 * Gets the user balance on card and compare the user balance and the receipt price
 * if user balance enough  than returns the info to the user that payment can be successfully processed
 * else informs the user that the payment cannot be processed due to not enough balance on user card
 */
public class CountTotalReceiptCommand implements Command {
    private final CarOrderService carOrderService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ValidateInput.DATE_TIME_PATTERN);
    private final Validate validateInput = new ValidateInput();

    public CountTotalReceiptCommand() {
        carOrderService = new CarOrderServiceImpl();
    }

    public CountTotalReceiptCommand(CarOrderService carOrderService) {
        this.carOrderService = carOrderService;
    }

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

        try {
            validateInput.passportValidate(userPassport);
        } catch (ValidationException e) {
            setInformMessageIfErrorOccur("err.passport", 12, request);
            throw new CommandException(ConstantPage.BOOK_CAR_PAGE);
        }


        if (fromDate.isEmpty() || toDate.isEmpty()) {
            setInformMessageIfErrorOccur("err.date_time", 14, request);
            throw new CommandException(ConstantPage.BOOK_CAR_PAGE);
        }

        BigDecimal totalPrice;

        try {

            LocalDateTime dateTime1 = LocalDateTime.parse(fromDate, formatter);
            LocalDateTime dateTime2 = LocalDateTime.parse(toDate, formatter);

            if (validateInput.datesAndTimeValidate(dateTime1, dateTime2, request)) {

                orderDurationInHours = calculateHoursFromGivenDates(dateTime1, dateTime2);
            }

            if ((orderDurationInHours > 0) && Double.parseDouble(carRentPrice) > 0) {

                totalPrice = carOrderService.countReceipt(orderDurationInHours,
                        Double.parseDouble(carRentPrice), Boolean.parseBoolean(withDriver));
            } else {
                setInformMessageIfErrorOccur("err.date_time", 10, request);
                throw new CommandException(ConstantPage.BOOK_CAR_PAGE);
            }
            if (Boolean.parseBoolean(withDriver)) {

                request.getSession().getServletContext().setAttribute("withDriver", "Y");
            } else {

                request.getSession().getServletContext().setAttribute("withDriver", "N");
            }
            request.getSession().getServletContext().setAttribute("userLogin", request.getParameter("userLoginReq"));
            request.getSession().getServletContext().setAttribute("rentPriceReq", carRentPrice);
            request.getSession().getServletContext().setAttribute("passport", userPassport);
            request.getSession().getServletContext().setAttribute("fromDate", fromDate);
            request.getSession().getServletContext().setAttribute("toDate", toDate);
            request.getSession().getServletContext().setAttribute("totalPrice", String.format("%.2f", totalPrice));

            if (BigDecimal.valueOf(Double.parseDouble(userBalance)).compareTo(totalPrice) > 0) {

                request.getSession().setAttribute(GeneralConstant.Util.RES_IF_BALANCE_OK, "You have enough balance for booking!");

            } else {

                request.getSession().setAttribute(GeneralConstant.Util.RES_IF_BALANCE_OK, null);

            }
        } catch (ServiceException e) {
            setInformMessageIfErrorOccur("err.balance_low", 11, request);
            throw new CommandException(ConstantPage.BOOK_CAR_PAGE);
        }

        route.setPathOfThePage(ConstantPage.CONFIRM_RECEIPT_PAGE);
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }

    private long calculateHoursFromGivenDates(LocalDateTime dateFrom, LocalDateTime dateTo) {
        long diffInMinutes = Duration.between(dateFrom, dateTo).toMinutes();
        return diffInMinutes / 60;
    }
}
