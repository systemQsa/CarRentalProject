package com.myproject.command;

import com.myproject.dao.entity.Order;
import com.myproject.exception.CommandException;
import org.junit.Test;
import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CommandTest {
    private  static HttpServletRequest request;
    private static Command command;


    public static void init(){
        request = mock(HttpServletRequest.class);
        command = mock(ActivateNewPass.class);
        when(request.getParameter("errMSG")).thenReturn("message");
     }


    @Test
    public void parseIncomeOrder() {
        Command command = new ActivateNewPass();
        String newOrder = " Order{orderId=0, userId=2, carId=16, passport=AB2345, withDriver=N, fromDate=2022-03-22 08:00:00, toDate=2022-03-22 09:00:00, receipt=12.0, dateFrom=null, dateTo=null, userLogin=user@gmail.com}";
        try {
            Order order = command.parseIncomeOrder(newOrder);
            assertEquals("user@gmail.com",order.getUserLogin());
        } catch (CommandException e) {
            e.printStackTrace();
        }


    }


    public void setInformMessageIfErrorOccur() {
       //HttpServletRequest answer = command.setInformMessageIfErrorOccur("message",1,request);
       verify(command).setInformMessageIfErrorOccur("mess",2,request);
    }
}