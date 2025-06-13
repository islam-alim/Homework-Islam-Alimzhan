package com.andersen.islam.hw1;

import com.andersen.islam.hw1.util.Storage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WorkspaceService {
    Storage<Workspace> workspaces = new Storage<>();
    int nextId = 1;
    Scanner scanner = new Scanner(System.in);

    void addWorkspace() {
        System.out.print("Enter workspace type: ");
        String type = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        Workspace workspace = new Workspace(nextId, type, price);
        workspaces.add(workspace);
        nextId++;
        System.out.println("Workspace added.");
    }

    void removeWorkspace() {
        System.out.print("Enter workspace ID to remove: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Workspace ws : workspaces) {
            if (ws.id == id) {
                workspaces.remove(ws);
                System.out.println("Workspace removed.");
                return;
            }
        }
        System.out.println("Workspace not found.");
    }

    void showAvailableWorkspaces() {
        for (Workspace ws : workspaces) {
            if (ws.available) {
                System.out.println(ws);
            }
        }
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
                writer.write(ws.toString());
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
                nextId = Math.max(nextId, ws.id + 1);
            }
        } catch (IOException e) {
            System.out.println("No saved workspaces found. Starting fresh.");
        }
    }
}
