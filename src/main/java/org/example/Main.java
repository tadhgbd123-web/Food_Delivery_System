package org.example;


public class Main
{
    public static void main(String[] args) throws Exception
    {
        CustomerDAO dao = new CustomerDAO();


        dao.createCustomer("Tadhg Brennan", "tadhgbd123@gmail.com", "0838784566", "Patrick Street");

        for (String c : dao.getAllCustomers())
        {
            System.out.println(c);
        }

        dao.updateCustomerEmail(1, "updated@test.com");
        dao.deleteCustomer(2);


    }
}