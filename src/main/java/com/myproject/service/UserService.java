package com.myproject.service;

import com.myproject.dao.entity.User;
import com.myproject.dao.entity.UserRole;
import com.myproject.exception.DaoException;
import com.myproject.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    String logInValidation(String login,char[]password) throws ServiceException;
    boolean setUserRole(String login,char[]password);
    User registerNewUser(String name,String surname,String login, char[] password,String phoneNumber) throws ServiceException;
    User deleteUser(User user);
    boolean updateUserBalance(double balance,String login) throws ServiceException;
    boolean updateUserStatus(String login, UserRole userRole) throws ServiceException;
    UserService getInstance();
    Optional<List<User>> getAllUsers() throws ServiceException;
    double getBalance(String login) throws ServiceException;
    boolean blockUser(String login) throws ServiceException;
    boolean unblockUser(String login) throws ServiceException;
    String checkUserStatus(String  login) throws ServiceException;
}
