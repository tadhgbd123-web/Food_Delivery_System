package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CustomerDAO
{

    //Create (C)
    public void createCustomer(String name, String email, String phoneNumber, String address) throws Exception
    {
        String query = "INSERT INTO Customer (name, email, phoneNumber, address) VALUES (?, ?, ?, ?)";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phoneNumber);
            ps.setString(4, address);
            ps.executeUpdate();
        }
    }

    // Read / Receive (R)
    public List<String> getAllCustomers() throws Exception {
        String query = "SELECT idCustomer, name, email, phoneNumber, address FROM Customer";
        List<String> list = new ArrayList<>();

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(
                        rs.getInt("idCustomer") + " | " +
                                rs.getString("name") + " | " +
                                rs.getString("email"));

            }
            return list;
        }
    }

        //UPDATE (U)
        public void updateCustomerEmail(int id, String email) throws Exception
        {
            String query = "UPDATE Customer SET email=? WHERE idCustomer=?";

            try(Connection con = DB.getConnection();
                PreparedStatement ps = con.prepareStatement(query))
            {
                ps.setString(1, email);
                ps.setInt(2, id);
                ps.executeUpdate();
            }
        }

        //DELETE
        public void deleteCustomer(int id) throws Exception
        {
            String query = "DELETE FROM Customer WHERE idCustomer=?";

            try (Connection con = DB.getConnection();
            PreparedStatement ps = con.prepareStatement(query))
            {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        }


    }
