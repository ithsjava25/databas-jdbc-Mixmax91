package com.example;

public interface UserRepository {
    boolean validateCredentials(String username, String password);
    boolean createUser(String firstName, String lastName, String ssn, String password, String userName);

    boolean userIdExists(int idInput);

    boolean updatePassword(int idInput, String newPassword);

    boolean deleteAccount(int idInput);

    int id(String name);

    boolean getUsername(String name);


}

