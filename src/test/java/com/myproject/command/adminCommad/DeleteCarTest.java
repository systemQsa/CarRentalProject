package com.myproject.command.adminCommad;

import com.myproject.command.Command;
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

public class DeleteCarTest {
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    @BeforeClass
    public static void init(){
        DBManager.getInstance().loadScript();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        when(request.getParameter("carId")).thenReturn("3");
    }

    @Test
    public void execute() {
        DBManager dbManager = DBManager.getInstance();
        Command command = new DeleteCar(new CarServiceImpl(new CarDaoImpl(dbManager)));

        try {
            assertEquals("redirect:/view/admin/admin.jsp",command.execute(request, response).getPathOfThePage());
        } catch (CommandException | ValidationException e) {
            e.printStackTrace();
        }

    }
}