package com.example;

import javax.sql.DataSource;

/**
 * Handles user inputs that calls for the correct data from repositories within menu method
 * It handles login validation
 * It initializes both repositories with datasource
 */
public class Logic {
    private final UserRepository userRepo;
    private final MoonMissionRepository moonMissionRepo;

    /**
     *
     * @param dataSource SimpleDriverManager for initializing repositories
     */
    public Logic(DataSource dataSource) {
        this.userRepo = new UserRepositoryImpl(dataSource);
        this.moonMissionRepo = new MoonMissionRepositoryImpl(dataSource);
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
    }


    /**
     * Presents a menu for user with 7 options.
     * If 0 is entered, returns to main.
     * 1-6 calls instance methods of MenuMethodsImpl
     * Loops until 0 is entered
     */
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
