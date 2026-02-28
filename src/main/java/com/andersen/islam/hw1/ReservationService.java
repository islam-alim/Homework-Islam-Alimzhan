package com.andersen.islam.hw1;

import java.sql.Timestamp;
import java.util.Scanner;

public class ReservationService {

    ReservationRepositoryJpa reservationRepositoryJpa = new ReservationRepositoryJpa();

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
            System.out.print("Enter start time (yyyy-MM-dd HH:mm:ss): ");
            String start = scanner.nextLine();
            System.out.print("Enter end time (yyyy-MM-dd HH:mm:ss): ");
            String end = scanner.nextLine();

            Reservation res = new Reservation(nextId, name, Timestamp.valueOf(start), Timestamp.valueOf(end), id);
            reservationRepositoryJpa.addReservation(res);
            nextId++;
            workspaceService.setAvailability(id, false);
            System.out.println("Reservation successful!");
        } catch (WorkspaceNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    void viewReservationsByName(String name) {
        reservationRepositoryJpa.viewReservationsByName(name);
    }

    void cancelReservation(String name) {
        reservationRepositoryJpa.cancelReservation(name);
    }

    void viewAllReservations() {
        reservationRepositoryJpa.showAllReservations();
    }
}
