package com.example;


/**
 * Defines the methods needed for requesting the correct
 * user data from database based on user integration with menu
 */
public interface UserRepository {
    boolean validateCredentials(String username, String password);
    boolean createUser(String firstName, String lastName, String ssn, String password, String userName);

    boolean userIdExists(int idInput);

    boolean updatePassword(int idInput, String newPassword);

    boolean deleteAccount(int idInput);

    int findIdByUsername(String name);

    boolean usernameExists(String name);


}

