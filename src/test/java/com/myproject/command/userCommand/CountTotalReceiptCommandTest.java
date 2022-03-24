package com.myproject.command.userCommand;

import com.myproject.command.util.Route;
import com.myproject.dao.OrderDao;
import com.myproject.dao.connection.DBManager;
import com.myproject.dao.impl.OrderDaoImpl;
import com.myproject.exception.CommandException;
import com.myproject.exception.ValidationException;
import com.myproject.service.CarOrderService;
import com.myproject.service.impl.CarOrderServiceImpl;
 import org.junit.BeforeClass;
import org.junit.Test;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class CountTotalReceiptCommandTest {
    private DBManager dbManager;
    private static HttpServletRequest request;
    private static HttpServletResponse response;

    @BeforeClass
    public static void beforeTest() {
        DBManager.getInstance().loadScript();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        ServletContext context = mock(ServletContext.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getParameter("carRentPrice")).thenReturn("10.0");
        when(request.getParameter("userPassport")).thenReturn("AV4567");
        when(request.getParameter("fromDate")).thenReturn("2022-05-22 13:00:00");
        when(request.getParameter("toDate")).thenReturn("2022-05-22 14:00:00");
        when(request.getParameter("flexRadioDefault")).thenReturn("N");
        when(request.getParameter("userBalance")).thenReturn("50");
        when(request.getParameter("userLogin")).thenReturn("user1@gmail.com");
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getServletContext()).thenReturn(context);

    }

    @Test
    public void execute() {
        dbManager = DBManager.getInstance();
        OrderDao orderDao = new OrderDaoImpl(dbManager);
        CarOrderService carOrderService = new CarOrderServiceImpl(orderDao);
        CountTotalReceiptCommand countTotalReceiptCommand = new CountTotalReceiptCommand(carOrderService);

        try {
            Route route = countTotalReceiptCommand.execute(request,response);
            assertEquals("redirect:/view/user/confirmReceipt.jsp",route.getPathOfThePage());
        } catch (CommandException | ValidationException e) {
            e.printStackTrace();
        }


    }
}