package com.myproject.command;

import com.myproject.command.util.Route;
import com.myproject.dao.entity.OrderViewForUserRequest;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.service.OrderViewService;
import com.myproject.service.impl.ViewOrderServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class PaginationCommand implements Command{
    private final OrderViewService orderViewService = new ViewOrderServiceImpl();
    private static final Logger logger = LogManager.getLogger(PaginationCommand.class);
    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        String pageId = request.getParameter("page");
        String userRole = (String) request.getSession().getAttribute("role");
        String requiredResult = request.getParameter("required");
        String userLogin =(String) request.getSession().getAttribute("userName");

        System.out.println("\nGET PAGINATION  # " + request.getParameter("page") + "\n" + "Action " + request.getParameter("action")
         + " Role " + request.getSession().getAttribute("role") + " " + requiredResult);

        Optional<List<OrderViewForUserRequest>> requiredList;
        List<OrderViewForUserRequest> listOfRequiredItems;
        if (userRole.equals("user")){
            if (requiredResult.equals("viewMyOrders")){
                try {
                   requiredList =  orderViewService.getAllUserPersonalOrders(userLogin,Integer.parseInt(pageId));
                   if (requiredList.isPresent()){
                       listOfRequiredItems = requiredList.get();
                       request.getSession().getServletContext().setAttribute("myPersonalOrders",listOfRequiredItems);
                   }
                } catch (ServiceException e) {
                    logger.warn("Cant get all personal user orders in PaginationCommand");
                    throw new CommandException(e.getMessage());
                }
            }
        }

        return null;
    }
}
