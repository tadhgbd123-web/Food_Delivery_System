package org.example;

import java.sql.Connection;

public class testConnections
{
    public static void main(String[] args)
    {
        try (Connection con = DB.getConnection())
        {
            System.out.println("✅ Connected to MySQL successfully!");
        } catch (Exception e)
        {
            System.out.println("❌ Connection failed:");
            e.printStackTrace();
        }
    }
}
