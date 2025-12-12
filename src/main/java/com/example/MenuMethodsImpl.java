package com.example;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Implementation of the MenuMethods interface.
 * Provides methods for handling user menu operations,
 * including displaying mission data and performing user account management.
 * It interacts with MoonMissionRepository and UserRepository
 * to perform data operations.
 */

public class MenuMethodsImpl implements MenuMethods {
    private final MoonMissionRepository moonMissionRepo;
    private final UserRepository userRepo;
    private final Scanner scanner;

    /**
     * Creates a MenuMethodsImpl using the provided moon mission repository, user repository, and input scanner.
     *
     * @param moonMissionRepo repository used to retrieve mission data
     * @param userRepo repository used to manage user accounts
     * @param scanner input scanner for reading user input
     * @throws IllegalArgumentException if any argument is null
     */
    public MenuMethodsImpl(MoonMissionRepository moonMissionRepo, UserRepository userRepo, Scanner scanner) {
        if(moonMissionRepo == null || userRepo == null || scanner == null) {
            throw new IllegalArgumentException("MoonMissionRepository, UserRepository and Scanner must not be null");
        }
        this.moonMissionRepo = moonMissionRepo;
        this.userRepo = userRepo;
        this.scanner = scanner;
    }

    /**
     * Prints the main menu to the user interface.
     */
    @Override
    public void printMenu() {
        System.out.println(
                """
                        1) List moon missions.
                        2) Get a moon mission by ID.
                        3) Count missions for a given year.
                        4) Create an account.
                        5) Update an account password.
                        6) Delete an account.
                        0) Exit.
                """);
    }

    /**
     * Validate user credentials.
     *
     * @return `true` if the provided username and password match a known user, `false` otherwise.
     */
    @Override
    public boolean validateCredentials(String username, String password) {
        return userRepo.validateCredentials(username, password);
    }


    /**
         * Reads lines from the configured Scanner until a valid integer is entered or the input stream is closed.
         *
         * If a line cannot be parsed as an integer, a prompt is printed and the method continues reading.
         *
         * @return the parsed integer from user input; returns 0 if the input stream is closed (Scanner.nextLine() throws NoSuchElementException)
         */
    @Override
    public int checkForInt() {
        while (true) {
            final String userInput;
            try{
                userInput = scanner.nextLine().trim();
            } catch(NoSuchElementException e){
                return 0;
            }

            try {
                return Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number: ");
            }
        }
    }

    /**
     * Retrieves and displays all missions available in the system.
     */
    @Override
    public void findAllMissions() {
        List<String> missions = moonMissionRepo.moonMissions();
        if (missions.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (String spacecraft : missions) {
                System.out.println(spacecraft);
            }
        }
    }

    /**
     * Prompts the user for a mission ID and displays the matching mission,
     * if one exists.
     */
    @Override
    public void findMissionByID() {
        System.out.println("Enter mission id: ");
        int idInput = checkForInt();
        String mission = moonMissionRepo.missionByID(idInput);
        if(mission == null || mission.isEmpty()) {
            System.out.println("No data found");
        } else {
            System.out.println(mission);
        }
    }

    /**
     * Counts and displays the number of missions associated with a given year.
     */
    @Override
    public void countMissionByYear() {
        System.out.println("Enter mission year: ");
        int yearInput = checkForInt();
        System.out.println("Missions for " + yearInput + " : " + moonMissionRepo.missionCountByYear(yearInput));
    }

    /**
     * Creates a new account by gathering user information,
     * validating input fields, and storing the account.
     */
    @Override
    public void createAccount() {
        //Prompts and validates user inputs
        System.out.println("Enter first name: ");
        String firstNameInput = scanner.nextLine().trim();
        if(firstNameInput.isBlank()){
            System.out.println("Cannot be blank");
            return;
        }
        //Since username is to be 3 letters from first name and 3 letters from lastname,
        //I call for formatting method to add numbers at the end if name is less than 3 letters
        String firstNameFormatted = formatStringForUsername(firstNameInput);
        System.out.println("Enter last name: ");
        String lastNameInput = scanner.nextLine().trim();
        lastNameInput = lastNameInput.trim();
        if(lastNameInput.isBlank()){
            System.out.println("Cannot be blank");
            return;
        }
        String lastNameFormatted = formatStringForUsername(lastNameInput);
        String ssn = checkSsn();

        System.out.println("Enter password: ");
        String password = scanner.nextLine();
        if(password.isBlank()){
            System.out.println("Cannot be blank");
            return;
        }

        //Creates the username by taking the first 3 letters of first name and last name
        String userName = firstNameFormatted.substring(0, 1).toUpperCase() +
                firstNameFormatted.substring(1, 3) +
                lastNameFormatted.substring(0, 1).toUpperCase() +
                lastNameFormatted.substring(1, 3);

        //Checks if username already exists, if so change it to become unique
        if(userRepo.usernameExists(userName)) {
            userName = makeUniqueUsername(userName);
        }

        //Creates the account
        if(!userRepo.createUser(firstNameInput, lastNameInput, ssn, password, userName)){
            System.out.println("Something went wrong creating account.");
        } else {
            System.out.println("Account created successfully with username: " + userName + " and ID: " + userRepo.findIdByUsername(userName));
        }
    }

