package com.example;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Scanner;

public class Logic {

    private static final Scanner scanner = new Scanner(System.in);
    private static UserRepository userRepo;

    public static void initialize(DataSource dataSource) {
        userRepo = new UserRepositoryImpl(dataSource);
    }

    public static boolean login(int maxAttempts) {
        int attempts = 0;
        boolean loggedIn = false;
        while(attempts < maxAttempts && !loggedIn) {
            System.out.println("Enter your username (or 0 to quit):");
            String username = scanner.nextLine();
            if (username.equals("0")) {
                System.out.println("Exiting program...");
                return loggedIn;
            }
            System.out.println("Enter your password:");
            String password = scanner.nextLine();
            if(userRepo.validateCredentials(username, password)) {
                System.out.println("Welcome " + username);
                loggedIn = true;
            } else {
                System.out.println("Invalid username or password. Enter 0 to exit");
            }
            attempts++;
        }
        return loggedIn;
    }

    public static void menu() {

        while(true) {
            printMenu();
            if(scanner.hasNextInt()) {
                int userInput = scanner.nextInt();
                switch (userInput) {
                    case 0 -> {System.out.println("Exiting program..."); return;}
                    case 1 -> System.out.println("Listing missions");
                    case 2 -> System.out.println("Gets mission by id");
                    case 3 -> System.out.println("Count missions by year");
                    case 4 -> System.out.println("Creates account");
                    case 5 -> System.out.println("Updates password");
                    case 6 -> System.out.println("Deletes account");
                    default -> System.out.println("Invalid input. Enter 0 to exit");
                }
            } else {
                System.out.println("Invalid menu option!");
                scanner.nextLine();
            }
        }
    }

    public static void printMenu() {
        System.out.println(
                """
                        1) List moon missions (prints spacecraft names from `moon_mission`).
                        2) Get a moon mission by mission_id (prints details for that mission).
                        3) Count missions for a given year (prompts: year; prints the number of missions launched that year).
                        4) Create an account (prompts: first name, last name, ssn, password; prints confirmation).
                        5) Update an account password (prompts: user_id, new password; prints confirmation).
                        6) Delete an account (prompts: user_id; prints confirmation).
                        0) Exit.
                """
        );
    }
}
