package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB
{


    private static final String DB_NAME = "food_delivery_system";


    private static final String URL =
            "jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false&serverTimezone=UTC";


    private static final String USER = "root";
    private static final String PASS = "AobhetainBailey2022";

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
