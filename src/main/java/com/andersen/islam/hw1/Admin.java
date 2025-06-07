package com.andersen.islam.hw1;

import java.util.Scanner;

public class Admin {
    WorkspaceService workspaceService;
    ReservationService reservationService;
    Scanner scanner = new Scanner(System.in);

    Admin(WorkspaceService workspaceService, ReservationService reservationService) {
        this.workspaceService = workspaceService;
        this.reservationService = reservationService;
    }

    void showMenu() {
        int choice;
        do {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add a new coworking space");
            System.out.println("2. Remove a coworking space");
            System.out.println("3. View all reservations");
            System.out.println("4. Back");
            System.out.print("Choose: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                workspaceService.addWorkspace();
            } else if (choice == 2) {
                workspaceService.removeWorkspace();
            } else if (choice == 3) {
                reservationService.viewAllReservations();
            }
        } while (choice != 4);
    }
}
