package com.myproject.command;

import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.User;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.UserService;
import com.myproject.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class FindAllUsersCommand implements Command {
    private final UserService userService = new AbstractFactoryImpl().getFactory().getServiceFactory().getUserService();
    private static final Logger logger = LogManager.getLogger(FindAllUsersCommand.class);

    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Route route = new Route();
        List<User>users = null;
        try {
            Optional<List<User>> allUsers = userService.getAllUsers();
            if (allUsers.isPresent()){
                users = allUsers.get();
            }
            assert users != null;
            users.forEach(System.out::println);
            request.getSession().setAttribute(GeneralConstant.Util.ALL_CARS,null);
            request.setAttribute(GeneralConstant.Util.ALL_USERS,users);
            route.setPathOfThePage(ConstantPage.WEB_INF_FULL_PATH_TO_ADMIN);
            route.setRoute(Route.RouteType.FORWARD);
        } catch (ServiceException e) {
            logger.warn("PROBLEM IN FindAllUsersCommand class");
            throw new CommandException("CANT FIND ALL USERS", e);
        }
        return route;
    }
}
