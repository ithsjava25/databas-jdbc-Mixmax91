package com.example;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Scanner;

public class Logic {
    private static UserRepository userRepo;
    private static MoonMissionRepositoryImpl moonMissionRepo;

    public static void initialize(DataSource dataSource) {
        userRepo = new UserRepositoryImpl(dataSource);
        moonMissionRepo = new MoonMissionRepositoryImpl(dataSource);
    }

    public static boolean login(int maxAttempts) {
        int attempts = 0;
        boolean loggedIn = false;
        while(attempts < maxAttempts && !loggedIn) {
            String username = IO.readln("Enter your username (or 0 to quit):");
            if (username.equals("0")) {
                System.out.println("Exiting program...");
                return loggedIn;
            }
            String password = IO.readln("Enter your password:");
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
        MenuMethods menu = new MenuMethodsImpl(moonMissionRepo, userRepo);
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
