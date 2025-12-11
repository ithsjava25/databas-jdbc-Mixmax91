package com.example;

/**
 * Defines the operations available for menu-driven user interactions,
 * including mission lookups and account management.
 */
public interface MenuMethods {

    void printMenu();

    int checkForInt();

    void findAllMissions();

    void findMissionByID();

    void countMissionByYear();

    void createAccount();

    String checkSsn();

    String formatStringForUsername(String name);

    String makeUniqueUsername(String userName);

    void updatePassword();

    void deleteAccount();
}
