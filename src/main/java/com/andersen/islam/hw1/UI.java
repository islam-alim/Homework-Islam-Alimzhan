package com.andersen.islam.hw1;

import java.util.Optional;
import java.util.Scanner;

//public class UI {
//    private final IdentifyService identifyService = new IdentifyService(new CustomerRepositorySql());
//
//    public void displayUi() {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("\n");
//        System.out.println("Good morning. Input your customer ID, please: " );
//        int customerId = scanner.nextInt();
//
//        Optional<String> customerName = identifyService.getName(customerId);
//        customerName.ifPresentOrElse(
//                name -> System.out.println("You are: " + name),
//                () -> System.out.println("I don't know you!")
//        );
//    }
//}
