package org.example;

import javax.swing.*;
import java.awt.*;
import org.example.CustomerDAO;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;


public class RestaurantGUI extends JFrame
{
    // Declare GUI components
    JLabel nameLabel, addressLabel, phoneLabel;
    JTextField nameField, addressField, phoneField;

    // Declare buttons for adding, deleting, and updating customers
    JButton addButton;
    JButton deleteButton;
    JButton updateButton;
    JButton backButton;

    // Declare a table to display customers and a table model to manage the data in the table
    JTable restaurantTable;
    DefaultTableModel tableModel;

    // Create an instance of RestaurantDAO in order for the form to interact with the DB
    RestaurantDAO dao = new RestaurantDAO();

    // Constructor to set up the GUI components and event handling
    public RestaurantGUI()
    {
        // Set the title of the window
        setTitle("Restaurant Management");

        // Set the size of the window
        setSize(700, 600);

        // Set the default close operation
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set the layout manager
        setLayout(new BorderLayout());

        // Initialize components
        nameLabel = new JLabel("Name:");    // Create a label for the restaurant name, phone, and address
        addressLabel = new JLabel("Address:");
        phoneLabel = new JLabel("Phone:");


        nameField = new JTextField(20);     // Create a text field for entering the restaurant name, phone and address, with a width of 20 characters
        addressField = new JTextField(20);
        phoneField = new JTextField(20);


        // Create buttons for adding, deleting, and updating Restaurant
        addButton = new JButton("Add Restaurant");
        deleteButton = new JButton("Delete Restaurant");
        updateButton = new JButton("Update Restaurant");
        backButton = new JButton("Back to Main Menu");

        // adds an action listener to the 'add' button, allowing it to add a new customer when clicked
        addButton.addActionListener(e ->
        {

            if (nameField.getText().trim().isEmpty() ||
                    addressField.getText().trim().isEmpty() ||
                    phoneField.getText().trim().isEmpty())
            {
                JOptionPane.showMessageDialog(this, "All fields must be filled!");
                return;
            }

            // Get the input values from the text fields
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();
            String phone = phoneField.getText().trim();

            // Check if phone number matches the specified format using a regular expression. If it does not match, display an error message and return.
            // allows brackets, followed by 3 digit, followed by an optional space, followed by 7 digits
            if (!phone.matches("\\(?\\d{3}\\)?\\s?\\d{7}"))
            {
                JOptionPane.showMessageDialog(this, "Invalid phone number format!");
                return;
            }

            try
            {
                // Calls the createCustomer Method from the DAO to add a new customer to the DB
                dao.createRestaurant(name, address, phone);

                // displays when a customer is successfully added to the DB
                JOptionPane.showMessageDialog(this, "Restaurant added successfully!");

                // Clear the input fields after a Restaurant is added
                nameField.setText("");
                addressField.setText("");
                phoneField.setText("");


                // Refresh the table and shows the updated list of customers
                loadRestaurantTable();
            }
            // Catch any exceptions that may occur during the process and displays an error message if an error is caught
            catch (Exception ex)
            {
                // Displays an error message if there is an issue
                JOptionPane.showMessageDialog(this, "Error adding Restaurant: " + ex.getMessage());
            }
        });

        // Add action listener for the delete button to delete the selected customer from the database and refresh the table
        deleteButton.addActionListener(e ->
        {
            try
            {
                int selectedRow = restaurantTable.getSelectedRow();   // Get the index of the selected row in the table
                // Check if a row is selected, if not, display a message and return
                if (selectedRow == -1)
                {
                    // If no row is selected, show a message to the user and exit the method
                    JOptionPane.showMessageDialog(this, "Please select a restaurant to delete.");
                    return;
                }

                int restaurantID = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());   //Get the restaurant ID from table.

                dao.deleteRestaurant(restaurantID); // Call the deleteRestaurant method in the DAO to delete the customer from the database

                JOptionPane.showMessageDialog(this, "Restaurant deleted successfully!");

                loadRestaurantTable();    // Refresh the table to show the updated list of customers after being deleted

                //
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(this, "Error deleting restaurant: " + ex.getMessage());
            }
        });

