package com.example;

import java.util.Scanner;

/**
 * Handles user inputs that calls for the correct data from repositories within menu method
 * It handles login validation
 * Is injected with UserRepository, MoonMissionRepository and a scanner
 */
public class Logic {

    private final Scanner scanner;
    private final MenuMethods menu;

    /**
     *
     * @param scanner instance of scanner
     * @param menu instance of MenuMethods
     * @throws IllegalArgumentException if arguments are null
     */
    public Logic(Scanner scanner, MenuMethods menu) {
        if (scanner == null || menu == null) {
            throw new IllegalArgumentException("Scanner and MenuMethods must not be null");
        }
        this.scanner = scanner;
        this.menu = menu;
    }

    /**
     *
     * @param maxAttempts max amount of attempts before method returns false, tracked with counter
     * @return Returns true if login is successful. Returns false if max attempts are hit or 0 is entered
     */
    public boolean login(int maxAttempts) {
        int attempts = 0;
        boolean loggedIn = false;

        while (attempts < maxAttempts && !loggedIn) {
            System.out.println("Enter your username (or 0 to quit):");
            String username = scanner.nextLine();
            if(username.isBlank()){
                System.out.println("Invalid username, cannot be blank.");
                continue;
            }
            if ("0".equals(username)) {
                return loggedIn;
            }

            System.out.println("Enter your password: ");
            String password = scanner.nextLine();

            if(password.isBlank()){
                System.out.println("Invalid password, cannot be blank.");
                continue;
            }

            if (menu.validateCredentials(username, password)) {
                System.out.println("Welcome " + username);
                loggedIn = true;
            } else {
                System.out.println("Invalid username or password. Enter 0 to exit");
            }
            attempts++;
        }
        return loggedIn;
    }


    /**
     * Presents a menu for user with 7 options.
     * If 0 is entered, returns to main.
     * 1-6 calls instance methods of MenuMethodsImpl
     * Loops until 0 is entered
     */
    public void menu() {
        while(true) {
            menu.printMenu();
            int menuInput = menu.checkForInt();
            switch (menuInput) {
                case 0 -> {System.out.println("Exiting program..."); return;}
                case 1 -> menu.findAllMissions();
                case 2 -> menu.findMissionByID();
                case 3 -> menu.countMissionByYear();
                case 4 -> menu.createAccount();
                case 5 -> menu.updatePassword();
                case 6 -> menu.deleteAccount();
                default -> System.out.println("Invalid input.\nPlease enter a number between 1 and 6 or 0 to exit: ");
            }
        }
    }
}