    /**
     * Produce a username that does not collide with existing accounts.
     *
     * @param userName the preferred username
     * @return the original username if available; otherwise the username with a numeric suffix that is not already used
     */
    @Override
    public String makeUniqueUsername(String userName){
        int counter = 1;
        String newUserName = userName;

        //Adds numbers at the end if username exists
        //Try adding 1, then 2, then 3 etc...
        while (userRepo.usernameExists(newUserName)) {
            newUserName = userName + counter;
            counter++;
        }
        return newUserName;
    }

    /**
     * Reads a Social Security Number from user input and validates it matches the format YYMMDD-NNNN.
     *
     * @return the entered SSN in the format YYMMDD-NNNN
     */
    @Override
    public String checkSsn() {
        while (true) {
            System.out.println("Enter ssn: ");
            String ssnInput = scanner.nextLine().trim();
            if (ssnInput.matches("\\d{6}-\\d{4}")) { //Asking for YYMMDD-NNNN format
                return ssnInput;
            } else {
                System.out.println("Invalid SSN, use format: YYMMDD-NNNN.");
            }
        }
    }

    /**
     * Ensure a name is at least three characters by appending incremental digits.
     *
     * @param name the original name to format; may be shorter than three characters
     * @return the name padded with incremental digits starting at 1 until its length is at least three
     */
    @Override
    public String formatStringForUsername(String name) {
        StringBuilder tempName = new StringBuilder(name);
        int counter = 1;
        //Adds numbers at the end while name length is less than 3
        while (tempName.length() < 3) {
            tempName.append(counter);
            counter++;
        }
        return tempName.toString();
    }



    /**
     * Update the password for an existing user account identified by ID.
     *
     * Prompts for an account ID, verifies the account exists, prompts for a new non-blank password,
     * attempts to update the password via the user repository, and prints success or failure messages.
     */
    @Override
    public void updatePassword() {
        System.out.println("Enter ID: ");
        int idInput = checkForInt();

        //checks if userId exists first
        if(userRepo.userIdExists(idInput)) {
            System.out.println("Enter new password: ");
            String password = scanner.nextLine();
            if(password.isBlank()) {
                System.out.println("Password cannot be blank");
                return;
            }
            if (!userRepo.updatePassword(idInput, password)) {
                System.out.println("Password could not be updated");
            } else {
                System.out.println("Updated password successfully for ID: " + idInput);
            }
        } else {
            System.out.println("ID doesn't exist");
        }
    }

    /**
     * Prompts for a user ID and deletes the corresponding account if it exists.
     *
     * If the provided ID does not match an existing account, a message is printed and no action is taken.
     * On deletion failure or success a corresponding message is printed to standard output.
     */
    @Override
    public void deleteAccount() {
        System.out.println("Enter ID: ");
        int idInput = checkForInt();
        if(!userRepo.userIdExists(idInput)) {
            System.out.println("Account with ID " + idInput + " does not exist.");
            return;
        }
//        This is for future use, cannot use without failing tests:
//        System.out.println("Enter username: ");
//        String userName = IO.readln();
//        System.out.println("Enter password: ");
//        String password = IO.readln();
//        if(!userRepo.validateCredentials(userName, password)) {
//            System.out.println("Invalid credentials. Deletion cancelled.");
//            return;
//        }
        if(!userRepo.deleteAccount(idInput)) {
            System.out.println("Something went wrong deleting account.");
        } else {
            System.out.println("Account deleted successfully for ID: " + idInput);
        }

    }
}