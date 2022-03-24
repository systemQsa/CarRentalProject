package com.myproject.command.userCommand;

import com.myproject.command.Command;
import com.myproject.command.util.GeneralConstant;
import com.myproject.command.util.Route;
import com.myproject.dao.connection.DBManager;
import com.myproject.dao.impl.UserDaoImpl;
import com.myproject.exception.CommandException;
import com.myproject.exception.ValidationException;
import com.myproject.service.impl.UserServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class TopUpBalanceCommandTest {
    private static HttpServletRequest request;
    private static HttpServletResponse response;

    @BeforeClass
    public static void init(){
        DBManager.getInstance().loadScript();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        ServletContext context = mock(ServletContext.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getParameter("userLogin")).thenReturn("user1@gmail.com");
        when(request.getParameter("topUpBalance")).thenReturn("10.0");
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getServletContext()).thenReturn(context);
        when(request.getSession().getServletContext().getAttribute(GeneralConstant.USER_NAME)).thenReturn("user1@gmail.com");
    }

    @Test
    public void execute() {
        DBManager dbManager = DBManager.getInstance();
        Command topUpBalance = new TopUpBalanceCommand(new UserServiceImpl(new UserDaoImpl(dbManager)));

        try {
            Route route = topUpBalance.execute(request, response);
            assertEquals("redirect:/view/user/user.jsp",route.getPathOfThePage());

        } catch (CommandException | ValidationException e) {
            e.printStackTrace();
        }
    }
}