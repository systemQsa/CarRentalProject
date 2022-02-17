package com.myproject.dao.connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ConnectionPool {
    private final Logger log = Logger.getLogger(ConnectionPool.class.getName());
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
            log.warning("some problem with connection to DB");
        }
        System.out.println("RETURN CONNECTION" + connection);
        return connection;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
