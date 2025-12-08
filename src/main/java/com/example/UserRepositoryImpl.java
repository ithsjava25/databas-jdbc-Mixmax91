package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRepositoryImpl implements UserRepository {

    Connection connection;
    PreparedStatement findUser, CreateUser, ValidateUser;
    public UserRepositoryImpl() {

    }
    @Override
    public boolean validateCredentials(String username, String password) {
        return false;

    }

    @Override
    public boolean createUser(String username, String password) {
        return false;
    }

    @Override
    public User findUser(String username, String password) {
        return null;
    }
}
