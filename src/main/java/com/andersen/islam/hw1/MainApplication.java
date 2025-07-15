package com.andersen.islam.hw1;

import com.andersen.islam.hw1.loader.PluginClassLoader;

import java.util.Scanner;

public class MainApplication {
    public static void main(String[] args) {

        testPlugin();

        Scanner scanner = new Scanner(System.in);
        WorkspaceService workspaceService = new WorkspaceService();
        ReservationService reservationService = new ReservationService(workspaceService);
        CustomerRepositoryJpa customerRepositoryJpa = new CustomerRepositoryJpa();


        workspaceService.loadFromFile();

        while (true) {
            System.out.println("\nWelcome to the Coworking Space Reservation System");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                Admin admin = new Admin(workspaceService, reservationService);
                admin.showMenu();
            } else if (choice == 2) {
                Customer customer = new Customer(workspaceService, reservationService);

                System.out.print("Enter your name: ");
                String name = scanner.nextLine();

                int new_or_not;
                System.out.println("Are you new in this application?" +
                        "\n 1. YES \n 2. NO");
                new_or_not = scanner.nextInt();

                if (new_or_not == 1) {
                    customerRepositoryJpa.addCustomer(name);
                } else if (new_or_not == 2) {
                    System.out.println("Great, let's go!");
                }

                customer.showMenu();

            } else if (choice == 3) {
                workspaceService.saveToFile();
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    public static void testPlugin() {
        try {
            PluginClassLoader loader = new PluginClassLoader("plugins");
            Class<?> clazz = loader.findClass("SpecialOffer");
            Object instance = clazz.getDeclaredConstructor().newInstance();
            clazz.getMethod("applyOffer").invoke(instance);
        } catch (Exception e) {
            System.out.println("Plugin failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
