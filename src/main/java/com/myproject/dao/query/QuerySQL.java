package com.myproject.dao.query;

/**
 * The QuerySQL class represents class of query constants for requests to DataBase
 */
public final class QuerySQL {
    public static final String GET_USER_ID_ACCORDING_TO_INPUT = "SELECT role_id,password FROM users WHERE login=?";
    public static final String ADD_NEW_USER = "INSERT INTO users(name,surname,login,password,phone) VALUES(?,?,?,?,?)";
    public static final String UPDATE_USER_ROLE_TO_ADMIN_ROLE = "UPDATE users SET role_id=1 WHERE id_user=?";
    public static final String GET_ALL_USERS = "SELECT id_user,name,surname,login,phone,banned,register_date,role_id FROM users ORDER BY id_user DESC";
    public static final String GET_ALL_CARS = "SELECT id_car,name,carClass,brand,rent_price FROM cars ORDER BY id_car DESC LIMIT ?,?";
    public static final String GET_CARS_TOTAL_RECORDS  = "SELECT COUNT(id_car) as records FROM cars";
    public static final String GET_ALL_CARS_SORT_BY_NAME = "SELECT id_car,name,carClass,brand,rent_price FROM cars ORDER BY name LIMIT ?,?";
    public static final String GET_ALL_CARS_SORT_BY_CAR_CLASS = "SELECT id_car,name,carClass,brand,rent_price FROM cars ORDER BY carClass LIMIT ?,?";
    public static final String GET_ALL_CARS_SORT_BY_RENT_PRICE = "SELECT id_car,name,carClass,brand,rent_price FROM cars ORDER BY rent_price LIMIT ?,?";
    public static final String GET_ALL_CARS_SORT_BY_BRAND = "SELECT id_car,name,carClass,brand,rent_price FROM cars ORDER BY brand LIMIT ?,?";
    public static final String DELETE_CAR = "DELETE FROM cars WHERE id_car=?";
    public static final String GET_USER_BY_LOGIN = "SELECT id_user,name,login,password,banned,role_id FROM users WHERE login=?";
    public static final String ADD_CAR = "INSERT INTO cars(name,carClass,brand,rent_price,photo) VALUES (?,?,?,?,?)";
    public static final String FIND_CAR_BY_NAME = "SELECT id_car FROM cars WHERE name=?";
    public static final String UPDATE_CAR = "UPDATE cars SET name=?,carClass=?,brand=?,rent_price=?,photo=? WHERE id_car=?";
    public static final String GET_ONE_CAR_BY_ID = "SELECT id_car,name,carClass,brand,rent_price,photo FROM cars WHERE id_car=?";
    public static final String TOP_UP_USER_BALANCE = "UPDATE users SET balance=? WHERE login=?";
    public static final String SEE_USER_BALANCE = "SELECT balance FROM users WHERE login=?";
    public static final String BAN_AND_UNBAN_USER = "UPDATE users SET banned=? WHERE login=?";
    public static final String CHECK_USER_STATUS = "SELECT banned FROM users WHERE login=?";
    public static final String SET_USER_ROLE = "UPDATE users SET role_id=? WHERE login=?";
    public static final String GET_DRIVER_RENT_PRICE = "SELECT price FROM driver_price WHERE id=?";
    public static final String INSERT_NEW_ORDER = "INSERT INTO orders (passport,from_date,to_date,with_driver,receipt,user_id) VALUES (?,?,?,?,?,?)";
    public static final String SET_ORDERS_FOR_USER = "INSERT INTO orders_cars (order_id,car_id) VALUES (?,?)";
    public static final String GET_USER_BALANCE = "SELECT balance FROM users WHERE id_user=?";
    public static final String SET_APPROVED_ORDER_BY_MANAGER = "UPDATE orders_cars SET feedback=?,manager_login=?,approved=? WHERE order_id=?";
    public static final String VIEW_ALL_APPROVED_OR_NOT_APPROVED_ORDERS_BY_MANAGER = "login,o.passport,receipt," +
                                                  "from_date,to_date,with_driver,c.name,c.carClass,c.brand FROM users\n" +
                                                   "JOIN orders o ON users.id_user = o.user_id\n" +
                                                   "JOIN orders_cars oc ON o.id_order = oc.order_id\n" +
                                                   "JOIN cars c ON oc.car_id = c.id_car WHERE approved=? order by order_id desc";
    public static final String ALL_ORDERS_USER_VIEW =" passport,from_date,to_date,with_driver,receipt," +
            "name,carClass,brand,feedback,approved FROM orders\n" +
            "JOIN orders_cars oc ON orders.id_order = oc.order_id\n" +
            "JOIN cars c ON c.id_car = oc.car_id WHERE user_id=(SELECT id_user FROM users WHERE login=?) order by order_id desc ";
    public static final String SEARCH_USER_BY_LOGIN = "SELECT id_user,name,surname,login,register_date,banned,phone,role_id FROM users WHERE login=?";
    public static final String SEARCH_CAR_BY_NAME = "SELECT id_car,name,carClass,brand,rent_price FROM cars WHERE name=?";
    public static final String UPDATE_PASS = "UPDATE users SET password=? WHERE login=?";
    public static final String CHECK_IF_CAR_ALREADY_PRESENT_IN_DB = "SELECT name,carClass,brand,rent_price FROM cars WHERE name=? AND carClass=? AND brand=? AND rent_price=?";
    public static final String CHECK_IF_ORDER_ALREADY_PRESENT_IN_DB_BY_USER =   "SELECT count(user_id) as record " +
                                                                                "FROM orders JOIN orders_cars ON id_order=order_id " +
                                                                                 "WHERE passport=? " +
                                                                                 "AND from_date=? " +
                                                                                 "AND to_date=? " +
                                                                                 "AND with_driver=? " +
                                                                                 "AND receipt=? " +
                                                                                 "AND user_id=? " +
                                                                                 "AND car_id=?;";

    public static final String UPDATE_DRIVER_PRICE = "UPDATE driver_price SET price=? WHERE id=1";
    public static final String GET_DRIVER_PRICE = "SELECT price FROM driver_price WHERE id=1";
}

