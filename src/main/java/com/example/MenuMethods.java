package com.example;

/**
 * Defines the operations available for menu-driven user interactions,
 * including mission lookups and account management.
 */
public interface MenuMethods {

    /**
 * Display the application's menu options to the user.
 */
void printMenu();

    /**
 * Prompts for and returns an integer value entered by the user.
 *
 * @return the integer value provided by the user
 */
int checkForInt();

    /**
 * Displays all missions available in the system.
 *
 * Implementations should present each mission's identifying information and key details to the user.
 */
void findAllMissions();

    /**
 * Locate and display details for a mission specified by its identifier.
 *
 * If a mission matching the provided identifier cannot be found, the method informs the user.
 */
void findMissionByID();

    /**
 * Counts missions that occurred in a specified year and presents the total to the user.
 */
void countMissionByYear();

    /**
 * Creates a new user account by collecting and validating required user information,
 * ensuring a unique username and valid SSN, and storing the account.
 */
void createAccount();

    /**
 * Obtain and validate a Social Security Number (SSN) provided by the user.
 *
 * @return a validated SSN string in a standardized format (for example, "XXX-XX-XXXX")
 */
String checkSsn();

    /**
 * Produce a username-safe string derived from the given name.
 *
 * @param name the input name (for example, a full or display name) to be converted into a username
 * @return a formatted username string suitable for use as an account identifier
 */
String formatStringForUsername(String name);

    /**
 * Produces a username derived from the provided proposed username that is unique within the system.
 *
 * @param userName the proposed username to base the result on
 * @return the resulting username that is unique within the system
 */
String makeUniqueUsername(String userName);

    /**
 * Update the password for an existing user account.
 */
void updatePassword();

    /**
 * Delete an existing user account and any associated data.
 *
 * Implementations should perform any necessary confirmation, validation, and cleanup steps required to remove the account.
 */
void deleteAccount();
}