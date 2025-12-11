package com.example;

/**
 * Defines the methods needed for requesting the correct
 * user data from database based on user integration with menu
 */
public interface UserRepository {
    /**
 * Validate that the provided username and password match an existing user account.
 *
 * @param username the user's login identifier
 * @param password the password to verify for the given username
 * @return {@code true} if the credentials correspond to an existing user, {@code false} otherwise
 */
boolean validateCredentials(String username, String password);
    /**
 * Create a new user with the provided personal details and credentials.
 *
 * @param firstName the user's given name
 * @param lastName the user's family name
 * @param ssn the user's social security number or national identifier
 * @param password the user's password credential
 * @param userName the desired unique username for the user
 * @return {@code true} if the user was created successfully, {@code false} otherwise
 */
boolean createUser(String firstName, String lastName, String ssn, String password, String userName);

    /**
 * Checks whether a user with the given ID exists.
 *
 * @param idInput the user ID to check for existence
 * @return `true` if a user with the specified ID exists, `false` otherwise
 */
boolean userIdExists(int idInput);

    /**
 * Update the password for the user with the given ID.
 *
 * @param idInput the unique user ID whose password will be changed
 * @param newPassword the new password to set for the user
 * @return `true` if the password was successfully updated, `false` otherwise
 */
boolean updatePassword(int idInput, String newPassword);

    /**
 * Delete the user account with the specified user ID.
 *
 * @param idInput the user ID of the account to delete
 * @return `true` if the account was deleted, `false` otherwise
 */
boolean deleteAccount(int idInput);

    /**
 * Retrieve the numeric ID associated with a username.
 *
 * @param name the username to look up
 * @return the user's ID if found, otherwise a negative value
 */
int findIdByUsername(String name);

    /**
 * Checks whether a user with the specified username exists in the data store.
 *
 * @param name the username to check
 * @return `true` if a user with the given username exists, `false` otherwise
 */
boolean usernameExists(String name);


}
