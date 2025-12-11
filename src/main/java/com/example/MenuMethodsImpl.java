package com.example;

/**
 * Implementation of the MenuMethods interface.
 * Provides methods for handling user menu operations,
 * including displaying mission data and performing user account management.
 * It interacts with MoonMissionRepository and UserRepository
 * to perform data operations.
 */

public class MenuMethodsImpl implements MenuMethods {
    MoonMissionRepository moonMissionRepo;
    UserRepository userRepo;

    /**
     * Creates a MenuMethodsImpl instance backed by the provided repositories.
     *
     * @param moonMissionRepo repository for retrieving moon mission data
     * @param userRepo repository for managing user accounts
     */
    MenuMethodsImpl(MoonMissionRepository moonMissionRepo, UserRepository userRepo) {
        this.moonMissionRepo = moonMissionRepo;
        this.userRepo = userRepo;
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
     * Reads user input repeatedly until a valid integer is entered.
     *
     * @return the integer value entered by the user
     */
    @Override
    public int checkForInt() {
        while (true) {
            String userInput = IO.readln();
            try {
                return Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number: ");
            }
        }
    }

    /**
     * Displays all missions available in the system to standard output.
     *
     * If no missions are available, prints "No data found".
     */
    @Override
    public void findAllMissions() {
        if(!moonMissionRepo.moonMissions()){
            System.out.println("No data found");
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
        if(!moonMissionRepo.missionByID(idInput)) {
            System.out.println("No data found");
        }
    }

    /**
     * Prompts the user for a mission year and prints the number of missions for that year.
     */
    @Override
    public void countMissionByYear() {
        System.out.println("Enter mission year: ");
        int yearInput = checkForInt();
        System.out.println("Missions for " + yearInput + " : " + moonMissionRepo.missionCountByYear(yearInput));
    }

    /**
     * Creates a new user account by prompting for and validating user details, ensuring a unique username, and persisting the account.
     *
     * <p>The method prompts for first name, last name, SSN, and password; rejects blank names or passwords; formats names for username creation; makes the username unique if necessary; attempts to create the user in the repository; and prints a success message with the created username and user ID or an error message on failure.</p>
     */
    @Override
    public void createAccount() {
        //Prompts and validates user inputs
        String firstNameInput = IO.readln("Enter first name: ");
        if(firstNameInput.isBlank()){
            System.out.println("Cannot be blank");
            return;
        }
        //Since username is to be 3 letters from first name and 3 letters from lastname,
        //I call for formatting method to add numbers at the end if name is less than 3 letters
        String firstNameFormatted = formatStringForUsername(firstNameInput);
        String lastNameInput = IO.readln("Enter last name: ");
        if(lastNameInput.isBlank()){
            System.out.println("Cannot be blank");
            return;
        }
        String lastNameFormatted = formatStringForUsername(lastNameInput);
        String ssn = checkSsn();
        String password = IO.readln("Enter password: ");
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
     * Ensures a username is unique by checking existing accounts
     * and adjusting the value if needed.
     * @param userName the initial username to validate
     * @return a unique username
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
     * Prompt for and return an SSN in the form YYMMDD-NNNN, re-prompting until the input matches that pattern.
     *
     * @return SSN string in the form YYMMDD-NNNN
     */
    @Override
    public String checkSsn() {
        while (true) {
            String ssnInput = IO.readln("Enter ssn: ");
            if (ssnInput.matches("\\d{6}-\\d{4}")) { //Asking for YYMMDD-NNNN format
                return ssnInput;
            } else {
                System.out.println("Invalid SSN, use format: YYMMDD-NNNN.");
            }
        }
    }

    /**
         * Ensure a name has at least three characters by appending numeric digits to its end.
         *
         * @param name the input name to format; if its length is already three or more it is returned unchanged
         * @return the formatted name with length >= 3, with digits appended starting from 1 when padding is needed
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
     * Update the password for an existing user account by ID.
     *
     * Prompts for an account ID and, if the account exists, prompts for a new password
     * and attempts to update it in the user repository. Prints messages indicating
     * whether the ID was found and whether the password update succeeded.
     */
    @Override
    public void updatePassword() {
        System.out.println("Enter ID: ");
        int idInput = checkForInt();

        //checks if userId exists first
        if(userRepo.userIdExists(idInput)) {
            System.out.println("Enter new password: ");
            String password = IO.readln();
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
     * Delete a user account identified by its numeric ID.
     *
     * Prompts for an ID, verifies the account exists, attempts to delete it, and reports the outcome via console messages.
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