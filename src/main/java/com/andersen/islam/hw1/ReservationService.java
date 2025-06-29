package com.andersen.islam.hw1;

import com.andersen.islam.hw1.util.Storage;

import java.util.Scanner;

public class ReservationService {
    Storage<Reservation> reservations = new Storage<>();
    int nextId = 1;
    WorkspaceService workspaceService;
    Scanner scanner = new Scanner(System.in);

    ReservationService(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    void makeReservation(String name) {
        workspaceService.showAvailableWorkspaces();
        System.out.print("Enter workspace ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        try {
            Workspace ws = workspaceService.getWorkspaceById(id);
            if (ws == null) {
                System.out.println("Invalid ID or workspace not available.");
                return;
            }
            System.out.print("Enter date: ");
            String date = scanner.nextLine();
            System.out.print("Enter start time: ");
            String start = scanner.nextLine();
            System.out.print("Enter end time: ");
            String end = scanner.nextLine();

            Reservation res = new Reservation(nextId, name, date, start, end, id);
            reservations.add(res);
            nextId++;
            workspaceService.setAvailability(id, false);
            System.out.println("Reservation successful!");
        } catch (WorkspaceNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    void viewReservationsByName(String name) {
        reservations.getAll().stream()
                .filter(res -> res.name.equalsIgnoreCase(name))
                .forEach(System.out::println);
    }

    void cancelReservation(String name) {
        viewReservationsByName(name);
        System.out.print("Enter reservation ID to cancel: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Reservation res : reservations) {
            if (res.id == id && res.name.equalsIgnoreCase(name)) {
                reservations.remove(res);
                workspaceService.setAvailability(res.workspaceId, true);
                System.out.println("Reservation cancelled.");
                return;
            }
        }
        System.out.println("Reservation not found.");
    }

    void viewAllReservations() {
        for (Reservation res : reservations) {
            System.out.println(res);
        }
    }
}
