package com.myproject.dao.connection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool implements ConnectManager{
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);

    private static volatile ConnectionPool instance;

    private ConnectionPool() {}

    public static ConnectionPool getInstance() {
        ConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionPool();
                }
            }
        }
        return localInstance;
    }

    @Override
    public Connection getConnection() {
        DataSource dataSource;
        Context initialContext;
        Connection connection = null;


        try {
            initialContext = new InitialContext();
            Context lookUp = (Context) initialContext.lookup("java:comp/env");
            dataSource = (DataSource) lookUp.lookup("jdbc/Data");
            connection = dataSource.getConnection();
        } catch (NamingException | SQLException e) {
            logger.fatal("SOME PROBLEM OCCUR CANT CONNECT TO DB");
        }
         return connection;
    }

    public  void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
               logger.fatal("CANT CLOSE CONNECTION!");
            }
        }
    }
}
