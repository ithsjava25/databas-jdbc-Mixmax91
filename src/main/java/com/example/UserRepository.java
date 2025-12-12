package com.example;


/**
 * Defines the methods needed for requesting the correct
 * user data from database based on user integration with menu
 */
public interface UserRepository {
    /**
 * Checks whether the provided username and password match an existing user's credentials.
 *
 * @param username the username to validate
 * @param password the password to validate for the given username
 * @return true if the credentials are valid, false otherwise
 */
boolean validateCredentials(String username, String password);
    /**
 * Create a new user record with the provided personal information and credentials.
 *
 * @param firstName the user's given name
 * @param lastName the user's family name
 * @param ssn the user's social security number or national identifier as a string
 * @param password the password for the new account
 * @param userName the desired username for the new account
 * @return `true` if the user was created successfully, `false` otherwise
 */
boolean createUser(String firstName, String lastName, String ssn, String password, String userName);

    /**
 * Determines whether a user with the given numeric ID exists in the repository.
 *
 * @param idInput the user ID to check for existence
 * @return `true` if a user with the specified ID exists, `false` otherwise
 */
boolean userIdExists(int idInput);

    /**
 * Update the password for the user identified by the given ID.
 *
 * @param idInput     the unique identifier of the user whose password will be changed
 * @param newPassword the new password to assign to the user
 * @return            `true` if the password was updated successfully, `false` otherwise
 */
boolean updatePassword(int idInput, String newPassword);

    /**
 * Delete the user account identified by the given ID.
 *
 * @param idInput the unique identifier of the user to delete
 * @return true if the account was deleted, false otherwise
 */
boolean deleteAccount(int idInput);

    /**
 * Retrieves the user ID for a given username.
 *
 * @param name the username to look up
 * @return the user ID corresponding to the given username; if no matching username exists, the result is implementation-defined
 */
int findIdByUsername(String name);

    /**
 * Checks whether a username is already present in the data store.
 *
 * @param name the username to check
 * @return `true` if the username exists, `false` otherwise
 */
boolean usernameExists(String name);


}
