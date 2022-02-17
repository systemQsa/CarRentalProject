package com.myproject.service.impl;

import com.myproject.command.util.GeneralConstant;
import com.myproject.dao.entity.User;
import com.myproject.dao.entity.UserRole;
import com.myproject.dao.impl.UserDao;
import com.myproject.exception.DaoException;
import com.myproject.exception.ServiceException;
import com.myproject.service.UserService;
import com.myproject.util.EncryptUtil;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class UserServiceImpl implements UserService {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
    private UserDao userDao = new UserDao();

    @Override
    public String logInValidation(String login, char[] password) throws ServiceException {
        //todo validate date on front
        int userId = 0;
        try {
            User user = userDao.getUserByLogin(login);
            System.out.println("PASSWORD DECRYPT");
            boolean decrypt = EncryptUtil.decryptPass(user.getPassword(), "123", password);
            if (decrypt) {
                userId = user.getRole();
                System.out.println("PASSWORD MATCHES!!");
            } else {
                System.out.println("PASSWORD NOT MATCH");
            }

        } catch (DaoException e) {
            logger.warning("NO SUCH USER IN DATABASE");
            throw new ServiceException("USER LOGIN CHECK FAILED", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UserRole.getRoleId(userId);
    }

    @Override
    public Optional<List<User>> getAllUsers() throws ServiceException {
        try {
            return Optional.of(userDao.findAll());
        } catch (DaoException e) {
            throw new ServiceException("CANT FIND ALL USERS", e);
        }
    }

    @Override
    public double getBalance(String login) throws ServiceException {
        double userBalance = 0;
        try {
            userBalance = userDao.getBalance(login);
        } catch (DaoException e) {
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
            logger.warning("Cant ADD A NEW USER");
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
            throw new ServiceException("CANT TOP UP USER BALANCE", e);
        }
        return response;
    }

    @Override
    public String checkUserStatus(String login) throws ServiceException {
        String status;
        try {
            status = userDao.getUserStatus(login);
        } catch (DaoException e) {
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
            throw new ServiceException("CANT BLOCK USER", e);
        }
        return isSuccess;
    }

    @Override
    public boolean updateUserStatus(String login,UserRole userRole)throws ServiceException {
        boolean statusChanged = false;
        try {
            User user = userDao.getUserByLogin(login);
            if (user.getIsBanned().equals(GeneralConstant.NOT_BLOCKED_STATUS) && user.getRole()!= UserRole.MANAGER.getId()
                    && userRole.getRole().equals(GeneralConstant.MANAGER)){
                statusChanged = userDao.setUserRole(login,UserRole.MANAGER);
            }else if (user.getIsBanned().equals(GeneralConstant.NOT_BLOCKED_STATUS) && user.getRole() != UserRole.USER.getId()
                    && userRole.getRole().equals(GeneralConstant.USER)){
                statusChanged = userDao.setUserRole(login,UserRole.USER);
            }
        } catch (DaoException e) {
            throw new ServiceException("CANT SET NEW ROLE FOR GIVEN USER",e);
        }
        return statusChanged;
    }

    @Override
    public UserService getInstance() {
        return null;
    }
}
