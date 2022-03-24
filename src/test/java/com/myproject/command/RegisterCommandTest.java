package com.myproject.command;

import com.myproject.command.util.Route;
import com.myproject.dao.connection.DBManager;
import com.myproject.dao.impl.UserDaoImpl;
import com.myproject.exception.CommandException;
import com.myproject.exception.ValidationException;
import com.myproject.service.impl.UserServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class RegisterCommandTest {
    private static HttpServletRequest request;
    private static HttpServletResponse response;

    @BeforeClass
    public static void init(){
        DBManager.getInstance().loadScript();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        when(request.getParameter("name")).thenReturn("John");
        when(request.getParameter("surname")).thenReturn("Peters");
        when(request.getParameter("phone")).thenReturn("+38090345613");
        when(request.getParameter("login")).thenReturn("john@gmail.com");
        when(request.getParameter("password")).thenReturn("555");
    }

    @Test
    public void execute() {
        DBManager dbManager = DBManager.getInstance();
        Command command = new RegisterCommand(new UserServiceImpl(new UserDaoImpl(dbManager)));

        try {
            Route route = command.execute(request, response);
            assertEquals("/login.jsp",route.getPathOfThePage());
        } catch (CommandException | ValidationException e) {
            e.printStackTrace();
        }

    }
}