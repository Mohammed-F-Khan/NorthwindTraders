package com.pluralsight;

import java.sql.*;
import java.util.Scanner; // to read user's choice from keyboard

public class App {
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Usage: java App <username> <password>");
            System.exit(1);
        }

        String username = args[0];
        String password = args[1];

        // Scanner
        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        // Simple home screen loop
        while (running) {
            System.out.println();
            System.out.println("What do you want to do?");
            System.out.println("  1) Display all products");
            System.out.println("  2) Display all customers");
            System.out.println("  0) Exit");
            System.out.print("Select an option: ");

            String choice = scanner.next(); // reads what the user typed

            if (choice.equals("1")) {
                displayAllProducts(username, password);
            } else if (choice.equals("2")) {
                displayAllCustomers(username, password);
            } else if (choice.equals("0")) {
                running = false; // end the loop
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    // option 1 all products

private static void displayAllProducts(String username, String password) {
    // declare jdbc objects outside try, so we can close tehm in finally
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet results = null;

    try {
        // load my SQL JDBC Driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // opens connection to northwind database
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/northwind",
                username,
                password
        );

        // SQL: get id, name, price, stock from products table
        String sql = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM products";

        // Create prepared statement
        statement = connection.prepareStatement(sql);

        // Run query
        results = statement.executeQuery();

        // Loop through each row and print stacked info
        System.out.println();
        System.out.println("=== All Products ===");
        while (results.next()) {
            int id = results.getInt("ProductID");
            String name = results.getString("ProductName");
            double price = results.getDouble("UnitPrice");
            int stock = results.getInt("UnitsInStock");

            System.out.println("Product Id: " + id);
            System.out.println("Name: " + name);
            System.out.println("Price: " + price);
            System.out.println("Stock: " + stock);
            System.out.println("-------------------------");
        }
    }

    catch (SQLException e) {
        // Any database error (wrong SQL, connection issues, etc.)
        e.printStackTrace();
    }
    catch (ClassNotFoundException e) {
        // Driver class not found
        e.printStackTrace();
    }

    finally {
        // Close resources in REVERSE order, with null checks,
        // just like the workbook's try/catch/finally example.

        if (results != null) {
            try {
                results.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
    // ==========================
    //  OPTION 2: ALL CUSTOMERS
    // ==========================
    private static void displayAllCustomers(String username, String password) {

        // Again, declare JDBC objects outside try
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open connection to northwind database
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/northwind",
                    username,
                    password
            );

            // We want:
            // - contact name (ContactName)
            // - company name (CompanyName)
            // - city (City)
            // - country (Country)
            // - phone number (Phone)
            // ordered by country
            String sql =
                    "SELECT ContactName, CompanyName, City, Country, Phone " +
                            "FROM customers " +
                            "ORDER BY Country";

            // Create prepared statement
            statement = connection.prepareStatement(sql);

            // Run query
            results = statement.executeQuery();

            // Loop through customers and print stacked info
            System.out.println();
            System.out.println("=== All Customers ===");
            while (results.next()) {
                String contactName = results.getString("ContactName");
                String companyName = results.getString("CompanyName");
                String city = results.getString("City");
                String country = results.getString("Country");
                String phone = results.getString("Phone");

                System.out.println("Contact: " + contactName);
                System.out.println("Company: " + companyName);
                System.out.println("City: " + city);
                System.out.println("Country: " + country);
                System.out.println("Phone: " + phone);
                System.out.println("-------------------------");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            // Close in reverse order with safety checks
            if (results != null) {
                try {
                    results.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}