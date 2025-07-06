package com.andersen.islam.hw1;

import java.sql.*;
import java.util.Optional;

public class CustomerRepositorySql {

    Connection dbConnection;

    public CustomerRepositorySql() {
        try {
            String dbUrl = "jdbc:postgresql://localhost:5432/Customers";
            String dbUser = "postgres";
            String dbPassword = "1234";
            this.dbConnection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            System.err.println("Can't connect to DB. Cause: " + e.getMessage());
            throw new RuntimeException(e);
        }

        fillCustomer();
    }

    private void fillCustomer() {
        try (PreparedStatement addCustomer = dbConnection.prepareStatement("INSERT INTO customer (id, customer_name) " +
                "SELECT ?, ? WHERE NOT EXISTS (SELECT 1 FROM customer WHERE id = ?)")) {
            addCustomer.setInt(1, 1);
            addCustomer.setString(2, "John Doe");
            addCustomer.setInt(3, 1);
            addCustomer.executeUpdate();


            addCustomer.setInt(1, 2);
            addCustomer.setString(2, "Michael Smith");
            addCustomer.setInt(3, 2);
            addCustomer.executeUpdate();

            System.out.println("Customers added");

        } catch (SQLException e) {
            System.err.println("Can't execute SQL command. Cause: " + e.getMessage());
            throw new RuntimeException(e);
        }
    } Optional<String> getNameById(int customerId) {
        String sqlRequest = "SELECT * FROM customer WHERE id=";
        try (Statement statement = dbConnection.createStatement()) {


            ResultSet resultSet = statement.executeQuery(sqlRequest + customerId);
            if (resultSet.next()) {
                return Optional.of(resultSet.getString("customer_name"));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            System.err.println("Can't execute SQL command. Cause: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void addUser(String username) throws SQLException {
        String query = "INSERT INTO customer (customer_name) VALUES ('" + username + "')";
        Statement statement = dbConnection.createStatement();
        statement.execute(query);
    }
}
