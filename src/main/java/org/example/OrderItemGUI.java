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
            //Check for empty fields
            if(orderIDTextField.getText().trim().isEmpty() ||
                    itemIDTextField.getText().trim().isEmpty() ||
                    quantityTextField.getText().trim().isEmpty() ||
                    totalTextField.getText().trim().isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            int orderID;
            int itemID;
            int quantity;
            double total;
            try
            {
                orderID = Integer.parseInt(orderIDTextField.getText());
                itemID = Integer.parseInt(itemIDTextField.getText());
                quantity = Integer.parseInt(quantityTextField.getText());
                total = Double.parseDouble(totalTextField.getText());
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                return;
            }

            // Check is quantity inputted is less than or equal to 0, out put message if it is
            if (quantity <= 0)
            {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than 0!");
                return;
            }

            // Check if total is less than 0 and outputs the message if it is
            if (total < 0)
            {
                JOptionPane.showMessageDialog(this, "Total must be greater than or equal to 0!");
                return;
            }

            try
            {
                // Call to the database
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

                // Check of row is selected
                if (row == -1)
                {
                    JOptionPane.showMessageDialog(this, "Please select an order item to update.");
                    return;
                }

                // Check if there is any empty fields
                if (quantityTextField.getText().trim().isEmpty() || totalTextField.getText().trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                    return;
                }

                int id = Integer.parseInt(model.getValueAt(row, 0).toString());


                int quantity;
                double total;

                try
                {
                    quantity = Integer.parseInt(quantityTextField.getText());
                    total = Double.parseDouble(totalTextField.getText());
                }
                catch (NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(this, "Please enter valid numbers for Quantity and Total.");
                    return;
                }

                // Validations
                // Validation for quantity
                if (quantity <= 0)
                {
                    JOptionPane.showMessageDialog(this, "Quantity must be greater than 0!");
                    return;
                }

                if (total < 0)
                {
                    JOptionPane.showMessageDialog(this, "Total must be greater than or equal to 0!");
                    return;
                }


                orderItemDAO.updateOrderItem(id, quantity, total);

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

                if (row == -1)
                {
                    JOptionPane.showMessageDialog(this, "Please select an order item to delete.");
                    return;
                }

                int id = Integer.parseInt(model.getValueAt(row, 0).toString());


                orderItemDAO.deleteOrderItem(id);

                JOptionPane.showMessageDialog(this, "Order Item Deleted Successfully!");

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
