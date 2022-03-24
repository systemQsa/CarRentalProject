package com.myproject.command.userCommand;

import com.myproject.command.Command;
import com.myproject.command.util.Route;
import com.myproject.exception.CommandException;
import com.myproject.exception.ValidationException;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ConfirmBookingCommandTest {

    private static HttpServletRequest request;
    private static HttpServletResponse response;

    @BeforeClass
    public static void init(){
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        when(request.getParameter("userPassport")).thenReturn("AV4567");
        when(request.getParameter("fromDate")).thenReturn("2022-05-22 13:00:00");
        when(request.getParameter("toDate")).thenReturn("2022-05-22 14:00:00");
        when(request.getParameter("withDriver")).thenReturn("N");
        when(request.getParameter("totalPrice")).thenReturn("10.0");
        when(request.getParameter("userIdByLogin")).thenReturn("3");
        when(request.getParameter("userLogin")).thenReturn("user3@gmail.com");
        when(request.getParameter("carId")).thenReturn("1");
    }

    @Test
    public void execute() {
        Command command = new ConfirmBookingCommand();

        try {
            Route route = command.execute(request, response);
            assertEquals(Route.RouteType.REDIRECT,route.getRoute());
        } catch (CommandException | ValidationException e) {
            e.printStackTrace();
        }
    }
}