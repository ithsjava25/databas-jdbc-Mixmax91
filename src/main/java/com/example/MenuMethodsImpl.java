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
                        1) List moon missions (prints spacecraft names from `moon_mission`).
                        2) Get a moon mission by mission_id (prints details for that mission).
                        3) Count missions for a given year (prompts: year; prints the number of missions launched that year).
                        4) Create an account (prompts: first name, last name, ssn, password; prints confirmation).
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
        System.out.println("Creates account");
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
