package com.myproject.dao.connection;

import java.sql.Connection;

public interface ConnectManager {
    Connection getConnection();
    void closeConnection(Connection connection);
}
