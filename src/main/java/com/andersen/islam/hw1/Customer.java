package com.andersen.islam.hw1;

import java.util.Scanner;

public class Customer {
    WorkspaceService workspaceService;
    ReservationService reservationService;
    Scanner scanner = new Scanner(System.in);

    Customer(WorkspaceService workspaceService, ReservationService reservationService) {
        this.workspaceService = workspaceService;
        this.reservationService = reservationService;
    }

    void showMenu() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        int choice;
        do {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. Browse available spaces");
            System.out.println("2. Make a reservation");
            System.out.println("3. View my reservations");
            System.out.println("4. Cancel a reservation");
            System.out.println("5. Back");
            System.out.print("Choose: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                workspaceService.showAvailableWorkspaces();
            } else if (choice == 2) {
                reservationService.makeReservation(name);
            } else if (choice == 3) {
                reservationService.viewReservationsByName(name);
            } else if (choice == 4) {
                reservationService.cancelReservation(name);
            }
        } while (choice != 5);
    }
}
