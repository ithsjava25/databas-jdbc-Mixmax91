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
     * Constructs a Logic instance and initializes user and moon mission repositories using the provided DataSource.
     *
     * @param dataSource the DataSource used to initialize repository implementations (provides database connections)
     */
    public Logic(DataSource dataSource) {
        this.userRepo = new UserRepositoryImpl(dataSource);
        this.moonMissionRepo = new MoonMissionRepositoryImpl(dataSource);
    }

    /**
     * Prompt the user to authenticate, allowing up to a specified number of attempts.
     *
     * @param maxAttempts the maximum number of credential attempts before the method stops prompting
     * @return `true` if the credentials are validated and the user is logged in; `false` otherwise (including when the user enters "0" to exit or when the maximum attempts are reached)
     */
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


    /**
         * Display a repeating text menu, handle user selections, and perform the chosen action until the user exits.
         *
         * Reads integer selections from the user and dispatches actions accordingly:
         * 0 — exit the menu and return;
         * 1 — list all missions;
         * 2 — find a mission by ID;
         * 3 — count missions by year;
         * 4 — create a user account;
         * 5 — update a user's password;
         * 6 — delete a user account.
         * Invalid inputs prompt the user to enter a valid option.
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