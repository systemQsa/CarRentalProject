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

    public String getUrl() {
        String url = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("connection.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties prop = new Properties();
        try {
            prop.load(fis);
            url = (String) prop.get("connection.url");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    public Connection getConnection(String connectionUrl) throws SQLException {
        Connection connect = null;
        try {
            connect = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection = connect;
        return connect;
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
