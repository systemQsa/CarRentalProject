package com.myproject.command.adminCommad;

import com.myproject.command.Command;
import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.Route;
import com.myproject.exception.CommandException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.DriverService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The UpdateDriverPriceCommand class implements the Command interface.
 * Presents class that updates driver rental price and returns to the admin page
 */
public class UpdateDriverPriceCommand implements Command {
    private final DriverService driverService;
    private static final Logger logger = LogManager.getLogger(UpdateDriverPriceCommand.class);

    public UpdateDriverPriceCommand() {
        driverService = new AbstractFactoryImpl().getFactory().getServiceFactory().getDriverService();
    }

    public UpdateDriverPriceCommand(DriverService driverService) {
        this.driverService = driverService;
    }

    @Override
    public Route execute(HttpServletRequest request,
                         HttpServletResponse response) throws CommandException, ValidationException {
        Route route = new Route();
        String newDriverPrice = request.getParameter("newDriverPrice");
        double newPrice = 0;

        if (newDriverPrice != null) {
            newPrice = Double.parseDouble(newDriverPrice);
        }
        try {
            driverService.changeDriverPrice(newPrice);
        } catch (ServiceException e) {
            setInformMessageIfErrorOccur("err.update_driver_price",22,request);
            logger.warn("Problem in UpdateDriverPriceCommand class cant update rental price for drivers");
            throw new CommandException(ConstantPage.WEB_INF_FULL_PATH_TO_ADMIN);
        }

        try {
            request.getSession().setAttribute("driverRentalPrice",driverService.getDriverRentalPrice());
        } catch (ServiceException e) {
            setInformMessageIfErrorOccur("err.get_driver_price",25,request);
            logger.warn("Problem occur in UpdateDriverPriceCommand class can`t get rental driver price");
            throw new CommandException(ConstantPage.WEB_INF_FULL_PATH_TO_ADMIN);
        }

        route.setPathOfThePage(ConstantPage.ADMIN_HOME_PAGE);
        route.setRoute(Route.RouteType.REDIRECT);
        return route;
    }
}
