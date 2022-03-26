package com.myproject.validation;

import com.myproject.dao.connection.DBManager;
import com.myproject.dao.impl.UserDaoImpl;
import com.myproject.exception.DaoException;
import com.myproject.exception.ValidationException;
import com.myproject.service.UserService;
import com.myproject.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class ValidateInputTest {
    private static final Logger logger = LogManager.getLogger(ValidateInputTest.class);
    private final Validate validateInput = new ValidateInput();
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String URL_CONNECTION = "jdbc:h2:~/test;user=sa;password=";
    private static final String USER = "sa";
    private static final String PASS = "";
    private static DBManager dbManager;


    @BeforeClass
    public static void beforeTesting() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (OutputStream output = new FileOutputStream("app.properties")) {
            Properties prop = new Properties();
            prop.setProperty("connection.url", URL_CONNECTION);
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }

        dbManager = DBManager.getInstance();

        try (Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = con.createStatement()) {
            String dropRole = "DROP TABLE IF EXISTS roles;";

            statement.executeUpdate(dropRole);

            String createTableRoles = "CREATE TABLE roles\n" +
                    "(\n" +
                    "    id_role INT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "    role    VARCHAR(25)\n" +
                    ");";
            statement.executeUpdate(createTableRoles);

            String insertRoles = "INSERT INTO roles (role) VALUES ('admin');";
            String insertUserRole = "INSERT INTO roles(role) VALUES ('user')";
            statement.executeUpdate(insertRoles);
            statement.executeUpdate(insertUserRole);
            String dropUsers = "DROP TABLE IF EXISTS users;";
            statement.executeUpdate(dropUsers);

            String createTableUsers = "CREATE TABLE users\n" +
                    "(\n" +
                    "    id_user       INT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "    name          VARCHAR(100) NOT NULL,\n" +
                    "    surname       VARCHAR(100) NOT NULL,\n" +
                    "    passport      VARCHAR(50),\n" +
                    "    login         VARCHAR(255) UNIQUE,\n" +
                    "    password      TEXT         NOT NULL,\n" +
                    "    balance       DECIMAL(10, 2) DEFAULT 0,\n" +
                    "    register_date DATETIME       DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
                    "    banned        CHAR(1)        DEFAULT 'N',\n" +
                    "    phone         VARCHAR(100),\n" +
                    "    role_id       INT            DEFAULT 2,\n" +
                    "    FOREIGN KEY (role_id) REFERENCES roles (id_role)\n" +
                    ");";
            statement.executeUpdate(createTableUsers);
            String insertUser = "INSERT INTO users(name, surname, login, password, phone) VALUES ('user', 'romanov', 'user2@gmail.com', '222', '+380636789120');";
            statement.executeUpdate(insertUser);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateLogin() {
        List<String> logins = Arrays.asList("user@gmail.com", "Alex@gmail.com", "користувач@gmail.com",
                "user123@gmail.com", "ABC@gmail.com");

        logins.forEach(login ->
                {
                    try {
                        assertThat(validateInput.loginValidate(login)).isTrue();
                        logger.info("validation login test passed");
                    } catch (ValidationException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    @Test
    public void validateBadLogins() {
        List<String> badLogins = Arrays.asList("user#gmail.com", "usergmail", "peter$gmail.com", "користувач1@gmail");

        for (String badLogin : badLogins) {
            assertThrows(ValidationException.class, () -> validateInput.loginValidate(badLogin));
            logger.info("validation bad logins in tests passed");
        }

    }

    @Test
    public void validatePassword() {
        List<char[]> passwords = new ArrayList<>();
        passwords.add(new char[]{'1', 'a', '3'});
        passwords.add(new char[]{'1', '5', '6', '7', '7', '8'});
        passwords.add(new char[]{'4', '5', 'Q', 'A', '7', 'y', 'U', '8', '0', '5', '5', '5', '5'});


        passwords.forEach(pass -> {
            try {
                assertThat(validateInput.passwordValidate(pass)).isTrue();
                logger.info("validation password in tests has passed");
            } catch (ValidationException e) {
                e.printStackTrace();
            }
        });

    }

    @Test
    public void validatePasswordNegative() {
        try {
            assertTrue(validateInput.passwordValidate(new char[]{'@', 'a', '3'}));
            logger.info("validation negative password in tests has passed");
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateInputNameSurname() {
        Map<String, String> map = new HashMap<>();
        map.put("Alex", "Parker");
        map.put("Peter", "Ford");
        map.put("Вася", "Петров");
        map.put("Alice", "Jasper");

        assertThat(map).allSatisfy((name, surname) -> {
            try {
                assertThat(validateInput.nameSurnameValidate(name, surname)).isIn(true);
                logger.info("validation name and surname in tests has passed");
            } catch (ValidationException e) {
                e.printStackTrace();
            }
        });

    }

    @Test
    public void validateBadInputNameSurname() {
        Map<String, String> badInputs = new HashMap<>();
        badInputs.put("123SD", "DFR#");
        badInputs.put("alice", "crazy12");
        badInputs.put("SDСф", "(*)");

        for (Map.Entry<String, String> entry : badInputs.entrySet()) {
            assertThrows(ValidationException.class, () -> validateInput.nameSurnameValidate(entry.getKey(), entry.getValue()));
            logger.info("validation bad name and surname in tests has passed");
        }

    }

    @Test
    public void validatePhoneNumber() {
        List<String> phones = Arrays.asList("+380983456123","+1234568","+4567892345","+390894567234","+12345678902345");

        assertThat(phones).allSatisfy(phone->{
            assertThat(validateInput.phoneValidate(phone)).isTrue();
        });
        logger.info("validation phone number in tests has passed");
    }

    @Test
    public void validatePhoneNumberNegative() {
        List<String> badPhones = Arrays.asList("45678","+123456", "+123456789012345","12","1","@","^&*)");

        for (String badPhone:badPhones){
            assertThrows(ValidationException.class,()->validateInput.phoneValidate(badPhone));
         }
        logger.info("validation bad phone numbers in tests has passed");
     }

    @Test
    public void validatePassport() {
        List<String> passports = Arrays.asList("AB345621","AV12","BR234","NN12345","ML12345678");
        ValidateInput validateInput = new ValidateInput();
        assertThat(passports).allSatisfy(passport->{
            assertThat(validateInput.passportValidate(passport)).isTrue();
        });
        logger.info("validation passport in tests has passed");

    }

    @Test
    public void validateUserIsBlocked() {
        dbManager = DBManager.getInstance();
        UserDaoImpl userDao = new UserDaoImpl(dbManager);
        UserService userService = new UserServiceImpl(userDao);
        ValidateInput validateInput = new ValidateInput(userService);
        try {
            userDao.blockUnblockUser("user2@gmail.com", "Y");
            assertTrue(validateInput.userIsBlockedValidate("user2@gmail.com"));
            logger.info("validation user is blocked in tests has passed");
        } catch (DaoException | ValidationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void validateDatesAndTime() {
        LocalDateTime from = LocalDateTime.of(2022, Month.AUGUST,10,17,00,00);
        LocalDateTime to = LocalDateTime.of(2022,Month.AUGUST,10,18,00,00);
        ValidateInput validateInput = new ValidateInput();
        try {
            assertTrue(validateInput.datesAndTimeValidate(from,to,null));
            logger.info("validation dates and time in tests has passed");
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
}
