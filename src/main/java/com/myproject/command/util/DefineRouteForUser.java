package com.myproject.command.util;

import java.util.logging.Logger;

public class DefineRouteForUser {
    public static final Logger logger = Logger.getLogger(DefineRouteForUser.class.getName());
    public static String getPagePathDependOnUserRole(String role){
        if (role.equals(GeneralConstant.ADMIN)){
            logger.info("something went wrong in DefineRouteForUser");
            return ConstantPage.ADMIN_HOME_PAGE;
        }else if (role.equals(GeneralConstant.USER)){
            logger.info("something went wrong in DefineRouteForUser");
            return ConstantPage.USER_HOME_PAGE;
        }else {
            logger.info("something went wrong in DefineRouteForUser");
            return ConstantPage.HOME_PAGE;
        }
    }
}
