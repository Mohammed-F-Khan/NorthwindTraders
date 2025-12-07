package com.pluralsight;

import java.sql.*;

public class App {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        if (args.length !=2) {
            System.out.println("Usage: java App <username> <password>");
            System.exit(1);
        }

        String username = args [0];
        String password = args[1];

        // Loads MySQL Driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Opens a connection
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/northwind",
                username,
                password);

        // creates a prepared statement
        PreparedStatement statement = connection.prepareStatement(
                "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products"
        );

        // executes query
        ResultSet results = statement.executeQuery();

        // Loop throughs the results
        while (results.next()) {
            // gets each value from the current row
            int id = results.getInt("ProductID");
            String name = results.getString("ProductName");
            double price = results.getDouble("UnitPrice");
            int stock = results.getInt("UnitsInStock");

            // prints the product information to the screen
            System.out.println("Product Id: " + id);
            System.out.println("Product Name: " + name);
            System.out.println("Product Price: " + price);
            System.out.println("Product Stock: " + stock);
            System.out.println("----------------------------");
        }

        // close everything
        results.close();
        statement.close();
        connection.close();
    }
}