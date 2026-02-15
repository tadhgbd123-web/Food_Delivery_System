package org.example;

import javax.swing.*;
import java.awt.*;
import org.example.CustomerDAO;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;


public class CustomerGUI extends JFrame
{
    // Declare GUI components
    JLabel nameLabel, emailLabel, phoneLabel, addressLabel;
    JTextField nameField, emailField, phoneField, addressField;

    // Declare buttons for adding, deleting, and updating customers
    JButton addButton;
    JButton deleteButton;
    JButton updateButton;

    // Declare a table to display customers and a table model to manage the data in the table
    JTable customerTable;
    DefaultTableModel tableModel;

    // Create an instance of CustomerDAO in order for the form to interact with the DB
    CustomerDAO dao = new CustomerDAO();

// Constructor to set up the GUI components and event handling
    public CustomerGUI()
    {
        // Set the title of the window
        setTitle("Customer Management");

        // Set the size of the window
        setSize(600, 600);

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the layout manager
        setLayout(new BorderLayout());

        // Initialize components
        nameLabel = new JLabel("Name:");    // Create a label for the customer name, email, phone, and address
        emailLabel = new JLabel("Email:");
        phoneLabel = new JLabel("Phone:");
        addressLabel = new JLabel("Address:");

        nameField = new JTextField(20);     // Create a text field for entering the customer name, email, phone and address, with a width of 20 characters
        emailField = new JTextField(20);
        phoneField = new JTextField(20);
        addressField = new JTextField(20);

        // Create buttons for adding, deleting, and updating customers
        addButton = new JButton("Add Customer");
        deleteButton = new JButton("Delete Customer");
        updateButton = new JButton("Update Customer");

        // adds an action listener to the 'add' button, allowing it to add a new customer when clicked
        addButton.addActionListener(e ->
        {
            try
            {
                // Get the input values from the text fields
                String name = nameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                String address = addressField.getText();

                // Calls the createCustomer Method from the DAO to add a new customer to the DB
                dao.createCustomer(name, email , phone, address);

                // displays when a customer is successfully added to the DB
                JOptionPane.showMessageDialog(this, "Customer added successfully!");

                // Clear the input fields after a customer is added
                nameField.setText("");
                emailField.setText("");
                phoneField.setText("");
                addressField.setText("");

                // Refresh the table and shows the updated list of customers
                loadCustomerTable();
            }
            // Catch any exceptions that may occur during the process and displays an error message if an error is caught
            catch (Exception ex)
            {
                // Displays an error message if there is an issue
                JOptionPane.showMessageDialog(this, "Error adding customer: " + ex.getMessage());
            }
        });

        // Add action listener for the delete button to delete the selected customer from the database and refresh the table
        deleteButton.addActionListener(e ->
        {
            try
            {
                int selectedRow = customerTable.getSelectedRow();   // Get the index of the selected row in the table
                // Check if a row is selected, if not, display a message and return
                if (selectedRow == -1)
                {
                    // If no row is selected, show a message to the user and exit the method
                    JOptionPane.showMessageDialog(this, "Plesase select a customer to delete.");
                    return;
                }

                int customerID = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());   //Get the Customer ID from table.

                dao.deleteCustomer(customerID); // Call the deleteCustomer method in the DAO to delete the customer from the database

                JOptionPane.showMessageDialog(this, "Customer deleted successfully!");

                loadCustomerTable();    // Refresh the table to show the updated list of customers after being deleted

                //
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(this, "Error deleting customer: " + ex.getMessage());
            }
        });

        // Set up the layout and add components to the frame
        // Create a panel for the form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2));  // Set the layout of the form panel to a grid with 4 rows and 2 columns

        // Add labels and text fields to the form panel
        formPanel.add(nameLabel);
        formPanel.add(nameField);

        formPanel.add(emailLabel);
        formPanel.add(emailField);

        formPanel.add(phoneLabel);
        formPanel.add(phoneField);

        formPanel.add(addressLabel);
        formPanel.add(addressField);

        // Create a panel for the buttons and add the buttons to it
        JPanel buttonPanel = new JPanel();

        // Add buttons to the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);


        // Set up the table to display customers
        tableModel = new DefaultTableModel();
        // Add columns to the table.
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Email");
        tableModel.addColumn("Phone");
        tableModel.addColumn("Address");

        // Create a JTable with the table model
        customerTable = new JTable(tableModel);

        // Add a selection listener to the table to populate the form fields when a customer is selected
        customerTable.getSelectionModel().addListSelectionListener(e ->
        {
            // Get the index of the selected row in the table
            int selectedRow = customerTable.getSelectedRow();
            // Checks if a row is selected and if it is , it is poplulated with the approprite customer info
            if (selectedRow != -1)
            {
                nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                emailField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                phoneField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                addressField.setText(tableModel.getValueAt(selectedRow, 4).toString());
            }
        });

        // Add action listener for the update button to update the selected customer's info
        updateButton.addActionListener(e ->
        {
            try
            {
                // Get the index of the selected row in the table
                int selectedRow = customerTable.getSelectedRow();
                // Check if a row is selected, if not, display a message and return
                if (selectedRow == -1)
                {
                    JOptionPane.showMessageDialog(this, "Please select a customer to update.");
                    return;
                }

                int customerID = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                String name = nameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                String address = addressField.getText();

                dao.updateCustomer(customerID, name, email, phone, address);

                JOptionPane.showMessageDialog(this, "Customer updated successfully!");

                loadCustomerTable();
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(this, "Error updating customer: " + ex.getMessage());
            }
        });

        // Create a scroll pane for the table and add the table to it
        JScrollPane scrollPane = new JScrollPane(customerTable);

        // Create a panel for the table and add the scroll pane to it
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Load customers into the table
        loadCustomerTable();

        // Add panels to the frame
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(tablePanel, BorderLayout.CENTER);


        // Add action listener to the button
        setVisible(true);
    }

    // Method to load customers from the DB and display them in the table
    public void loadCustomerTable()
    {

        try
        {
            // Clear the existing rows in the table model before loading new data
            tableModel.setRowCount(0);

            // Get the list of customers from the DAO and add them to the table model
            for (String customer : dao.getAllCustomers())
            {
                String[] data = customer.split(" \\| ");
                tableModel.addRow(data);
            }
        }
        // Catch any errors and displays error message if found
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(this, "Error loading customers: " + ex.getMessage());
        }
    }
}
