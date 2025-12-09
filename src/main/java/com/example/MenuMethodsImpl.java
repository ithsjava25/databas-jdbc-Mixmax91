package com.example;

public class MenuMethodsImpl implements MenuMethods {
    MoonMissionRepository moonMissionRepo;

    MenuMethodsImpl(MoonMissionRepository moonMissionRepo) {
        this.moonMissionRepo = moonMissionRepo;
    }
    @Override
    public void printMenu() {
        System.out.println(
                """
                        1) List moon missions.
                        2) Get a moon mission by ID.
                        3) Count missions for a given year.
                        4) Create an account.
                        5) Update an account password (prompts: user_id, new password; prints confirmation).
                        6) Delete an account (prompts: user_id; prints confirmation).
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
        String firstName = IO.readln("Enter first name: ");
        String lastName = IO.readln("Enter last name: ");
        String ssn = checkSsn();
        String password = IO.readln("Enter password: ");
        String userName = firstName.substring(0, 1).toUpperCase() +
                firstName.substring(1, 3) +
                lastName.substring(0, 1).toUpperCase() +
                lastName.substring(1, 3);
        //TODO: Check if already exists
        if(!moonMissionRepo.createUser(firstName, lastName, ssn, password, userName)){
            System.out.println("Something went wrong creating account.");
        } else {
            System.out.println("Account created successfully with username: " + userName + " and ID: " + moonMissionRepo.id(userName));
        }
    }

    @Override
    public String checkSsn() {
        while (true) { System.out.println("Enter ssn: ");
            String ssnInput = IO.readln();
            if (ssnInput.matches("\\d{6}-\\d{4}")) {
                return ssnInput;
            } else {
                System.out.println("Invalid SSN, use format: YYYYMMDD-NNNN");
            }
        }
    }


    @Override
    public void updatePassword() {
        System.out.println("Updates password");
    }

    @Override
    public void deleteAccount() {
        System.out.println("Deletes account");
    }
}
