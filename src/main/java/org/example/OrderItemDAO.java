package org.example;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO
{
    // Create (C)
    public void createOrderItem(int orderID, int menuItemID, int quantity, double lineTotal) throws Exception
    {
        String query = "INSERT INTO OrderItem (FK_orderID, item_id, quantity, lineTotal) VALUES (?, ?, ?, ?)";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query))
        {

            ps.setInt(1, orderID);
            ps.setInt(2, menuItemID);
            ps.setInt(3, quantity);
            ps.setDouble(4, lineTotal);
            ps.executeUpdate();
        }
    }

    // Receive (R)
    public List<String> getAllOrderItems() throws Exception
    {
        String query = "SELECT OrderItemID, FK_orderID, item_id, quantity, lineTotal FROM OrderItem";
        List<String> list = new ArrayList<>();

        try (Connection con = DB.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                list.add(
                        rs.getInt("OrderItemID") + " | " +
                                rs.getInt("FK_orderID") + " | " +
                                rs.getInt("item_id") + " | " +
                                rs.getInt("quantity") + " | " +
                                rs.getDouble("lineTotal"));
            }
        }
        return list;
    }

    public void updateOrderItem(int orderItemID, int quantity, double lineTotal) throws Exception
    {
        String query = "UPDATE OrderItem SET quantity=?, lineTotal=? WHERE OrderItemID=?";

        try(Connection con = DB.getConnection();
            PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setInt(1, quantity);
            ps.setDouble(2, lineTotal);
            ps.setInt(3, orderItemID);
            ps.executeUpdate();
        }
    }

        // Delete (D)
    public void deleteOrderItem(int orderItemID) throws Exception
    {
        String query = "DELETE FROM OrderItem WHERE OrderItemID=?";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, orderItemID);
            ps.executeUpdate();
        }
    }

    public List<String> getOrderItemsWithNames() throws Exception
    {
        String query = "SELECT OrderItem.OrderItemID, MenuItems.name, OrderItem.quantity, OrderItem.lineTotal " +
                "FROM OrderItem " +
                "INNER JOIN MenuItems ON OrderItem.item_id = MenuItems.itemID";

        List<String> list = new ArrayList<>();

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                list.add(
                        rs.getInt("OrderItemID") + " | " +
                                rs.getString("name") + " | " +
                                rs.getInt("quantity") + " | " +
                                rs.getDouble("lineTotal")
                );
            }
        }
        return list;
    }
}
