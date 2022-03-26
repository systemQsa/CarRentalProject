package com.myproject.dao.impl;

import com.myproject.command.util.GeneralConstant;
import com.myproject.dao.UserDao;
import com.myproject.dao.connection.ConnectManager;
import com.myproject.dao.connection.ConnectionPool;
import com.myproject.dao.entity.User;
import com.myproject.dao.entity.UserRole;
import com.myproject.dao.query.QuerySQL;
import com.myproject.exception.DaoException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The UserDaoImpl class represents class to work with User entity and  process all desired user requests
 */

public class UserDaoImpl implements UserDao<User> {
    private Connection connection = null;
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private ConnectManager connectManager = null;

    public UserDaoImpl() {
        connectManager = ConnectionPool.getInstance();
    }

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public UserDaoImpl(ConnectManager connect) {
        this.connectManager = connect;
    }

    @Override
    public void setConnection(ConnectManager connectManager) {
        this.connectManager = connectManager;
    }

    /**
     * The method gets All users from DB
     *
     * @return list of founded users
     * @throws DaoException in case cannot find all users in DB
     */
    @Override
    public List<User> findAll() throws DaoException {
        List<User> listUsers = new ArrayList<>();
        connection = connectManager.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QuerySQL.GET_ALL_USERS);
            while (resultSet.next()) {
                User.UserBuilder userBuilder = new User.UserBuilder();
                userBuilder.setUserId(resultSet.getInt("id_user"))
                        .setFirstName(resultSet.getString("name"))
                        .setLastName(resultSet.getString("surname"))
                        .setLogin(resultSet.getString("login"))
                        .setPhone(resultSet.getString("phone"))
                        .setIsBanned(resultSet.getString("banned"))
                        .setRegisterDate(Timestamp.valueOf(resultSet.getString("register_date")))
                        .setUserRole(UserRole.getRoleId(resultSet.getInt("role_id")));
                listUsers.add(userBuilder.build());
            }

        } catch (SQLException e) {
            logger.error("THERE IS NO USERS IN DATABASE");
            throw new DaoException("CANT FIND ALL USERS", e);
        } finally {
            connectManager.closeConnection(connection);
        }
        logger.info("USERS IN DB WERE FOUND SUCCESSFULLY");
        return listUsers;
    }

    /**
     * THe method changes old password to new one
     *
     * @param login - gets user login
     * @param pass  - gets user new password
     * @return if the password were updated successfully returns true
     * @throws DaoException in case by some reason cannot update user password
     */
    @Override
    public boolean updatePassword(String login, String pass) throws DaoException {
        connection = connectManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.UPDATE_PASS)) {
            statement.setString(1, pass);
            statement.setString(2, login);
            if (statement.executeUpdate() > 0) {
                return true;
            }

        } catch (SQLException e) {
            logger.warn("Some problem cant update user password");
            throw new DaoException(e);
        } finally {
            connectManager.closeConnection(connection);
        }
        return false;
    }

    /**
     * The method find THe whole needed info about given user
     *
     * @param login - gets user login
     * @return found user
     * @throws DaoException in case impossible to find user by given login
     */
    @Override
    public User findByLogin(String login) throws DaoException {
        connection = connectManager.getConnection();
        ResultSet resultSet;
        User.UserBuilder user = new User.UserBuilder();
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.SEARCH_USER_BY_LOGIN)) {
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setUserId(resultSet.getLong("id_user"))
                        .setFirstName(resultSet.getString("name"))
                        .setLastName(resultSet.getString("surname"))
                        .setLogin(resultSet.getString("login"))
                        .setRegisterDate(resultSet.getTimestamp("register_date"))
                        .setIsBanned(resultSet.getString("banned"))
                        .setPhone(resultSet.getString("phone"))
                        .setRole(resultSet.getInt("role_id"));

            }
        } catch (SQLException e) {
            throw new DaoException("Cant find user by given" + login + " login!");
        } finally {
            connectManager.closeConnection(connection);
        }
        return user.build();
    }

    /**
     * The method gets user by given login but returns specific info about given user
     *
     * @param login - gets user login
     * @return found user
     * @throws DaoException in case  cannot find user
     */
    @Override
    public User getUserByLogin(String login) throws DaoException {
        User.UserBuilder user = new User.UserBuilder();
        ResultSet resultSet;
        connection = connectManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.GET_USER_BY_LOGIN)) {
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setUserId(resultSet.getInt("id_user"))
                        .setFirstName(resultSet.getString("name"))
                        .setLogin(resultSet.getString("login"))
                        .setIsBanned(resultSet.getString("banned"))
                        .setHashPass(resultSet.getString("password"))
                        .setRole(resultSet.getInt("role_id"));
            } else {
                throw new SQLException("NO SUCH USER IN DB");
            }

        } catch (SQLException e) {
            logger.error("CANT FIND USER WITH SUCH CREDENTIALS!");
            throw new DaoException(e);
        } finally {
            connectManager.closeConnection(connection);
        }
        logger.info("USER WAS SUCCESSFULLY FOUND IN DB");
        return user.build();
    }

    /**
     * The method add a new user to DB
     * if the first user adds to the DataBase must be registered as admin which begins from (admin01)!!!
     * @param newUser - gets a new user
     * @return new user
     * @throws DaoException in case cannot add an ew user to DB
     */
    @Override
    public User addRecordToTable(User newUser) throws DaoException {
        ResultSet resultSet;
        System.out.println("Add RECORD TO TABLE" + newUser.getPhone());
        connection = connectManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.ADD_NEW_USER, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement setAdminStatus = connection.prepareStatement(QuerySQL.UPDATE_USER_ROLE_TO_ADMIN_ROLE)) {
            connection.setAutoCommit(false);
            statement.setString(1, newUser.getName());
            statement.setString(2, newUser.getSurname());
            statement.setString(3, newUser.getLogin());
            statement.setString(4, newUser.getPassword());
            statement.setString(5, newUser.getPhone());
            if (statement.executeUpdate() > 0) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    if (newUser.getLogin().contains("admin01")) {
                        setAdminStatus.setInt(1, id);
                        setAdminStatus.executeUpdate();
                    }

                }
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new DaoException("Can`t add a new user", e);
            }
            logger.error("SOMETHING WENT WRONG CANT ADD NEW USER!");
            throw new DaoException("Can`t add a new user", e);
        } finally {
            connectManager.closeConnection(connection);
        }
        logger.info("NEW USER WERE REGISTERED SUCCESSFULLY");
        return newUser;
    }

    /**
     * The method top up the user balance
     *
     * @param balance - gets needed amount
     * @param login   = gets user login
     * @return if the balance were successfully replenished returns true
     * @throws DaoException in case cannot top up the user balance
     */
    @Override
    public boolean topUpBalance(double balance, String login) throws DaoException {
        connection = connectManager.getConnection();
        boolean response = false;
        double resultBalance = 0;
        ResultSet resultSet;


        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.SEE_USER_BALANCE)) {
            PreparedStatement statement2 = connection.prepareStatement(QuerySQL.TOP_UP_USER_BALANCE);
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                resultBalance = resultSet.getDouble("balance");
            }
            balance += resultBalance;

            statement2.setDouble(1, balance);
            statement2.setString(2, login);
            if (statement2.executeUpdate() > 0) {
                response = true;
            }
            connection.commit();
            statement2.close();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.fatal("SOMETHING WENT WRONG CANT TOP UP THE BALANCE");
                throw new DaoException("ROLLBACK CANT TOP UP  USER BALANCE", e);
            }
            logger.fatal("IMPOSSIBLE TO TOP UP THE BALANCE");
            throw new DaoException("CANT TOP UP USER BALANCE ", e);
        } finally {
            connectManager.closeConnection(connection);
        }
        logger.info("USER TOP UP BALANCE SUCCESSFULLY " + response);
        return response;
    }

    /**
     * The method gets user balance by given login
     *
     * @param login - gets user login
     * @return found user balance
     * @throws DaoException in case by some reason cannot get user balance
     */
    @Override
    public double getBalance(String login) throws DaoException {
        connection = connectManager.getConnection();
        ResultSet resultSet;
        double resultBalance = 0;
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.SEE_USER_BALANCE)) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                resultBalance = resultSet.getDouble("balance");
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("USER CANT GET BALANCE SOME PROBLEM OCCUR");
                throw new DaoException("CANT SEE USER BALANCE PROPERLY", e);
            }
            throw new DaoException("SOMETHING WENT WRONG CANT FIND USER BALANCE", e);
        } finally {
            connectManager.closeConnection(connection);
        }
        logger.info("USER GOT ITS BALANCE SUCCESSFULLY");
        return resultBalance;
    }

    /**
     * The method sets a new role for the given user
     *
     * @param login-   gets user login
     * @param userRole - gets a new user role
     * @return if the new role were successfully set to given user returns true
     * @throws DaoException in case if cannot set the given role to the given user
     */
    @Override
    public boolean setUserRole(String login, UserRole userRole) throws DaoException {
        connection = connectManager.getConnection();
        boolean resultIsSuccessful = false;
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.SET_USER_ROLE)) {
            statement.setInt(1, userRole.getId());
            statement.setString(2, login);
            if (statement.executeUpdate() > 0) {
                resultIsSuccessful = true;
            }
        } catch (SQLException e) {
            logger.error("SOME PROBLEM SET ROLE FOR USER FAILED");
            throw new DaoException("CANT SET ROLE FROM USER", e);
        } finally {
            connectManager.closeConnection(connection);
        }
        logger.info("ROLE FOR USER " + login + " WERE CHANGED SUCCESSFULLY");
        return resultIsSuccessful;
    }

    /**
     * The method blocks and unblocks the user
     *
     * @param login  - gets the user login
     * @param status - gets the user new status(blocked/unblocked)
     * @return if the status were successfully changed returns true
     * @throws DaoException in case by some reason cannot change the status
     */
    @Override
    public boolean blockUnblockUser(String login, String status) throws DaoException {
        connection = connectManager.getConnection();
        boolean response = false;

        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.BAN_AND_UNBAN_USER)) {
            statement.setString(1, status);
            statement.setString(2, login);
            if (statement.executeUpdate() > 0) {
                response = true;
            }

        } catch (SQLException e) {
            logger.error("CANT BLOCK OR UNBLOCK USER");
            throw new DaoException("SOME PROBLEMS OCCUR DUE TO BLOCKING USER", e);
        } finally {
            connectManager.closeConnection(connection);
        }
        logger.info("USER WERE BLOCKED/UNBLOCKED SUCCESSFULLY");
        return response;
    }

    /**
     * The method gets the user status by given login
     *
     * @param login - gets user login
     * @return found status
     * @throws DaoException in case by some reason impossible to get the status
     */
    @Override
    public String getUserStatus(String login) throws DaoException {
        System.out.println("gt user status DB " + connectManager.getClass());
        connection = connectManager.getConnection();
        ResultSet resultSet;
        String statusCheck = GeneralConstant.EMPTY_STRING;
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.CHECK_USER_STATUS)) {
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                statusCheck = resultSet.getString("banned");
            }
        } catch (SQLException e) {
            logger.error("SOME PROBLEM CANT GET USER STATUS");
            throw new DaoException("CANT GET USER STATUS", e);

        } finally {
            connectManager.closeConnection(connection);
        }
        logger.info("USER GOT ITS STATUS SUCCESSFULLY");
        return statusCheck;
    }

}
