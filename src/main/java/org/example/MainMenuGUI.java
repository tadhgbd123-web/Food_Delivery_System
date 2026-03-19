package org.example;

import javax.swing.*;
import java.awt.*;

public class MainMenuGUI extends JFrame
{
    JButton customerButton, ordersButton, menuItemButton, orderItemButton,exitButton;

    public MainMenuGUI()
    {
        setTitle("Food Delivery System - Main Menu");

        setSize(400, 300);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridLayout(4, 1, 10, 10));

        customerButton = new JButton("Customer Management");
        ordersButton = new JButton("Order Management");
        menuItemButton = new JButton("Menu Items Management");
        orderItemButton = new JButton("Order Item Management");
        exitButton = new JButton("Exit");

        add(customerButton);
        add(ordersButton);
        add(menuItemButton);
        add(orderItemButton);
        add(exitButton);

        customerButton.addActionListener(e ->
        {
            new CustomerGUI();
            dispose();
        });

        ordersButton.addActionListener(e ->
        {
            new OrdersGUI();
            dispose();
        });

        orderItemButton.addActionListener(e ->
        {
            new OrderItemGUI();
            dispose();
        });

        menuItemButton.addActionListener(e ->
        {
            new MenuItemGUI();
            dispose();
        });

        exitButton.addActionListener(e -> System.exit(0));

        // Center the window on the screen
        setLocationRelativeTo(null);

        // Make the window visible
        setVisible(true);
    }

}
