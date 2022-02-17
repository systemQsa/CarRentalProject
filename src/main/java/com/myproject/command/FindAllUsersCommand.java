package com.myproject.command;

import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.User;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.service.UserService;
import com.myproject.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class FindAllUsersCommand implements Command {
    private final UserService userService = new UserServiceImpl();

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
            request.getSession().setAttribute("allCars",null);
            request.setAttribute("allUsers",users);
            route.setPathOfThePage(ConstantPage.WEB_INF_FULL_PATH_TO_ADMIN);
            route.setRoute(Route.RouteType.FORWARD);
        } catch (ServiceException e) {
            throw new CommandException("CANT FIND ALL USERS", e);
        }
        return route;
    }
}