package com.myproject.command.adminCommad;

import com.myproject.command.Command;
import com.myproject.command.util.Route;
import com.myproject.dao.connection.DBManager;
import com.myproject.dao.impl.CarDaoImpl;
import com.myproject.exception.CommandException;
import com.myproject.exception.ValidationException;
import com.myproject.service.impl.CarServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class AddCarCommandTest {
    private static HttpServletRequest request;
    private  static HttpServletResponse response;

    @BeforeClass
    public static void init(){
        DBManager.getInstance().loadScript();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        when(request.getParameter("name")).thenReturn("audi");
        when(request.getParameter("carClass")).thenReturn("A");
        when(request.getParameter("brand")).thenReturn("audi");
        when(request.getParameter("rentalPrice")).thenReturn("25.0");
        when(request.getParameter("photo")).thenReturn(null);
    }

    @Test
    public void execute() {
        DBManager dbManager = DBManager.getInstance();
        Command command = new AddCarCommand(new CarServiceImpl(new CarDaoImpl(dbManager)));

        try {
            Route route = command.execute(request, response);
            assertEquals("redirect:/view/admin/admin.jsp",route.getPathOfThePage());
        } catch (CommandException | ValidationException e) {
            e.printStackTrace();
        }

    }
}