package com.example;

public class MenuMethodsImpl implements MenuMethods {
    MoonMissionRepository moonMissionRepo;
    UserRepository userRepo;

    MenuMethodsImpl(MoonMissionRepository moonMissionRepo, UserRepository userRepo) {
        this.moonMissionRepo = moonMissionRepo;
        this.userRepo = userRepo;
    }
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

    @Override
    public void findAllMissions() {
        if(!moonMissionRepo.moonMissions()){
            System.out.println("No data found");
        }
    }

    @Override
    public void findMissionByID() {
        System.out.println("Enter mission id: ");
        int idInput = checkForInt();
        if(!moonMissionRepo.missionByID(idInput)) {
            System.out.println("No data found");
        }
    }

    @Override
    public void countMissionByYear() {
        System.out.println("Enter mission year: ");
        int yearInput = checkForInt();
        System.out.println("Missions for " + yearInput + " : " + moonMissionRepo.missionCountByYear(yearInput));
    }

    @Override
    public void createAccount() {
        String firstNameInput = IO.readln("Enter first name: ");
        if(firstNameInput.isBlank()){
            System.out.println("Cannot be blank");
            return;
        }
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
        String userName = firstNameFormatted.substring(0, 1).toUpperCase() +
                firstNameFormatted.substring(1, 3) +
                lastNameFormatted.substring(0, 1).toUpperCase() +
                lastNameFormatted.substring(1, 3);

        if(userRepo.usernameExists(userName)) {
            userName = makeUniqueUsername(userName);
        }

        if(!userRepo.createUser(firstNameInput, lastNameInput, ssn, password, userName)){
            System.out.println("Something went wrong creating account.");
        } else {
            System.out.println("Account created successfully with username: " + userName + " and ID: " + userRepo.findIdByUsername(userName));
        }
    }

    @Override
    public String makeUniqueUsername(String userName){
        int counter = 1;
        String newUserName = userName;

        while (userRepo.usernameExists(newUserName)) {
            newUserName = userName + counter;
            counter++;
        }
        return newUserName;
    }
    @Override
    public String checkSsn() {
        while (true) {
            String ssnInput = IO.readln("Enter ssn: ");
            if (ssnInput.matches("\\d{6}-\\d{4}")) {
                return ssnInput;
            } else {
                System.out.println("Invalid SSN, use format: YYMMDD-NNNN.");
            }
        }
    }

    @Override
    public String formatStringForUsername(String name) {
        StringBuilder tempName = new StringBuilder(name);
        int counter = 1;
        while (tempName.length() < 3) {
            tempName.append(counter);
            counter++;
        }
        return tempName.toString();
    }


    @Override
    public void updatePassword() {
        System.out.println("Enter ID: ");
        int idInput = checkForInt();
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

    @Override
    public void deleteAccount() {
        System.out.println("Enter ID: ");
        int idInput = checkForInt();
        if(!userRepo.userIdExists(idInput)) {
            System.out.println("Account with ID " + idInput + " does not exist.");
            return;
        }
//        System.out.println("Enter username: "); //This is for future use, cannot use without failing tests
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
