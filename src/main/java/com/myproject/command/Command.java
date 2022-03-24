package com.myproject.command;

import com.myproject.command.util.GeneralConstant;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.Order;
import com.myproject.exception.CommandException;
import com.myproject.exception.ControllerException;
import com.myproject.exception.DaoException;
import com.myproject.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Stream;


public interface Command {

    Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException;

    default HttpServletRequest setInformMessageIfErrorOccur(String message, int errNumber, HttpServletRequest request) {
        String errMSG = message.replaceAll("\\S+:","");
        String lang = (String)request.getSession().getAttribute("lang");
        ResourceBundle bundle;

        switch (lang){
            case "en":bundle = ResourceBundle.getBundle(GeneralConstant.RESOURCES,
                    new Locale(GeneralConstant.LANGUAGE_ENG, GeneralConstant.COUNTRY_US));
                      errMSG = bundle.getString(errMSG);
                System.out.println("case english");
                      break;
            case "uk": bundle = ResourceBundle.getBundle(GeneralConstant.RESOURCES,
                    new Locale(GeneralConstant.LANGUAGE_UKR, GeneralConstant.COUNTRY_UA));
                       errMSG = bundle.getString(errMSG);
                System.out.println("case Ukrainian");
                       break;
        }
        request.setAttribute("errMSG",errMSG);
        request.setAttribute("err", errNumber);
        return request;
    }

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