        // Set up the layout and add components to the frame
        // Create a panel for the form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2));  // Set the layout of the form panel to a grid with 4 rows and 2 columns

        // Add labels and text fields to the form panel
        formPanel.add(nameLabel);
        formPanel.add(nameField);

        formPanel.add(addressLabel);
        formPanel.add(addressField);

        formPanel.add(phoneLabel);
        formPanel.add(phoneField);

        // Create a panel for the buttons and add the buttons to it
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setPreferredSize(new Dimension(600, 50));

        // Add buttons to the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(backButton);


        // Set up the table to display customers
        tableModel = new DefaultTableModel();
        // Add columns to the table.
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Address");

        tableModel.addColumn("Phone");

        // Create a JTable with the table model
        restaurantTable = new JTable(tableModel);

        // Add a selection listener to the table to populate the form fields when a customer is selected
        restaurantTable.getSelectionModel().addListSelectionListener(e ->
        {
            // Get the index of the selected row in the table
            int selectedRow = restaurantTable.getSelectedRow();
            // Checks if a row is selected and if it is , it is populated with the appropriate customer info
            if (selectedRow != -1)
            {
                nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                addressField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                phoneField.setText(tableModel.getValueAt(selectedRow, 3).toString());
            }
        });

        // Add action listener for the update button to update the selected customer's info
        updateButton.addActionListener(e ->
        {
            if (nameField.getText().trim().isEmpty() ||
                    addressField.getText().trim().isEmpty() ||
                    phoneField.getText().trim().isEmpty())
            {
                JOptionPane.showMessageDialog(this, "All fields must be filled!");
                return;
            }

            try
            {
                // Get the index of the selected row in the table
                int selectedRow = restaurantTable.getSelectedRow();
                // Check if a row is selected, if not, display a message and return
                if (selectedRow == -1)
                {
                    JOptionPane.showMessageDialog(this, "Please select a restaurant to update.");
                    return;
                }

                String name = nameField.getText().trim();
                String address = addressField.getText().trim();
                String phone = phoneField.getText().trim();

                // Check if phone number matches the specified format using a regular expression. If it does not match, display an error message and return.
                // allows brackets, followed by 3 digit, followed by an optional space, followed by 7 digits
                if (!phone.matches("\\(?\\d{3}\\)?\\s?\\d{7}"))
                {
                    JOptionPane.showMessageDialog(this, "Invalid phone number format!");
                    return;
                }

                int restaurantID = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());


                dao.updateRestaurant(restaurantID, name, address, phone);

                JOptionPane.showMessageDialog(this, "Restaurant updated successfully!");

                // Clear the input fields after a Restaurant is added
                nameField.setText("");
                addressField.setText("");
                phoneField.setText("");

                loadRestaurantTable();
            }

            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(this, "Error updating restaurant: " + ex.getMessage());
            }
        });

        // Add action listener for the back button to return to the main menu
        backButton.addActionListener(e ->
        {
            new MainMenuGUI();
            dispose();
        });

        // Create a scroll pane for the table and add the table to it
        JScrollPane scrollPane = new JScrollPane(restaurantTable);

        // Create a panel for the table and add the scroll pane to it
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Load customers into the table
        loadRestaurantTable();

        // Add panels to the frame
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(tablePanel, BorderLayout.CENTER);

        // Center the window on the screen
        setLocationRelativeTo(null);

        // Add action listener to the button
        setVisible(true);
    }

    // Method to load customers from the DB and display them in the table
    public void loadRestaurantTable()
    {

        try
        {
            // Clear the existing rows in the table model before loading new data
            tableModel.setRowCount(0);

            // Get the list of restaurant from the DAO and add them to the table model
            for (String restaurant : dao.getAllRestaurants())
            {
                String[] data = restaurant.split(" \\| ");
                tableModel.addRow(data);
            }
        }
        // Catch any errors and displays error message if found
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(this, "Error loading restaurants: " + ex.getMessage());
        }
    }
}
