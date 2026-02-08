package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersDAO
{
    //Create (C)
    public void createOrder(int customerID, String status, double total) throws Exception{
        String query = "INSERT INTO Orders (customerID, orderDate, status, total) VALUES (?, NOW(), ?, ?)";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, customerID);
            ps.setString(2, status);
            ps.setDouble(3, total);
            ps.executeUpdate();
        }
    }

    // Read / Receive (R)
    public List<String> getAllOrders() throws Exception
    {
        String query = "SELECT orderID, customerID, orderDate, status, total FROM Orders";
        List<String> list = new ArrayList<>();

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                list.add(
                        rs.getInt("orderID") + " | " +
                                rs.getInt("customerID") + " | " +
                                rs.getTimestamp("orderDate") + " | " +
                                rs.getString("status") + " | €" +
                                rs.getDouble("total"));

            }
        }
        return list;
    }

    // UPDATE STATUS
    public void updateOrderStatus(int orderID, String status) throws Exception {

        String query = "UPDATE Orders SET status=? WHERE orderID=?";

        try(Connection con = DB.getConnection();
            PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, status);
            ps.setInt(2, orderID);

            ps.executeUpdate();
        }
    }
}


