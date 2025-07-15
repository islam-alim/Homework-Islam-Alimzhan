package com.andersen.islam.hw1;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class WorkspaceService {

    List<Workspace> workspaces = new ArrayList<>();

    WorkspaceRepositoryJpa workspaceRepositoryJpa = new WorkspaceRepositoryJpa();



    Scanner scanner = new Scanner(System.in);


    void addWorkspace(String type, double price, boolean available) {
        workspaceRepositoryJpa.addWorkspace(type, BigDecimal.valueOf(price), available);
    }

    void addWorkspace() {
        boolean available = true;
        System.out.print("Enter workspace type: ");
        String type = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        addWorkspace(type, price, available);
    }

    boolean removeWorkspaceById(int id) {
        workspaceRepositoryJpa.deleteWorkspaceById(id);
        return true;
    }

    void removeWorkspace() {
        System.out.print("Enter workspace ID to remove: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (removeWorkspaceById(id)) {
            System.out.println("Workspace removed");
        } else {
            System.out.println("Workspace not found.");
        }
    }

    void showAvailableWorkspaces() {
        workspaceRepositoryJpa.showAvailableWorkspaces();
    }

    List<Workspace> getAllWorkspaces() {
        return workspaceRepositoryJpa.getAllWorkspaces();
    }

    Workspace getWorkspaceById(int id) throws WorkspaceNotFoundException {
        return workspaceRepositoryJpa.getWorkspaceById(id);
    }

    void setAvailability(int id, boolean available) {
        workspaceRepositoryJpa.setAvailability(id, available);
    }

    void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("workspaces.txt"))) {
            for (Workspace ws : workspaces) {
                writer.write(ws.id + ", " + ws.type + ", " + ws.price + ", " + ws.available);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving workspaces to file.");
        }
    }

    void loadFromFile() {
        int nextId = 1;
        try (BufferedReader reader = new BufferedReader(new FileReader("workspaces.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Workspace ws = Workspace.fromString(line);
                workspaces.add(ws);
                nextId = Math.max(nextId, ws.id) + 1;

            }
        } catch (IOException e) {
            System.out.println("No saved workspaces found. Starting fresh.");
        }
    }


}
