package com.myproject.service.impl;

import com.myproject.command.util.GeneralConstant;
import com.myproject.dao.UserDao;
import com.myproject.dao.connection.ConnectManager;
import com.myproject.dao.connection.ConnectionPool;
import com.myproject.dao.entity.User;
import com.myproject.dao.entity.UserRole;
import com.myproject.dao.impl.UserDaoImpl;
import com.myproject.exception.DaoException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.factory.AbstractFactory;
import com.myproject.factory.DaoFactory;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.factory.impl.DaoFactoryImpl;
import com.myproject.service.UserService;
import com.myproject.util.EncryptUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final UserDao<User> userDao = new AbstractFactoryImpl().getFactory().getDaoFactory().getUserDao();

    public UserServiceImpl(){
    }

     @Override
    public Optional<User> getUser(String login) throws ServiceException {

        try {
            return Optional.of(userDao.findByLogin(login));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User getUserByLoginAndPass(String login, char[] password) throws ServiceException {
        User user;
        try {
            user = userDao.getUserByLogin(login);
        } catch (DaoException e) {
            logger.error("SOME PROBLEM CANT GET USER FROM DB");
            throw new ServiceException("/login.jsp");
        }
        return user;
    }

    @Override
    public String logInValidation(String login, char[] password, HttpServletRequest request) throws ValidationException {
        int userId = 0;
        User user;
        try {
            user = getUserByLoginAndPass(login, password);
        } catch (ServiceException e) {
            request.setAttribute("err", 2);
            request.setAttribute("errMSG", "You are not registered yet");
            throw new ValidationException(e.getMessage());
        }

        boolean decrypt;
        try {
            decrypt = EncryptUtil.decryptPass(user.getPassword(), "123", password);
            if (decrypt) {
                userId = user.getRole();
                System.out.println("PASSWORD MATCHES!!");
            }
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }

        return UserRole.getRoleId(userId);
    }


    @Override
    public Optional<List<User>> getAllUsers() throws ServiceException {
        try {
            return Optional.of(userDao.findAll());
        } catch (DaoException e) {
            logger.warn("CANT FIND ALL USERS IN UserServiceImpl class");
            throw new ServiceException("CANT FIND ALL USERS");
        }
    }

    @Override
    public double getBalance(String login) throws ServiceException {
        double userBalance = 0;
        try {
            userBalance = userDao.getBalance(login);
        } catch (DaoException e) {
            logger.warn("SOME PROBLEM CANT GET USER BALANCE IN UserServiceImpl class");
            throw new ServiceException("SOME PROBLEM CANT GET USER BALANCE IN SERVICE", e);
        }
        return userBalance;
    }

    @Override
    public boolean setUserRole(String login, char[] password) {
        return false;
    }

    @Override
    public User registerNewUser(String name, String surname, String login, char[] password, String phoneNumber) throws ServiceException {
        System.out.println("service " + phoneNumber);
        System.out.println("REGISTER NEW USER!");
        try {
            String encrypt = EncryptUtil.encrypt(String.valueOf(password).getBytes(StandardCharsets.UTF_8), "123");
            User.UserBuilder user = new User.UserBuilder();
            user.setFirstName(name)
                    .setLastName(surname)
                    .setLogin(login)
                    .setHashPass(encrypt)
                    .setPhone(phoneNumber);
            return userDao.addRecordToTable(user.build());
        } catch (Exception e) {
            logger.warn("CANT REGISTER A NEW USER IN UserServiceImpl class");
            throw new ServiceException("SERVICE EXCEPTION", e);
        }
    }

    @Override
    public User deleteUser(User user) {
        return null;
    }

    @Override
    public boolean updateUserBalance(double balance, String login) throws ServiceException {
        boolean response;
        try {
            response = userDao.topUpBalance(balance, login);
        } catch (DaoException e) {
            logger.warn("CANT TOP UP USER BALANCE SOMETHING WENT WRONG IN UserServiceImpl class");
            throw new ServiceException(e);
        }
        return response;
    }

    @Override
    public String checkUserStatus(String login) throws ServiceException {
        String status;
        try {
            status = userDao.getUserStatus(login);
        } catch (DaoException e) {
            logger.warn("CANT GET USER STATUS IN UserServiceImpl class");
            throw new ServiceException("CANT GET USER STATUS", e);
        }
        return status;
    }

    @Override
    public boolean unblockUser(String login) throws ServiceException {
        boolean response;
        try {
            response = userDao.blockUnblockUser(login, "N");
        } catch (DaoException e) {
            logger.warn("CANT UNBLOCK GIVEN USER " + login + " IN UserServiceImpl class");
            throw new ServiceException("CANT UNBLOCK USER IN SERVICE", e);
        }
        return response;
    }

    @Override
    public boolean blockUser(String login) throws ServiceException {
        boolean isSuccess;
        try {
            isSuccess = userDao.blockUnblockUser(login, "Y");
        } catch (DaoException e) {
            logger.warn("CANT BLOCK GIVEN USER " + login + " IN UserServiceImpl class");
            throw new ServiceException("CANT BLOCK USER", e);
        }
        return isSuccess;
    }

    @Override
    public boolean updateUserStatus(String login, UserRole userRole) throws ServiceException {
        boolean statusChanged = false;
        try {
            User user = userDao.getUserByLogin(login);
            if (user.getIsBanned().equals(GeneralConstant.NOT_BLOCKED_STATUS) && user.getRole() != UserRole.MANAGER.getId()
                    && userRole.getRole().equals(GeneralConstant.MANAGER)) {
                statusChanged = userDao.setUserRole(login, UserRole.MANAGER);
            } else if (user.getIsBanned().equals(GeneralConstant.NOT_BLOCKED_STATUS) && user.getRole() != UserRole.USER.getId()
                    && userRole.getRole().equals(GeneralConstant.USER)) {
                statusChanged = userDao.setUserRole(login, UserRole.USER);
            }
        } catch (DaoException e) {
            logger.warn("CANT SET NEW ROLE FOR USER IN UserServiceImpl class");
            throw new ServiceException("CANT SET NEW ROLE FOR GIVEN USER", e);
        }
        return statusChanged;
    }

    @Override
    public UserService getInstance() {
        return null;
    }
}
