package com.myproject.command.util;

public final  class ConstantPage {
    public static final String ROOT = "/car/";
    public static final String HOME_PAGE = "/index.jsp";
    public static  final String LOG_IN_PAGE="/login.jsp";
    public static final String REGISTER_PAGE = "/register.jsp";
    public static final String ADMIN_HOME_PAGE = "redirect:/view/admin/admin.jsp";
    public static final String USER_HOME_PAGE = "redirect:/view/user/user.jsp";
    public static final String ADMIN_PAGE = "/view/admin/admin.jsp";
    public static final String WEB_INF_FULL_PATH_TO_ADMIN = "/WEB-INF/view/admin/admin.jsp";
    public static final String WEB_INF_FULL_PATH_TO_USER="/WEB-INF/view/user/user.jsp";
    public static final String ADD_CAR_PAGE = "/WEB-INF/view/admin/addNewCar.jsp";
    public static final String UPDATE_CAR_PAGE_REDIRECT = "redirect:/view/admin/updateCar.jsp";
    public static final String UPDATE_CAR_PAGE = "/WEB-INF/view/admin/updateCar.jsp";
}