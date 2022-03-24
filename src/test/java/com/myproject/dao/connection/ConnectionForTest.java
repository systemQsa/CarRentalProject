package com.myproject.dao.connection;


import com.myproject.dao.connection.ConnectManager;
import com.myproject.dao.connection.ConnectionPool;

import javax.naming.Context;
import javax.sql.DataSource;
import javax.xml.transform.OutputKeys;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionForTest implements ConnectManager {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String URL_CONNECTION = "jdbc:h2:~/test;user=sa;password=";
    private static final String USER = "sa";
    private static final String PASS = "";

    private static ConnectionForTest instance;

    public static ConnectionForTest getInstance(){
        ConnectionForTest local = instance;
        if (local == null) {
            synchronized (ConnectionPool.class) {
                local = instance;
                if (local == null) {
                    instance = local = new ConnectionForTest();
                }
            }
        }
        return local;

    }

    @Override
    public Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        try(OutputStream stream = new FileOutputStream("src/test/resources/connection.properties")){
            Properties properties = new Properties();
            properties.setProperty("connection.url", URL_CONNECTION);
            properties.store(stream, null);

        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    @Override
    public void closeConnection(Connection connection) {
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
