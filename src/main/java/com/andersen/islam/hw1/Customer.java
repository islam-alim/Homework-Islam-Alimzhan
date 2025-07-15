package com.andersen.islam.hw1;

import jakarta.persistence.*;

import java.util.Scanner;


@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String customer_name;


    WorkspaceService workspaceService;
    ReservationService reservationService;
    Scanner scanner = new Scanner(System.in);

    public Customer() {
    }

    public Customer(WorkspaceService workspaceService, ReservationService reservationService) {
        this.workspaceService = workspaceService;
        this.reservationService = reservationService;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public WorkspaceService getWorkspaceService() {
        return workspaceService;
    }

    public void setWorkspaceService(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    public ReservationService getReservationService() {
        return reservationService;
    }

    public void setReservationService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    void showMenu() {


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
                reservationService.makeReservation(customer_name);
            } else if (choice == 3) {
                reservationService.viewReservationsByName(customer_name);
            } else if (choice == 4) {
                reservationService.cancelReservation(customer_name);
            }
        } while (choice != 5);
    }
}
