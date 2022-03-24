package com.myproject.command.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class DefineRouteForUserTest {

    @Test
    public void getPagePathDependOnUserRoleAdmin() {
        String page = DefineRouteForUser.getPagePathDependOnUserRole("admin");
        assertEquals(ConstantPage.ADMIN_HOME_PAGE,page);
    }


    @Test
    public void getPagePathDependOnUserRoleUser() {
        String page = DefineRouteForUser.getPagePathDependOnUserRole("user");
        assertEquals(ConstantPage.USER_HOME_PAGE,page);
    }

    @Test
    public void getPagePathDependOnUserRoleManager() {
        String page = DefineRouteForUser.getPagePathDependOnUserRole("manager");
        assertEquals(ConstantPage.MANAGER_HOME_PAGE,page);
    }
}