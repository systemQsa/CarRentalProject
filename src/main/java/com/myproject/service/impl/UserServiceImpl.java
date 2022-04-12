package com.myproject.service.impl;

import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
import com.myproject.dao.UserDao;
import com.myproject.dao.entity.User;
import com.myproject.dao.entity.UserRole;
import com.myproject.exception.DaoException;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.factory.impl.AbstractFactoryImpl;
import com.myproject.service.UserService;
import com.myproject.util.EncryptUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * The UserServiceImpl class represents class which provides methods which are related to work with user
 */

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final UserDao<User> userDao;

    public UserServiceImpl(){
        userDao = new AbstractFactoryImpl().getFactory().getDaoFactory().getUserDao();
    }

    public UserServiceImpl(UserDao<User> userDao){
        this.userDao = userDao;
    }

    /**
     * The method change old user password to new one
     * @param login - gets user login
     * @param password - gets new user password
     * @return if the password was successfully changed in DataBase
     * @throws ServiceException in case if can`t reset the password for given user
     */
    @Override
    public boolean resetPassword(String login, char[] password) throws ServiceException {
        boolean response;
        try {
            String encryptedPass =  EncryptUtil.encrypt(String.valueOf(password)
                    .getBytes(StandardCharsets.UTF_8), GeneralConstant.Util.KEY);
            response = userDao.updatePassword(login,encryptedPass);
        } catch (Exception e) {
            logger.warn("Impossible to reset the password in UserServiceImpl Class due to some problem occur");
            throw new ServiceException(e);
        }
        return response;
    }

    /**
     * The method gets all user info by given login
     * @param login - gets user login
     * @return found user
     * @throws ServiceException in case can`t get user by given login
     */
    @Override
    public Optional<User> getUser(String login) throws ServiceException {

        try {
            return Optional.ofNullable(userDao.findByLogin(login));
        } catch (DaoException e) {
            logger.warn("Cant get user in UserServiceImpl class by given login " + login);
            throw new ServiceException(e.getMessage());
        }
    }


    /**
     * The method finds all user info according to given data
     * @param login - gets user login
     * @param password - gets user password
     * @return found user
     * @throws ServiceException in case if impossible to get such user by given credentials
     */
    @Override
    public User getUserByLoginAndPass(String login, char[] password) throws ServiceException {
        User user;
        try {
            user = userDao.getUserByLogin(login);
        } catch (DaoException e) {
            logger.error("SOME PROBLEM CANT GET USER FROM DB");
            throw new ServiceException(ConstantPage.LOG_IN_PAGE);
        }
        return user;
    }

    /**
     * Gets the user role depending on given login and password
     * before getting the user decrypt the password to check if all data are valid
     * @param login - gets user login
     * @param password - gets user password
     * @param request - gets the HttpRequest in case in problem occur put inform message to request
     * @return user role
     * @throws ValidationException in case if problem occur and can`t get user by given credentials
     */
    @Override
    public String logInValidation(String login, char[] password,
                                  HttpServletRequest request) throws ValidationException {
        int userId = 0;
        User user;
        try {
            user = getUserByLoginAndPass(login, password);
        } catch (ServiceException e) {
            logger.warn("Can`t get user by given credentials in UserServiceImpl class");
            throw new ValidationException(e.getMessage());
        }

        boolean decrypt;
        try {
            decrypt = EncryptUtil.decryptPass(user.getPassword(), GeneralConstant.Util.KEY, password);
            if (decrypt) {
                userId = user.getRole();
            }
        } catch (Exception e) {
            logger.warn("Impossible to decrypt given password in UserServiceImpl class");
            throw new ValidationException(e.getMessage());
        }

        return UserRole.getRoleId(userId);
    }


    /**
     * The methods find all users
     * @return all found users
     * @throws ServiceException in case if can`t find all users in DataBase
     */
    @Override
    public Optional<List<User>> getAllUsers() throws ServiceException {
        try {
            return Optional.ofNullable(userDao.findAll());
        } catch (DaoException e) {
            logger.warn("CANT FIND ALL USERS IN UserServiceImpl class");
            throw new ServiceException("CANT FIND ALL USERS");
        }
    }

    /**
     * The method find user balance depends on login data
     * @param login - gets user login
     * @return user balance on card
     * @throws ServiceException in case if problem occur and impossible to get user balance
     */
    @Override
    public double getBalance(String login) throws ServiceException {
        double userBalance;
        try {
            userBalance = userDao.getBalance(login);
        } catch (DaoException e) {
            logger.warn("SOME PROBLEM CANT GET USER BALANCE IN UserServiceImpl class");
            throw new ServiceException("SOME PROBLEM CANT GET USER BALANCE IN SERVICE", e);
        }
        return userBalance;
    }

    /**
     * The method register new user
     * @param name - gets name
     * @param surname - gets user surname
     * @param login - gets user login
     * @param password - gets user password
     * @param phoneNumber - gets user phone number
     * @return all user credentials
     * @throws ServiceException in case if can`t register a new user
     */
    @Override
    public User registerNewUser(String name, String surname, String login,
                                char[] password, String phoneNumber) throws ServiceException {

        try {
            String encrypt = EncryptUtil.encrypt(String.valueOf(password)
                    .getBytes(StandardCharsets.UTF_8), GeneralConstant.Util.KEY);
            Arrays.fill(password,(char)67);
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

    /**
     * The method top up the user balance
     * @param balance - gets amount
     * @param login - gets user login
     * @return in case if replenishment went well
     * @throws ServiceException in case if by some reason impossible to top up user balance
     */
    @Override
    public boolean updateUserBalance(double balance, String login) throws ServiceException {
        try {
            return userDao.topUpBalance(balance, login);
        } catch (DaoException e) {
            logger.warn("CANT TOP UP USER BALANCE SOMETHING WENT WRONG IN UserServiceImpl class");
            throw new ServiceException(e);
        }

    }

    /**
     * The method gets user status
     * @param login- gets user login
     * @return user status
     * @throws ServiceException in case if can`t get user status
     */
    @Override
    public String checkUserStatus(String login) throws ServiceException {
        try {
            return userDao.getUserStatus(login);
        } catch (DaoException e) {
            logger.warn("CANT GET USER STATUS IN UserServiceImpl class");
            throw new ServiceException("CANT GET USER STATUS", e);
        }
    }

    /**
     * The method unblocks the given user
     * @param login - gets user login
     * @return if user was successfully unblocked returns true
     * @throws ServiceException in case if impossible to unblock the user
     */
    @Override
    public boolean unblockUser(String login) throws ServiceException {
        try {
            return userDao.blockUnblockUser(login, "N");
        } catch (DaoException e) {
            logger.warn("CANT UNBLOCK GIVEN USER " + login + " IN UserServiceImpl class");
            throw new ServiceException("CANT UNBLOCK USER IN SERVICE", e);
        }

    }

    /**
     * The method blocks given user
     * @param login - gets user login
     * @return if user was successfully blocked returns true
     * @throws ServiceException in case if impossible to block given user
     */
    @Override
    public boolean blockUser(String login) throws ServiceException {
        try {
            return userDao.blockUnblockUser(login, "Y");
        } catch (DaoException e) {
            logger.warn("CANT BLOCK GIVEN USER " + login + " IN UserServiceImpl class");
            throw new ServiceException("CANT BLOCK USER", e);
        }
    }

    /**
     * The method change user role depend on from the desired role
     * @param login - gets user login
     * @param userRole - gets new user role
     * @return if updating was successful returns true
     * @throws ServiceException in case if impossible to update user status
     */
    @Override
    public boolean updateUserStatus(String login, UserRole userRole) throws ServiceException {
        boolean statusChanged = false;
        try {
            User user = userDao.getUserByLogin(login);
            if (user.getIsBanned().equals(GeneralConstant.NOT_BLOCKED_STATUS)
                    && user.getRole() != UserRole.MANAGER.getId()
                    && userRole.getRole().equals(GeneralConstant.MANAGER)) {
                statusChanged = userDao.setUserRole(login, UserRole.MANAGER);
            } else if (user.getIsBanned().equals(GeneralConstant.NOT_BLOCKED_STATUS)
                    && user.getRole() != UserRole.USER.getId()
                    && userRole.getRole().equals(GeneralConstant.USER)) {
                statusChanged = userDao.setUserRole(login, UserRole.USER);
            }
        } catch (DaoException e) {
            logger.warn("CANT SET NEW ROLE FOR USER IN UserServiceImpl class");
            throw new ServiceException("CANT SET NEW ROLE FOR GIVEN USER", e);
        }
        return statusChanged;
    }
}
