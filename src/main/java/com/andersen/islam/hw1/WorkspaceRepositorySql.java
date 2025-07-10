package com.andersen.islam.hw1;

import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkspaceRepositorySql {

    Connection dbConnection;

    public WorkspaceRepositorySql() {
        try {
            String dbUrl = "jdbc:postgresql://localhost:5432/Customers";
            String dbUser = "postgres";
            String dbPassword = "1234";
            this.dbConnection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            System.err.println("Can't connect to DB. Cause: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }

    protected void addWorkspace(Workspace workspace) {

        int id = workspace.id;
        String type = workspace.type;
        BigDecimal price = workspace.price;
        boolean available = workspace.available;

        String sql = "INSERT INTO workspace (ws_id, ws_type, ws_price, ws_available) " +
                "SELECT ?, ?, ?, ? WHERE NOT EXISTS (SELECT 1 FROM workspace WHERE ws_id = ?)";

        try (PreparedStatement addWorkspace = dbConnection.prepareStatement(sql)) {
            addWorkspace.setInt(1, id);
            addWorkspace.setString(2, type);
            addWorkspace.setBigDecimal(3, price);
            addWorkspace.setBoolean(4, available);
            addWorkspace.setInt(5, id);

            int rowsAffected = addWorkspace.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);

            if (!dbConnection.getAutoCommit()) {
                dbConnection.commit();
            }

            if (rowsAffected > 0) {
                System.out.println("Workspace added");
            } else {
                System.out.println("Workspace with id=" + id + " already exists, not added");
            }

        } catch (SQLException e) {
            System.err.println("Can't execute SQL command. Cause: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    protected void deleteWorkspaceById(int id) {
        String sql = "DELETE FROM workspace WHERE id = ?";

        try (PreparedStatement deleteWorkspace = dbConnection.prepareStatement(sql)) {
            deleteWorkspace.setInt(1, id);
            deleteWorkspace.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Can't execute SQL command. Cause: " + e.getMessage());
            throw new RuntimeException(e);

        }
    }

    protected void showAvailableWorkspaces() {
        String sql = "SELECT * FROM workspace WHERE ws_available = true";

        try (PreparedStatement showWorkspace = dbConnection.prepareStatement(sql);
             ResultSet resultSet = showWorkspace.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("ws_id");
                    String type = resultSet.getString("ws_type");
                    BigDecimal price = resultSet.getBigDecimal("ws_price");
                    boolean available = resultSet.getBoolean("ws_available");

                    System.out.println("ID: " + id + ", Type: " + type +
                            ", Price: $" + price + ", Available: " + available);
                }
            } catch (SQLException e) {
            System.err.println("Can't execute SQL command. Cause: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected Workspace getWorkspaceById(int id) {
        String sql = "SELECT * FROM workspace WHERE id=?";

        try (PreparedStatement getWorkspace = dbConnection.prepareStatement(sql);
             ResultSet resultSet = getWorkspace.executeQuery()) {

            if (resultSet.next()) {
                String type = resultSet.getString("ws_type");
                BigDecimal price = resultSet.getBigDecimal("ws_price");
                boolean available = resultSet.getBoolean("ws_available");

                return new Workspace(id, type, price, available);
            } else {
                System.out.println("There isn't a workspace with this ID. Please try again.");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Can't execute SQL command. Cause: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected List<Workspace> getAllWorkspaces() {
        String sql = "SELECT * FROM workspace";
        List<Workspace> workspaces = new ArrayList<>();

        try (PreparedStatement getWorkspace = dbConnection.prepareStatement(sql);
             ResultSet resultSet = getWorkspace.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String type = resultSet.getString("ws_type");
                BigDecimal price = resultSet.getBigDecimal("ws_price");
                boolean available = resultSet.getBoolean("ws_available");

                workspaces.add(new Workspace(id, type, price, available));
            }

            return workspaces;

        } catch (SQLException e) {
            System.err.println("Can't execute SQL command. Cause: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    protected void setAvailability(int id, boolean available) {
        String sql = "UPDATE workspace SET ws_available = ? WHERE id = ?";

        try (PreparedStatement changeAvailabilty = dbConnection.prepareStatement(sql)) {
            changeAvailabilty.setBoolean(1, available);
            changeAvailabilty.setInt(2, id);

            int rowsAffected = changeAvailabilty.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Workspace availability updated.");
            } else {
                System.out.println("Workspace not found.");
            }

        } catch (SQLException e) {
            System.err.println("Can't execute SQL command. Cause: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected int getNextWorkspaceId() {
        String sql = "SELECT MAX(ws_id) AS max_id FROM workspace";

        try (Statement stmt = dbConnection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                int maxId = rs.getInt("max_id");
                return maxId + 1;
            } else {
                return 1;
            }

        } catch (SQLException e) {
            System.err.println("Error fetching max ws_id: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
