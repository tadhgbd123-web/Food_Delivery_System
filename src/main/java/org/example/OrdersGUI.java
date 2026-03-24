package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.example.OrdersDAO;



import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class OrdersGUI extends JFrame {
    JLabel customerIDLabel, statusLabel, totalLabel;

    JTextField customerIDField, statusField, totalField;

    JTable ordersTable;
    DefaultTableModel tableModel;

    JButton addButton, updateButton, backButton;

    OrdersDAO dao = new OrdersDAO();

    public OrdersGUI() {
        setTitle("Order Management");

        setSize(700, 600);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        customerIDLabel = new JLabel("Customer ID:");
        statusLabel = new JLabel("Status:");
        totalLabel = new JLabel("Total:");

        customerIDField = new JTextField(15);
        statusField = new JTextField(15);
        totalField = new JTextField(15);

        addButton = new JButton("Add Order");
        updateButton = new JButton("Update Order");
        backButton = new JButton("Back to Main Menu");

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        formPanel.add(customerIDLabel);
        formPanel.add(customerIDField);

        formPanel.add(statusLabel);
        formPanel.add(statusField);

        formPanel.add(totalLabel);
        formPanel.add(totalField);

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(backButton);

        tableModel = new DefaultTableModel();

        tableModel.addColumn("Order ID");
        tableModel.addColumn("Customer Name");
        tableModel.addColumn("Order Date");
        tableModel.addColumn("Status");
        tableModel.addColumn("Total");

        ordersTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(ordersTable);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(tablePanel, BorderLayout.CENTER);

        // Center the window on the screen
        setLocationRelativeTo(null);

        // Load orders into the table
        setVisible(true);


        loadOrdersTable();

        ordersTable.getSelectionModel().addListSelectionListener(e ->
        {
            int row = ordersTable.getSelectedRow();

            if (row != -1)
            {
                customerIDField.setText("");
                statusField.setText(tableModel.getValueAt(row, 3).toString());
                totalField.setText(tableModel.getValueAt(row, 4).toString());
            }
        });

        addButton.addActionListener(e ->
        {
            try {
                int customerID = Integer.parseInt(customerIDField.getText());

                String status = statusField.getText();

                double total = Double.parseDouble(totalField.getText());

                dao.createOrder(customerID, status, total);

                loadOrdersTable();

                JOptionPane.showMessageDialog(this, "Order Created!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error! " + ex.getMessage());
            }
        });


        updateButton.addActionListener(e ->
        {
            try {
                int row = ordersTable.getSelectedRow();

                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "Please select an order!");
                    return;
                }

                int orderID = Integer.parseInt(ordersTable.getValueAt(row, 0).toString());

                String status = statusField.getText();
                double total = Double.parseDouble(totalField.getText());

                dao.updateOrderStatus(orderID, status, total);

                loadOrdersTable();

                JOptionPane.showMessageDialog(this, "Order Updated!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error! " + ex.getMessage());
            }


        });

        // Add action listener for the back button to return to the main menu
        backButton.addActionListener(e ->
        {
            new MainMenuGUI();
            dispose();
        });

    }

    public void loadOrdersTable() {
        try {
            tableModel.setRowCount(0);

            for (String order : dao.getOrdersItemWithName()) {
                tableModel.addRow(order.split(" \\| "));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading orders: " + e.getMessage());
        }

    }
}


