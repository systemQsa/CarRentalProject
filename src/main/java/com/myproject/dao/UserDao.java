package com.myproject.dao;

import com.myproject.dao.entity.UserRole;
import com.myproject.exception.DaoException;

import java.util.List;

public interface UserDao<T>{
    List<T> findAll() throws DaoException;
    T findById(int id) throws DaoException;
    boolean deleteById(int id) throws DaoException;
    boolean update(T t) throws DaoException;
   T addRecordToTable(T t) throws DaoException;
   boolean topUpBalance(double cash,String login) throws DaoException;
   double getBalance(String login) throws DaoException;
   boolean blockUnblockUser(String login,String status) throws DaoException;
   String getUserStatus(String login) throws DaoException;
   boolean setUserRole(String login, UserRole userRole) throws DaoException;
}
