package com.example;

public interface UserValidation {
    boolean validateCredentials();
    boolean createUser(String username, String password);

    User findUser(String username, String password);


}
