package com.myproject.dao.impl;

import com.myproject.dao.UserDao;
import com.myproject.dao.connection.DBManager;
import com.myproject.dao.entity.User;
import com.myproject.dao.entity.UserRole;
import com.myproject.exception.DaoException;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;



public class UserDaoImplTest {
    private static DBManager dbManager;

    @BeforeClass
    public static void beforeTesting() {
        DBManager.getInstance().loadScript();
    }


    @Test
    public void findByLogin() {
        dbManager = DBManager.getInstance();
        UserDaoImpl userDao = new UserDaoImpl(dbManager);
        try {
            User user = userDao.findByLogin("user1@gmail.com");
            System.out.println(user);
            assertEquals("user", user.getName());
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void findAll() {
        dbManager = DBManager.getInstance();
        UserDao<User> userDao = new UserDaoImpl(dbManager);

        try {
            List<User> list = userDao.findAll();
            assertEquals(3, list.size());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addRecordToTable() {
        User.UserBuilder newUser = new User.UserBuilder();
        newUser.setFirstName("user4")
                .setLastName("petrov")
                .setLogin("user4@gmail.com")
                .setHashPass("123")
                .setPhone("+380905467123");
        dbManager = DBManager.getInstance();
        UserDaoImpl userDao = new UserDaoImpl(dbManager);
        try {
            User user = userDao.addRecordToTable(newUser.build());
            System.out.println(user);
            assertEquals("user4@gmail.com", user.getLogin());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getBalance() {
        dbManager = DBManager.getInstance();
        UserDaoImpl userDao = new UserDaoImpl(dbManager);
        try {
            userDao.topUpBalance(50.0, "user1@gmail.com");
            double balance = userDao.getBalance("user1@gmail.com");
            assertEquals(100.0, balance,0.00001);

        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void topUpBalance() {
        dbManager = DBManager.getInstance();
        UserDaoImpl userDao = new UserDaoImpl(dbManager);
        try {
            assertTrue(userDao.topUpBalance(50.0, "user2@gmail.com"));
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setUserRole() {
        dbManager = DBManager.getInstance();
        UserDaoImpl userDao = new UserDaoImpl(dbManager);

        try {
            assertTrue(userDao.setUserRole("user1@gmail.com", UserRole.ADMIN));
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void blockUnblockUser() {
        dbManager = DBManager.getInstance();
        UserDaoImpl userDao = new UserDaoImpl(dbManager);
        try {
            assertTrue(userDao.blockUnblockUser("user1@gmail.com", "Y"));
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUserStatus() {
        dbManager = DBManager.getInstance();
        UserDaoImpl userDao = new UserDaoImpl(dbManager);
        try {
            userDao.blockUnblockUser("user1@gmail.com", "Y");
            assertEquals("Y", userDao.getUserStatus("user1@gmail.com"));
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}


