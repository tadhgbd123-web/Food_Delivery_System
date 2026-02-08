package org.example;

public class Main {

    public static void main(String[] args) {

        OrdersDAO orderDao = new OrdersDAO();

        try {
            // CREATE ORDER (John Doe is ID 7)
            System.out.println("Creating order...");
            orderDao.createOrder(7, "PENDING", 25.99);
            System.out.println("Order created!");

            // READ
            System.out.println("\nAll orders:");
            for (String order : orderDao.getAllOrders()) {
                System.out.println(order);
            }

            // UPDATE
            System.out.println("\nUpdating order status...");
            orderDao.updateOrderStatus(1, "DELIVERED");
            System.out.println("Order updated!");

            // READ AGAIN
            System.out.println("\nAll orders after update:");
            for (String order : orderDao.getAllOrders()) {
                System.out.println(order);
            }

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

