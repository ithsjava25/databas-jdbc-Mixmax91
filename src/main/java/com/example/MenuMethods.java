package com.example;

/**
 * Defines the operations available for menu-driven user interactions,
 * including mission lookups and account management.
 */
public interface MenuMethods {

    /**
 * Display the interactive menu of available user operations.
 *
 * The menu presents the actions the user can perform (missions lookup, account
 * management, and input commands) and prompts for a selection.
 */
void printMenu();

    /**
 * Check whether the given username and password correspond to an existing user account.
 *
 * @param username the username to validate
 * @param password the password to validate for the given username
 * @return `true` if the credentials match an existing user account, `false` otherwise
 */
boolean validateCredentials(String username, String password);
    /**
 * Read an integer value entered by the user.
 *
 * @return the integer value provided by the user
 */
int checkForInt();

    /**
 * Retrieves and displays all missions.
 *
 * Implementations should present each mission's identifier and key details to the user.
 */
void findAllMissions();

    /**
 * Retrieves and displays the mission corresponding to a provided identifier.
 *
 * The implementation should obtain an identifier (for example, from user input),
 * look up the matching mission, and present its details; if no mission is found,
 * an appropriate "not found" message should be displayed.
 */
void findMissionByID();

    /**
 * Counts missions that occurred in a specified year and displays the resulting count.
 *
 * Implementations should determine the target year and present the computed count to the user.
 */
void countMissionByYear();

    /**
 * Creates a new user account by collecting and validating required user information and persisting the account for future authentication.
 */
void createAccount();

    /**
 * Validates an SSN and returns it in a standardized string format.
 *
 * <p>If validation succeeds, the returned value is normalized (for example, "123-45-6789").</p>
 *
 * @return the validated SSN in standardized format (for example, "123-45-6789")
 */
String checkSsn();

    /**
 * Formats an input name into a normalized username suitable for account creation.
 *
 * @param name the input name (for example, a full name) to format into a username
 * @return a username derived from the provided name suitable for use as an account identifier
 */
String formatStringForUsername(String name);

    /**
 * Generate a unique username derived from the supplied base username.
 *
 * If the provided base is already available, the same value may be returned;
 * otherwise a modified variant is produced that does not collide with existing usernames.
 *
 * @param userName the base username to derive from
 * @return the resulting username that is unique within the application's user namespace
 */
String makeUniqueUsername(String userName);

    /**
 * Updates the password for the currently authenticated user.
 *
 * Implementations are expected to validate and persist the new password. 
 */
void updatePassword();

    /**
 * Deletes the currently authenticated user account.
 *
 * Implementations should remove the account and any associated persistent data. 
 */
void deleteAccount();
}