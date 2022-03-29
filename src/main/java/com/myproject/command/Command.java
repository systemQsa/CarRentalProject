package com.myproject.command;

import com.myproject.command.util.GeneralConstant;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.Order;
import com.myproject.exception.CommandException;
import com.myproject.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Stream;


public interface Command {

    Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException;


    /**
     * If the request was successfully processed
     * this method sets information in the desired language.
     * Puts the info into the session
     * @param message - gets informative message
     * @param infoNumber = gets the number of informative message
     * @param request - gets the request to where th information will be put
     */
    default void setSuccessMessage(String message, int infoNumber, HttpServletRequest request){
        message = getWord(message, request);
        request.getSession().setAttribute("info_num",infoNumber);
        request.getSession().setAttribute("info",message);
    }

    /**
     * This method sets information in case some problem was occurs during the process the request
     * in required language
     * @param message - gets the message that should be print to the user
     * @param errNumber - gets the number of err message
     * @param request - gets the request where the message will be put
     * @return same request
     */
    default HttpServletRequest setInformMessageIfErrorOccur(String message,
                                                            int errNumber, HttpServletRequest request) {
        String errMSG = message.replaceAll("\\S+:","");
        errMSG = getWord(errMSG,request);
        request.setAttribute("errMSG",errMSG);
        request.setAttribute("err", errNumber);
        return request;
    }

    /**
     * This method translate info and error messages to the required language
     * @param message - gets massage which should be printed to the user
     * @param request - gets request where retrieves the desired language
     * @return message in required language
     */
    default String getWord(String message, HttpServletRequest request) {
        ResourceBundle bundle;
        switch ((String) request.getSession().getAttribute("lang")){
            case "en":bundle = ResourceBundle.getBundle(GeneralConstant.RESOURCES,
                    new Locale(GeneralConstant.LANGUAGE_ENG, GeneralConstant.COUNTRY_US));
                message = bundle.getString(message);
                break;
            case "uk": bundle = ResourceBundle.getBundle(GeneralConstant.RESOURCES,
                    new Locale(GeneralConstant.LANGUAGE_UKR, GeneralConstant.COUNTRY_UA));
                message = bundle.getString(message);
                break;
        }
        return message;
    }

    /**
     * This method gets the order from the client as String representation
     * get rid of unnecessary information
     * creates the Order object
     * fill all required fields.
     * @param freshOrder - gets the order
     * @return - the order object
     * @throws CommandException in case problem occur during the parsing process
     */
    default Order parseIncomeOrder(String freshOrder) throws CommandException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

          return Stream.of(freshOrder).map(value -> value.substring(6, value.length() - 1)
                        .replaceAll("\\S+\\=", ""))
                .map(str -> str.split(","))
                .map(elements -> {
                            Order.OrderBuilder orderBuilder = new Order.OrderBuilder();
                    try {
                        orderBuilder.setUserId(Integer.parseInt(elements[1].trim()))
                                .setCar(Integer.parseInt(elements[2].trim()))
                                .setPassport(elements[3].trim())
                                .setWithDriver(elements[4].trim())
                                .setFromDate(elements[5].trim())
                                .setToDate(elements[6].trim())
                                .setDateFrom(new Timestamp(dateFormat.parse(elements[5].trim()).getTime()))
                                .setDateTo(new Timestamp(dateFormat.parse(elements[6].trim()).getTime()))
                                .setReceipt(Double.parseDouble(elements[7].trim()))
                                .setUserLogin(elements[10].trim());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return orderBuilder.build();
                        }
                ).findFirst().orElseThrow(() -> new CommandException("CANT PARSE THE ORDER"));
    }
}
