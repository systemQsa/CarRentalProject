package com.myproject.dao.impl;

import com.myproject.command.util.GeneralConstant;
import com.myproject.dao.connection.ConnectionPool;
import com.myproject.dao.entity.User;
import com.myproject.dao.entity.UserRole;
import com.myproject.dao.query.QuerySQL;
import com.myproject.exception.DaoException;
import com.myproject.util.Encryption;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements com.myproject.dao.UserDao<User> {
    private Connection connection = null;
    private final Encryption encryption = new Encryption();
    private static final Logger logger = LogManager.getLogger(UserDao.class);

    @Override
    public List<User> findAll() throws DaoException {
        List<User> listUsers = new ArrayList<>();
        connection = ConnectionPool.getInstance().getConnection();
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
            ConnectionPool.closeConnection(connection);
        }
        logger.info("USERS IN DB WERE FOUND SUCCESSFULLY");
        return listUsers;
    }

    @Override
    public User findById(int userId) {
        return null;
    }

    @Override
    public boolean deleteById(int userId) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    public User getUserByLogin(String login) throws DaoException {
        User.UserBuilder user = new User.UserBuilder();
        ResultSet resultSet;
        connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.GET_USER_BY_LOGIN)) {
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setUserId(resultSet.getInt("id_user"))
                        .setFirstName(resultSet.getString("name"))
                        .setIsBanned(resultSet.getString("banned"))
                        .setHashPass(resultSet.getString("password"))
                        .setRole(resultSet.getInt("role_id"));
            }

        } catch (SQLException e) {
            logger.error("CANT FIND USER WITH SUCH CREDENTIALS!");
            throw new DaoException("NO SUCH USER IN DB", e);
        } finally {
            ConnectionPool.closeConnection(connection);
        }
        logger.info("USER WAS SUCCESSFULLY FOUND IN DB");
        return user.build();
    }

    @Override
    public User addRecordToTable(User newUser) throws DaoException {
        ResultSet resultSet;
        System.out.println("Add RECORD TO TABLE" + newUser.getPhone());
        User.UserBuilder userBuilder = new User.UserBuilder();
        connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.ADD_NEW_USER, Statement.RETURN_GENERATED_KEYS)) {
            int index = 1;
            statement.setString(1, newUser.getName());
            statement.setString(2, newUser.getSurname());
            statement.setString(3, newUser.getLogin());
            statement.setString(4, newUser.getPassword());
            statement.setString(5, newUser.getPhone());
            if (statement.executeUpdate() > 0) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            logger.error("SOMETHING WENT WRONG CANT ADD NEW USER!");
            throw new DaoException("Can`t add a new user", e);
        } finally {
            ConnectionPool.closeConnection(connection);
        }
        logger.info("NEW USER WERE REGISTERED SUCCESSFULLY");
        return userBuilder.build();
    }

    @Override
    public boolean topUpBalance(double balance, String login) throws DaoException {
        connection = ConnectionPool.getInstance().getConnection();
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
            ConnectionPool.closeConnection(connection);
        }
        logger.info("USER TOP UP BALANCE SUCCESSFULLY");
        return response;
    }

    @Override
    public double getBalance(String login) throws DaoException {
        connection = ConnectionPool.getInstance().getConnection();
        ResultSet resultSet;
        double resultBalance = 0;
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.SEE_USER_BALANCE)) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
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
            ConnectionPool.closeConnection(connection);
        }
        logger.info("USER GOT ITS BALANCE SUCCESSFULLY");
        return resultBalance;
    }

    @Override
    public boolean setUserRole(String login, UserRole userRole) throws DaoException {
        connection = ConnectionPool.getInstance().getConnection();
        boolean resultIsSuccessful = false;
        try(PreparedStatement statement = connection.prepareStatement(QuerySQL.SET_USER_ROLE)){
            statement.setInt(1,userRole.getId());
            statement.setString(2,login);
            if (statement.executeUpdate() > 0){
                resultIsSuccessful = true;
            }
        }catch (SQLException e){
            logger.error("SOME PROBLEM SET ROLE FOR USER FAILED");
            throw new DaoException("CANT SET ROLE FROM USER",e);
        }finally {
            ConnectionPool.closeConnection(connection);
        }
        logger.info("ROLE FOR USER " + login + " WERE CHANGED SUCCESSFULLY");
        return resultIsSuccessful;
    }

    @Override
    public boolean blockUnblockUser(String login, String status) throws DaoException {
        connection = ConnectionPool.getInstance().getConnection();
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
            ConnectionPool.closeConnection(connection);
        }
        logger.info("USER WERE BLOCKED/UNBLOCKED SUCCESSFULLY");
        return response;
    }

    @Override
    public String getUserStatus(String login) throws DaoException {
        connection = ConnectionPool.getInstance().getConnection();
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
            ConnectionPool.closeConnection(connection);
        }
        logger.info("USER GOT ITS STATUS SUCCESSFULLY");
        return statusCheck;
    }

    public int getUserRoleAccordingToInput(String login, String password1) throws DaoException {
        ResultSet resultSet;
        String realPass;
        int id = 0;
        System.out.println("USER DAO    " + login + "  " + password1);
        connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement(QuerySQL.GET_USER_ID_ACCORDING_TO_INPUT)) {
            System.out.println("execute");
            statement.setString(1, login);
            // statement.setString(2, password);
            resultSet = statement.executeQuery();
            System.out.println("execute2");
            if (resultSet.next()) {
                id = resultSet.getInt("role_id");
                realPass = resultSet.getString("password");
                System.out.println("realPass " + realPass);
                //   byte[] byteArrayFromHexStr = Encryption.getByteArrayFromHexStr(realPass);
                //   System.out.println("match "+Arrays.toString(byteArrayFromHexStr));
                System.out.println("IDENTICAL " + encryption.matchPasswords(password1.getBytes(), realPass.getBytes()));

            }
            System.out.println("id " + id);

        } catch (SQLException e) {
            throw new DaoException("There is no user with such credentials in DB", e);
        } finally {
            ConnectionPool.closeConnection(connection);
        }
        return id;
    }
}
