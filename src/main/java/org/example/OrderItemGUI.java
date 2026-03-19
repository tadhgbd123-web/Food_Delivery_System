package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class OrderItemGUI extends JFrame
{
    JLabel orderIDLabel, itemIDLabel, quantityLabel, totalLabel;
    JTextField orderIDTextField, itemIDTextField, quantityTextField, totalTextField;

    JButton addButton, updateButton, deleteButton,backButton;

    JTable table;
    DefaultTableModel model;

    OrderItemDAO orderItemDAO = new OrderItemDAO();

    public OrderItemGUI()
    {
        setTitle("Order Item Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Form Panel
        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));

        orderIDLabel = new JLabel("Order ID:");
        itemIDLabel = new JLabel("Item ID:");
        quantityLabel = new JLabel("Quantity:");
        totalLabel = new JLabel("Total:");

        orderIDTextField = new JTextField();
        itemIDTextField = new JTextField();
        quantityTextField = new JTextField();
        totalTextField = new JTextField();

        form.add(orderIDLabel);
        form.add(orderIDTextField);
        form.add(itemIDLabel);
        form.add(itemIDTextField);
        form.add(quantityLabel);
        form.add(quantityTextField);
        form.add(totalLabel);
        form.add(totalTextField);

        // Button Panel
        JPanel buttons = new JPanel();
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        backButton = new JButton("Back to Main Menu");

        buttons.add(addButton);
        buttons.add(updateButton);
        buttons.add(deleteButton);
        buttons.add(backButton);

        // Table
        model = new DefaultTableModel();

        model.addColumn("OrderItemID");
        model.addColumn("Item Name");
        model.addColumn("Quantity");
        model.addColumn("Total");

        table = new JTable(model);

        JScrollPane scroll = new JScrollPane(table);

        add(form, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        // Load data into table
        loadTable();

        // Button Actions
        addButton.addActionListener(e ->
        {
            try {
                int orderID = Integer.parseInt(orderIDTextField.getText());
                int itemID = Integer.parseInt(itemIDTextField.getText());
                int quantity = Integer.parseInt(quantityTextField.getText());
                double total = Double.parseDouble(totalTextField.getText());

                orderItemDAO.createOrderItem(orderID, itemID, quantity, total);

                loadTable();

                JOptionPane.showMessageDialog(this, "Order Item Added Successfully!");
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        updateButton.addActionListener(e ->
        {
            try {
                int row = table.getSelectedRow();

                int id = Integer.parseInt(model.getValueAt(row, 0).toString());

                orderItemDAO.updateOrderItem(id, Integer.parseInt(quantityTextField.getText()), Double.parseDouble(totalTextField.getText())
                );

                JOptionPane.showMessageDialog(this, "Order Item updated Successfully!");

                loadTable();

            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        deleteButton.addActionListener(e ->
        {
            try {
                int row = table.getSelectedRow();

                int id = Integer.parseInt(model.getValueAt(row, 0).toString());

                JOptionPane.showMessageDialog(null, "Order Item Deleted Successfully!");

                orderItemDAO.deleteOrderItem(id);
                loadTable();

            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        backButton.addActionListener(e ->
        {
            new MainMenuGUI();
            dispose();
        });

        table.getSelectionModel().addListSelectionListener(e ->
        {
            int row = table.getSelectedRow();

            if(row != -1)
            {
                quantityTextField.setText(model.getValueAt(row, 2).toString());
                totalTextField.setText(model.getValueAt(row, 3).toString());
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void loadTable()
    {
        try
        {
            model.setRowCount(0);

            for(String s : orderItemDAO.getOrderItemsWithNames())
                model.addRow(s.split(" \\| "));
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
