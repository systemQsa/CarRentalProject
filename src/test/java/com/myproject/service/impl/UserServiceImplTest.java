package com.myproject.service.impl;

import com.myproject.dao.UserDao;
import com.myproject.dao.connection.DBManager;
import com.myproject.dao.entity.User;
import com.myproject.dao.entity.UserRole;
import com.myproject.dao.impl.UserDaoImpl;
import com.myproject.exception.ServiceException;
import com.myproject.service.UserService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class UserServiceImplTest {
    private DBManager dbManager;

    @BeforeClass
    public static void beforeTesting(){
        DBManager.getInstance().loadScript();
    }

    @Test
    public void getUser() {
        dbManager = DBManager.getInstance();
        UserDao<User> userDao = new UserDaoImpl(dbManager);
        UserService userService = new UserServiceImpl(userDao);

        try {
            Optional<User> user = userService.getUser("user1@gmail.com");
            user.ifPresent(value -> assertEquals("romanov", value.getSurname()));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUserByLoginAndPass(){
        dbManager = DBManager.getInstance();
        UserDao<User> userDao = new UserDaoImpl(dbManager);
        UserService userService = new UserServiceImpl(userDao);

        try {
            User returnedUser = userService.getUserByLoginAndPass("user1@gmail.com", new char[]{'2','2','2'});
            assertEquals("user",returnedUser.getName());
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getBalance(){
        dbManager = DBManager.getInstance();
        UserDao<User> userDao = new UserDaoImpl(dbManager);
        UserService userService = new UserServiceImpl(userDao);
        try {
            double balance = userService.getBalance("user1@gmail.com");
            assertEquals("50.0",String.valueOf(balance));
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getAllUsers(){
        Optional<List<User>> listOptional;
        dbManager = DBManager.getInstance();
        UserDao<User> userDao = new UserDaoImpl(dbManager);
        UserService userService = new UserServiceImpl(userDao);

        try {
            listOptional = userService.getAllUsers();
            listOptional.ifPresent(list->assertEquals(3,list.size()));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void registerNewUser(){
        dbManager = DBManager.getInstance();
        UserDao<User> userDao = new UserDaoImpl(dbManager);
        UserService userService = new UserServiceImpl(userDao);

        try {
            User user = userService.registerNewUser("peter","parker","peter@gmail.com",new char[]{'1','2','3'},"+1234567890");
            assertEquals("peter@gmail.com",user.getLogin());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateUserBalance(){
        dbManager = DBManager.getInstance();
        UserDao<User> userDao = new UserDaoImpl(dbManager);
        UserService userService = new UserServiceImpl(userDao);

        try {
            assertTrue(userService.updateUserBalance(10.0, "user3@gmail.com"));
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void checkUserStatus(){
        dbManager = DBManager.getInstance();
        UserDao<User> userDao = new UserDaoImpl(dbManager);
        UserService userService = new UserServiceImpl(userDao);

        try {
            String status = userService.checkUserStatus("user1@gmail.com");
            assertEquals("N",status);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void unblockUser(){
        dbManager = DBManager.getInstance();
        UserDao<User> userDao = new UserDaoImpl(dbManager);
        UserService userService = new UserServiceImpl(userDao);

        try {
            boolean result = userService.unblockUser("user1@gmail.com");
            assertTrue(result);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void blockUser(){
        dbManager = DBManager.getInstance();
        UserDao<User> userDao = new UserDaoImpl(dbManager);
        UserService userService = new UserServiceImpl(userDao);

        try {
           assertTrue(userService.blockUser("user1@gmail.com"));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateUserStatus(){
        dbManager = DBManager.getInstance();
        UserDao<User> userDao = new UserDaoImpl(dbManager);
        UserService userService = new UserServiceImpl(userDao);

        try {
            assertTrue(userService.updateUserStatus("user2@gmail.com", UserRole.USER));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }


}