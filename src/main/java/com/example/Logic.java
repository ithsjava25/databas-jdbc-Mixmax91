package com.example;

import javax.sql.DataSource;

public class Logic {
    private final UserRepository userRepo;
    private final MoonMissionRepository moonMissionRepo;

    public Logic(DataSource dataSource) {
        this.userRepo = new UserRepositoryImpl(dataSource);
        this.moonMissionRepo = new MoonMissionRepositoryImpl(dataSource);
    }
    public boolean login(int maxAttempts) {
        try {
            int attempts = 0;
            boolean loggedIn = false;
            while (attempts < maxAttempts && !loggedIn) {
                String username = IO.readln("Enter your username (or 0 to quit):");
                if ("0".equals(username)) {
                    System.out.println("Exiting program...");
                    return loggedIn;
                }

                String password = IO.readln("Enter your password:");

                if (userRepo.validateCredentials(username, password)) {
                    System.out.println("Welcome " + username);
                    loggedIn = true;
                } else {
                    System.out.println("Invalid username or password. Enter 0 to exit");
                }
                attempts++;
            }
            return loggedIn;
        } catch (NullPointerException e) {
            System.out.println("Invalid username or password. Enter 0 to exit");
        }
        return false;
    }

    public void menu() {
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
