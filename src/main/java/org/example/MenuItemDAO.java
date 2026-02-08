package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO
{

    //Create (C)
    public void createMenuItem(int restaurantID, String name, double price, boolean available) throws Exception
    {
        String query = "INSERT INTO MenuItems (restaurantID, name, price, available) VALUES (?, ?, ?, ?)";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, restaurantID);
            ps.setString(2, name);
            ps.setDouble(3, price);
            ps.setBoolean(4, available);
            ps.executeUpdate();
        }
    }

    // Read / Receive (R)
    public List<String> getAllMenuItems() throws Exception {
        String query = "SELECT itemID, name, price, available FROM MenuItems";
        List<String> list = new ArrayList<>();

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(
                        rs.getInt("itemID") + " | " +
                                rs.getString("name") + " | €" +
                                rs.getDouble("price") + " | Available: " +
                                rs.getBoolean("available"));

            }
            return list;
        }
    }

    //UPDATE (U)
    public void updatePrice(int itemID, double newPrice) throws Exception
    {
        String query = "UPDATE MenuItems SET price=? WHERE itemID=?";

        try(Connection con = DB.getConnection();
            PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setDouble(1, newPrice);
            ps.setInt(2, itemID);
            ps.executeUpdate();
        }
    }

    //DELETE
    public void deleteMenuItem(int itemID) throws Exception
    {
        String query = "DELETE FROM MenuItems WHERE itemID = ?";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setInt(1, itemID);
            ps.executeUpdate();
        }
    }


}

