package com.myproject.command;

import com.myproject.command.util.Route;
import com.myproject.dao.entity.Order;
import com.myproject.exception.CommandException;
import com.myproject.exception.ControllerException;
import com.myproject.exception.DaoException;
import com.myproject.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Stream;

public interface Command {
    Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException,ValidationException;
    default void setInformMessageIfErrorOccur(String message,HttpServletRequest request){
        request.getSession().setAttribute("errorMSG",message);
    }

    default  Order parseIncomeOrder(String freshOrder) throws CommandException {
        return Stream.of(freshOrder).map(value -> value.substring(6, value.length() - 1)
                        .replaceAll("\\S+\\=", ""))
                .map(str -> str.split(","))
                .map(elements -> {
                            Order.OrderBuilder orderBuilder = new Order.OrderBuilder();
                            orderBuilder.setUserId(Integer.parseInt(elements[1].trim()))
                                    .setCar(Integer.parseInt(elements[2].trim()))
                                    .setPassport(elements[3].trim())
                                    .setWithDriver(elements[4].trim())
                                    .setFromDate(elements[5].trim())
                                    .setToDate(elements[6].trim())
                                    .setReceipt(Double.parseDouble(elements[7].trim()))
                                    .setUserLogin(elements[10].trim());
                            return orderBuilder.build();
                        }
                ).findFirst().orElseThrow(() -> new CommandException("CANT PARSE THE ORDER"));
    }
}
