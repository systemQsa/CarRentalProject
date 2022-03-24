package com.myproject.dao.connection;

import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class ConnectionPoolTest {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Test
    public void getInstance() {
        connectionPool = ConnectionPool.getInstance();
        assertNotNull(connectionPool);
    }

    @Test
    public void closeConnection() {
        Connection connection = connectionPool.getConnection();
        connectionPool.closeConnection(connection);
        assertNull(connection);
    }
}