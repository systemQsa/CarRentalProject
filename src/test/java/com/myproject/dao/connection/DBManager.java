package com.myproject.dao.connection;


import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager implements ConnectManager {
    private static DBManager dbManager;
    private Connection connection;

    private DBManager() {
    }

    public static DBManager getInstance() {
        if (dbManager == null) {
            dbManager = new DBManager();
        }
        return dbManager;
    }

    public void loadScript(){
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (OutputStream output = new FileOutputStream("connection.properties")) {
            properties.load(classLoader.getResourceAsStream("connection.properties"));
            Class.forName(properties.getProperty("JDBC_DRIVER"));
            properties.store(output, null);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(properties.getProperty("DB_URL"),
                properties.getProperty("USER"),
                properties.getProperty("PASS"))) {
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new FileReader("database/dbtest.sql"));
            scriptRunner.runScript(reader);

        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        Connection connect = null;
        try {
            connect = DriverManager.getConnection("jdbc:h2:~/test;user=sa;password=");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection = connect;
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
