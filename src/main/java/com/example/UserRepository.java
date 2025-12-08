package com.example;

public interface UserRepository {
    boolean validateCredentials(String username, String password);
    boolean createUser(String username, String password);

    User findUser(String username, String password);
}
