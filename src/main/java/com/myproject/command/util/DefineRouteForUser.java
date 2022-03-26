package com.myproject.command.util;

import com.myproject.command.userCommand.TopUpBalanceCommand;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * The class defines the page path where the request will be sent
 */
public class DefineRouteForUser {
    private static final Logger logger = LogManager.getLogger(TopUpBalanceCommand.class);

    public static String getPagePathDependOnUserRole(String role) {
        switch (role) {
            case GeneralConstant.ADMIN:
                logger.warn("cannot get proper page for admin in DefineRouteForUser class");
                return ConstantPage.ADMIN_HOME_PAGE;
            case GeneralConstant.USER:
                logger.warn("cannot get proper page for user in DefineRouteForUser class");
                return ConstantPage.USER_HOME_PAGE;
            case GeneralConstant.MANAGER:
                logger.warn("cannot get proper page for manager in DefineRouteForUser class");
                return ConstantPage.MANAGER_HOME_PAGE;
            default:
                logger.warn("something went wrong in DefineRouteForUser");
                return ConstantPage.HOME_PAGE;
        }

    }
}
