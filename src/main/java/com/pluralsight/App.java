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
                "SELECT ProductName FROM Products"
        );

        // executes query
        ResultSet results = statement.executeQuery();

        // Loop throughs the results
        while (results.next()) {
            String name = results.getString("ProductName");
            System.out.println(name);
        }

        // close everything
        results.close();
        statement.close();
        connection.close();
    }
}