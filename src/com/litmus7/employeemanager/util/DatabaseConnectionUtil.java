package com.litmus7.employeemanager.util;

import com.litmus7.employeemanager.constants.SqlConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionUtil {

    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(
                SqlConstants.url,
                SqlConstants.user,
                SqlConstants.password
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
