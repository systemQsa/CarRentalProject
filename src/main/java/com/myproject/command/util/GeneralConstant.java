package com.myproject.command.util;

public final class GeneralConstant {
    public static String NOT_BLOCKED_STATUS = "N";
    public static String BLOCKED_STATUS = "Y";
    public static String WEB_INF = "WEB-INF";
    public static final String LANGUAGE_UKR = "uk";
    public static final String LANGUAGE_ENG = "en";
    public static final String COUNTRY_US = "US";
    public static final String COUNTRY_UA = "UA";
    public static final String EMPTY_STRING = "";
    public static final String ENCODING = "UTF-8";
    public static final String CONTENT_TYPE = "text/html";
    public static final String ACTION = "action";
    public static final String LANG = "lang";
    public static final String LANGUAGE = "language";
    public static final String ROLE = "role";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String LOGGED_USERS = "loggedUsers";
    public static final String USER_NAME = "userName";
    public static final String ADMIN = "admin";
    public static final String USER = "user";
    public static final String MANAGER = "manager";
    public static final String REDIRECT = "redirect:";
    public static final String REGISTER = "register";
    public static final String CHANGE_LOCALE_REGEX_URL_CAR = ".*/car";
    public static final String CHANGE_LOCALE_REGEX_WEB_INF = ".*/car/WEB-INF";
    public static final String CAR = "/car";
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String NO_STORE_MUST_REVALIDATE = "no-store,must-revalidate";
    public static final String PRAGMA = "Pragma";
    public static final String NO_CACHE = "no-cache";
    public static final String EXPIRES = "Expires";
    public static final String RESOURCES = "resources";

    public static final class Util{
        public static final String LOGGED_USERS = "loggedUsers";
        public static final String USER_BALANCE = "userBalance";
        public static final String USER_ID_BY_LOGIN = "userIdByLogin";
        public static final String USER_LOGIN = "userLogin";
        public static final String ALL_USERS = "allUsers";
        public static final String ALL_CARS = "allCars";
        public static final String CURRENT_PAGE = "currentPage";
        public static final String VIEW_USER_PERSONAL_ORDERS = "viewMyOrders";
        public static final String VIEW_ORDERS_APPROVED_NOT_APPROVED_FOR_MANAGER = "viewOrders";
        public static final String APPROVED = "approved";
        public static final String DECLINED = "declined";
        public static final String LIST_PERSONAL_ORDERS_FOR_USER = "myPersonalOrders";
        public static final String LIST_REQUIRED_ORDERS_FOR_MANAGER = "";
        public static final String AMOUNT_OF_RECORDS = "amountOfRecords";
        public static final String VIEW_CARS_PAGINATION = "viewCars";
        public static final String VIEW_SUCH_ORDERS = "";
    }

    public static final class ErrorMSG{
         public static final String INVALID_PASS = "Invalid password";
         public static final String BLOCKED_USER = "By some reason you were blocked. Please contact our manager for more details!";
         public static final String INVALID_NAME_SURNAME = "Invalid name/surname";
         public static final String INVALID_USERNAME_LOGIN = "Invalid userName/login";
         public static final String INVALID_PHONE = "Invalid phone";
         public static final String NOT_UNIQUE_LOGIN = "I can`t register you! Your login has to be unique.Try again!";

    }
}
