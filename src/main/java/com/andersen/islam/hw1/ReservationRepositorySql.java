package com.andersen.islam.hw1;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReservationRepositorySql {
    Scanner scanner = new Scanner(System.in);

    Connection dbConnection;

    public ReservationRepositorySql() {
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

    protected void addReservation(Reservation reservation) {

        int id = reservation.id;
        String name = reservation.name;
        Timestamp startTime = reservation.startTime;
        Timestamp endTime = reservation.endTime;
        int workspaceId = reservation.workspaceId;

        String sql = "INSERT INTO reservation (rv_id, rv_name, rv_start, rv_end, ws_id) " +
                "SELECT ?, ?, ?, ?, ? WHERE NOT EXISTS (SELECT 1 FROM reservation WHERE id = ?)";

        try (PreparedStatement addReservation = dbConnection.prepareStatement(sql)) {
            addReservation.setInt(1, id);
            addReservation.setString(2, name);
            addReservation.setTimestamp(3, startTime);
            addReservation.setTimestamp(4, endTime);
            addReservation.setInt(5, workspaceId);

            addReservation.setInt(6, id);

            addReservation.executeUpdate();

            System.out.println("Reservation added");

        } catch (SQLException e) {
            System.err.println("Can't execute SQL command. Cause: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected void cancelReservation(String name) {
        viewReservationsByName(name);
        System.out.println("Enter reservation ID to cancel: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        deleteReservationById(id);
    }

    protected void deleteReservationById(int id) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        try (PreparedStatement deleteReservation = dbConnection.prepareStatement(sql)) {
            deleteReservation.setInt(1, id);
            deleteReservation.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Can't execute SQL command. Cause: " + e.getMessage());
            throw new RuntimeException(e);

        }
    }

    protected void showAllReservations() {
        String sql = "SELECT * FROM reservation";

        try (PreparedStatement showReservation = dbConnection.prepareStatement(sql);
             ResultSet resultSet = showReservation.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String type = resultSet.getString("rv_name");
                    BigDecimal price = resultSet.getBigDecimal("rv_start");
                    boolean available = resultSet.getBoolean("ws_available");

                    System.out.println("ID: " + id + ", Type: " + type +
                            ", Price: $" + price + ", Available: " + available);
                }
            } catch (SQLException e) {
            System.err.println("Can't execute SQL command. Cause: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected Reservation getReservationById(int id) {
        String sql = "SELECT * FROM reservation WHERE id=?";

        try (PreparedStatement getWorkspace = dbConnection.prepareStatement(sql);
             ResultSet resultSet = getWorkspace.executeQuery()) {

            if (resultSet.next()) {
                String name = resultSet.getString("rv_name");
                Timestamp startTime = resultSet.getTimestamp("rv_start");
                Timestamp endTime = resultSet.getTimestamp("rv_end");
                int workspaceId = resultSet.getInt("ws_id");

                return new Reservation(id, name, startTime, endTime, workspaceId);
            } else {
                System.out.println("There isn't a reservation with this ID. Please try again.");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Can't execute SQL command. Cause: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected void viewReservationsByName(String name) {
        String sql = "SELECT * FROM reservation WHERE rv_name=?";

        try (PreparedStatement viewReservation = dbConnection.prepareStatement(sql)) {
            viewReservation.setString(1, name);

            try (ResultSet resultSet = viewReservation.executeQuery()) {
                boolean found = false;

                while (resultSet.next()) {
                    int id = resultSet.getInt("rv_id");
                    Timestamp startTime = resultSet.getTimestamp("rv_start");
                    Timestamp endTime = resultSet.getTimestamp("rv_end");
                    int workspaceId = resultSet.getInt("ws_id");

                    System.out.println("ID: " + id + ", Start time: " + startTime + ", End time: " + endTime + ", Workspace ID: " + workspaceId);
                    found = true;
                }

                if (!found) {
                    System.out.println("There aren't any reservations with this name. Please try again.");
                }
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
}
