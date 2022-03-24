package com.myproject.dao;

import com.myproject.dao.connection.ConnectManager;
import com.myproject.dao.entity.User;
import com.myproject.dao.entity.UserRole;
import com.myproject.exception.DaoException;

import java.util.List;

public interface UserDao<T> {
    List<T> findAll() throws DaoException;

    T findByLogin(String login) throws DaoException;

    T addRecordToTable(T t) throws DaoException;

    boolean topUpBalance(double cash, String login) throws DaoException;

    double getBalance(String login) throws DaoException;

    boolean blockUnblockUser(String login, String status) throws DaoException;

    String getUserStatus(String login) throws DaoException;

    boolean setUserRole(String login, UserRole userRole) throws DaoException;

    void setConnection(ConnectManager connectManager);

    User getUserByLogin(String login) throws DaoException;

    boolean updatePassword(String login, String pass) throws DaoException;
}
