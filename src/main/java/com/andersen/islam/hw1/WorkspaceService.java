package com.andersen.islam.hw1;

import com.andersen.islam.hw1.util.Storage;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;


public class WorkspaceService {
    Storage<Workspace> workspaces = new Storage<>();
    int nextId = 1;
    Scanner scanner = new Scanner(System.in);


    void addWorkspace(String type, double price) {
        Workspace workspace = new Workspace(nextId, type, BigDecimal.valueOf(price));
        workspaces.add(workspace);
        nextId++;
    }

    void addWorkspace() {
        System.out.print("Enter workspace type: ");
        String type = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        addWorkspace(type, price);
        System.out.println("Workspace added.");
    }

    boolean removeWorkspaceById(int id) {
        for (Workspace ws : workspaces) {
            if (ws.id == id) {
                workspaces.remove(ws);
                return true;
            }
        }
        return false;
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
        for (Workspace ws : workspaces) {
            if (ws.available) {
                System.out.println(ws);
            }
        }
    }

    public ArrayList<Workspace> getWorkspaces() {
        return (ArrayList<Workspace>) workspaces.getAll();
    }

    Workspace getWorkspaceById(int id) throws WorkspaceNotFoundException {
        for (Workspace ws : workspaces) {
            if (ws.id == id && ws.available) {
                return ws;
            }
        }
        throw new WorkspaceNotFoundException("Workspace ID " + id + " not available.");
    }

    void setAvailability(int id, boolean available) {
        for (Workspace ws : workspaces) {
            if (ws.id == id) {
                ws.available = available;
            }
        }
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
