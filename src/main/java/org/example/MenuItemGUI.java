package org.example;

import javax.swing.*;
import java.awt.*;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class MenuItemGUI extends JFrame
{
    // Declare GUI components
    JLabel restaurantIDLabel, nameLabel, priceLabel, availableLabel;
    JTextField restaurantIDField, nameField, priceField;
    JCheckBox availableCheckBox;

    // Declare buttons for adding, deleting, and updating items
    JButton addButton;
    JButton deleteButton;
    JButton updateButton;
    JButton backButton;


    // Declare a table to display Menu Items and a table model to manage the data in the table
    JTable menuItemTable;
    DefaultTableModel tableModel;

    // Create an instance of MenuItemDAO in order for the form to interact with the DB
    MenuItemDAO dao = new MenuItemDAO();

    // Constructor to set up the GUI components and event handling
    public MenuItemGUI()
    {
        // Set the title of the window
        setTitle("Menu Item Management");

        // Set the size of the window
        setSize(700, 600);

        // Set the default close operation
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set the layout manager
        setLayout(new BorderLayout());

        // Initialize components
        restaurantIDLabel = new JLabel("Restaurant ID:");
        nameLabel = new JLabel("Name:");    // Create a label for the item restaurantID, name, price and availability
        priceLabel = new JLabel("Price:");
        availableLabel = new JLabel("Available:");

        restaurantIDField = new JTextField(20);     // Create a text field for entering the item restaurantID, name, price and availability, with a width of 20 characters
        nameField = new JTextField(20);
        priceField = new JTextField(20);

        // Create a checkbox for availability
        availableCheckBox = new JCheckBox();

        // Create buttons for adding, deleting, and updating customers
        addButton = new JButton("Add Menu Item");
        deleteButton = new JButton("Delete Menu Item");
        updateButton = new JButton("Update Menu Item");
        backButton = new JButton("Back to Main Menu");


        // adds an action listener to the 'add' button, allowing it to add a new item when clicked
        addButton.addActionListener(e ->
        {
            // Empty check
            if (restaurantIDField.getText().trim().isEmpty() ||
                    nameField.getText().trim().isEmpty() ||
                    priceField.getText().trim().isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Please enter valid fields for RestaurantID and Price, and ensure all fields are filled!");
                return;
            }

            String name = nameField.getText().trim();
            int restaurantID;
            double price;


            try {
                // Get the input values from the text fields
                restaurantID = Integer.parseInt(restaurantIDField.getText());
                price = Double.parseDouble(priceField.getText());
            }
            catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            if (price < 0)
            {
                JOptionPane.showMessageDialog(this, "Price cannot be negative!");
                return;
            }

            boolean availability = availableCheckBox.isSelected();
            try
            {
                // Calls the createMenuItems Method from the DAO to add a new customer to the DB
                dao.createMenuItem(restaurantID, name , price, availability);

                // displays when an item is successfully added to the DB
                JOptionPane.showMessageDialog(this, "Menu Item added successfully!");

                // Clear the input fields after a items is added
                restaurantIDField.setText("");
                nameField.setText("");
                priceField.setText("");
                availableCheckBox.setSelected(false);

                // Refresh the table and shows the updated list of items
                loadMenuItemTable();
            }
            // Catch any exceptions that may occur during the process and displays an error message if an error is caught
            catch (Exception ex)
            {
                // Displays an error message if there is an issue
                JOptionPane.showMessageDialog(this, "Error adding menu items: " + ex.getMessage());
            }
        });

        // Add action listener for the delete button to delete the selected Menu Item from the database and refresh the table
        deleteButton.addActionListener(e ->
        {
            try
            {
                int selectedRow = menuItemTable.getSelectedRow();   // Get the index of the selected row in the table
                // Check if a row is selected, if not, display a message and return
                if (selectedRow == -1)
                {
                    // If no row is selected, show a message to the user and exit the method
                    JOptionPane.showMessageDialog(this, "Please select an Item to delete.");
                    return;
                }

                int itemID = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());   //Get the Customer ID from table.

                dao.deleteMenuItem(itemID); // Call the deleteMenuItem method in the DAO to delete the Item from the database

                JOptionPane.showMessageDialog(this, "Menu Item deleted successfully!");

                loadMenuItemTable();    // Refresh the table to show the updated list of items after being deleted

                //
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(this, "Error deleting Item: " + ex.getMessage());
            }
        });

        // Set up the layout and add components to the frame
        // Create a panel for the form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2,5,5));  // Set the layout of the form panel to a grid with 4 rows and 2 columns

        // Add labels and text fields to the form panel
        formPanel.add(restaurantIDLabel);
        formPanel.add(restaurantIDField);

        formPanel.add(nameLabel);
        formPanel.add(nameField);

        formPanel.add(priceLabel);
        formPanel.add(priceField);

        formPanel.add(availableLabel);
        formPanel.add(availableCheckBox);

        // Create a panel for the buttons and add the buttons to it
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setPreferredSize(new Dimension(600, 60));

        // Add buttons to the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(backButton);


        // Set up the table to display Menu Items
        tableModel = new DefaultTableModel();
        // Add columns to the table.
        tableModel.addColumn("itemID");
        tableModel.addColumn("RestaurantID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Price");
        tableModel.addColumn("Available");

        // Create a JTable with the table model
        menuItemTable = new JTable(tableModel);

        // Add a selection listener to the table to populate the form fields when a item is selected
        menuItemTable.getSelectionModel().addListSelectionListener(e ->
        {
            // Get the index of the selected row in the table
            int selectedRow = menuItemTable.getSelectedRow();
            // Checks if a row is selected and if it is , it is populated with the appropriate items info
            if (selectedRow != -1)
            {
                restaurantIDField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                nameField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                priceField.setText(tableModel.getValueAt(selectedRow, 3).toString());

                availableCheckBox.setSelected(
                        tableModel.getValueAt(selectedRow, 4).toString().equals("1")
                );


            }
        });

        // Add action listener for the update button to update the selected Menu Item's info
        updateButton.addActionListener(e ->
        {
            // Get the index of the selected row in the table
            int selectedRow = menuItemTable.getSelectedRow();

            // Check if a row is selected, if not, display a message and return
            if (selectedRow == -1)
            {
                JOptionPane.showMessageDialog(this, "Please select an item to update.");
                return;
            }

            // Check if any fields were empty
            if (restaurantIDField.getText().trim().isEmpty() ||
                    nameField.getText().trim().isEmpty() ||
                    priceField.getText().trim().isEmpty())
            {
                JOptionPane.showMessageDialog(this, "All fields must be filled!");
                return;
            }

            int menuItemID = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            String name = nameField.getText().trim();

            int restaurantID;
            double price;

            try {

                restaurantID = Integer.parseInt(restaurantIDField.getText());
                price = Double.parseDouble(priceField.getText().replace("€", ""));
            }
            catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for Restaurant ID and Price.");
                return;
            }

            // Validation to ensure the price cannot be a negative number
            if (price < 0)
            {
                JOptionPane.showMessageDialog(this, "Price cannot be negative!");
                return;
            }

            boolean availability = availableCheckBox.isSelected();

            try
            {
                dao.updateMenuItem(menuItemID, restaurantID, name, price, availability);

                JOptionPane.showMessageDialog(this, "Item updated successfully!");

                loadMenuItemTable();
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(this, "Error updating Menu Item: " + ex.getMessage());
            }
        });

        // Button to return to main menu
        backButton.addActionListener(e ->
        {
            new MainMenuGUI();
            dispose();
        });

        // Create a scroll pane for the table and add the table to it
        JScrollPane scrollPane = new JScrollPane(menuItemTable);

        // Create a panel for the table and add the scroll pane to it
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Load Menu Items into the table
        loadMenuItemTable();

        // Add panels to the frame
        add(formPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);


        setLocationRelativeTo(null);

        // Add action listener to the button
        setVisible(true);
    }

    // Method to load customers from the DB and display them in the table
    public void loadMenuItemTable()
    {

        try
        {
            // Clear the existing rows in the table model before loading new data
            tableModel.setRowCount(0);

            // Get the list of customers from the DAO and add them to the table model
            for (String menuItem : dao.getAllMenuItems())
            {
                String[] data = menuItem.split(" \\| ");
                tableModel.addRow(data);
            }
        }
        // Catch any errors and displays error message if found
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(this, "Error loading menu items: " + ex.getMessage());
        }
    }
}
