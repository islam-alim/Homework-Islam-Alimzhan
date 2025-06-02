package com.andersen.islam.hw1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WorkspaceService {
    List<Workspace> workspaces = new ArrayList<>();
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

    Workspace getWorkspaceById(int id) {
        for (Workspace ws : workspaces) {
            if (ws.id == id && ws.available) {
                return ws;
            }
        }
        return null;
    }

    void setAvailability(int id, boolean available) {
        for (Workspace ws : workspaces) {
            if (ws.id == id) {
                ws.available = available;
            }
        }
    }
}
