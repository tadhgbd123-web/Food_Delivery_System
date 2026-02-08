package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO {

    // Create (C)
    public void createRestaurant(String name, String address, String phone) throws Exception {
        String query = "INSERT INTO Restaurant (name, address, phone) VALUES (?, ?, ?)";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, phone);
            ps.executeUpdate();
        }
    }

    // Read (R)
    public List<String> getAllRestaurants() throws Exception {
        String query = "SELECT restaurantID, name, address, phone FROM Restaurant";
        List<String> list = new ArrayList<>();

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(
                        rs.getInt("restaurantID") + " | " +
                                rs.getString("name") + " | " +
                                rs.getString("address") + " | " +
                                rs.getString("phone"));
            }
            return list;
        }
    }

    // Update (U)
    public void updateRestaurantPhone(int id, String phone) throws Exception {
        String query = "UPDATE Restaurant SET phone=? WHERE restaurantID=?";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, phone);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    // Delete (D)
    public void deleteRestaurant(int id) throws Exception {
        String query = "DELETE FROM Restaurant WHERE restaurantID=?";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}