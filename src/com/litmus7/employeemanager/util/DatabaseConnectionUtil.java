package com.litmus7.employeemanager.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionUtil {

    private static final String url = "jdbc:mysql://localhost:3306/empdb";
    private static final String user = "habeeba";
    private static final String password = "abc";

    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Connection getConnection() {
        return connection;
    }
}
