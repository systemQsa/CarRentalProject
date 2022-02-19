package com.myproject.command.util;

import com.myproject.command.userCommand.TopUpBalanceCommand;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DefineRouteForUser {
    private static final Logger logger = LogManager.getLogger(TopUpBalanceCommand.class);
    public static String getPagePathDependOnUserRole(String role){
        if (role.equals(GeneralConstant.ADMIN)){
            logger.warn("in DefineRouteForUser class");
            return ConstantPage.ADMIN_HOME_PAGE;
        }else if (role.equals(GeneralConstant.USER)){
            logger.warn("in DefineRouteForUser class");
            return ConstantPage.USER_HOME_PAGE;
        }else {
            logger.warn("something went wrong in DefineRouteForUser");
            return ConstantPage.HOME_PAGE;
        }
    }
}
