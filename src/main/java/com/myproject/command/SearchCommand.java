package com.myproject.command;

import com.myproject.command.facade.SearchCommandFacade;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.dao.entity.Car;
import com.myproject.dao.entity.User;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.CarService;
import com.myproject.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The SearchCommand class implements Command interface.
 * Represents class to work with searching process
 */
public class SearchCommand implements Command {
    private final SearchCommandFacade searchCommandFacade;
    private static final Logger logger = LogManager.getLogger(SearchCommand.class);

    public SearchCommand(){
        searchCommandFacade = new SearchCommandFacade();
    }

    public SearchCommand(SearchCommandFacade searchCommandFacade){
        this.searchCommandFacade = searchCommandFacade;
    }
    /**
     * The method retrieves desired arguments
     * depending on search result execute different commands
     *
     * @param request  - gets request from the client
     * @param response - sends the response to the browser
     * @return return route where the result will be sent
     * @throws CommandException    in case some problem occurred in SearchCommand class
     * @throws ValidationException in case the input validation was failed
     */
    @Override
    public Route execute(HttpServletRequest request,
                         HttpServletResponse response) throws CommandException, ValidationException {
        Route route;
        String role = (String) request.getSession().getAttribute("role");
        String search = request.getParameter("search");

        try {
            if (role == null) {
                route = searchCommandFacade.searchCarForGuest(search.trim(),request);
                logger.info("found searched car for guest");
            } else {

                 if (role.equals("admin") && search.contains("@")) {
                     route = searchCommandFacade.searchUserForAdmin(search.trim(),request);
                    logger.info("found searched user for admin");
                } else {
                     route = searchCommandFacade.searchCarForAdminOrUser(search.trim(),request);
                     logger.info("found required search for user/admin depend on request");
                }
            }
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
        return route;
    }
}
