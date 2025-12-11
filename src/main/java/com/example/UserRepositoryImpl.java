package com.example;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Implementation for UserRepository interface
 *
 * Interacts with database to retrieve user data
 * Uses parameter datasource as connection
 * MySql queries is sent for each operation
 */

public class UserRepositoryImpl implements UserRepository {

    private final DataSource dataSource;

    /**
     * Stores datasource in an instance
     * @param dataSource used to connect to database
     */
    public UserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;

    }

    /**
     * @param username user entered username, is compared with database
     * @param password user entered password. is compared with database
     * @return true if username and password is found, returns false if not found
     * @throws RuntimeException if a database access error occurs
     */
    @Override
    public boolean validateCredentials(String username, String password) {
        String findUser = "select * from account where name = ? and password = ?";
        try(Connection conn = dataSource.getConnection()) {
            PreparedStatement findUserStatement = conn.prepareStatement(findUser);
            findUserStatement.setString(1, username);
            findUserStatement.setString(2, password);
            try(ResultSet rs = findUserStatement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param firstName users first name
     * @param lastName users last name
     * @param ssn users ssn 001122-3344 format
     * @param password the password for the account
     * @param userName the username for the account
     * @return true if successfully created account
     * @throws RuntimeException if a database access error occurs
     * @throws IllegalArgumentException if any argument is null
     */
    @Override
    public boolean createUser(String firstName, String lastName, String ssn, String password, String userName) {
        if (firstName == null || lastName == null || ssn == null || password == null || userName == null) {
            throw new IllegalArgumentException("All parameters must be non-null");
            }
        String createUserQuery = "insert into account(name, password, first_name, last_name, ssn) values(?, ?, ?, ?, ?)";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement createUserStmt = connection.prepareStatement(createUserQuery)) {

            createUserStmt.setString(1, userName);
            createUserStmt.setString(2, password);
            createUserStmt.setString(3, firstName);
            createUserStmt.setString(4, lastName);
            createUserStmt.setString(5, ssn);
            createUserStmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to create user '" + userName + "': " + e.getMessage());
            return false;
        }
    }

    /**
     * Checks if user id exists in database
     * @param idInput used to match with database
     * @return true if found, returns false if not found
     * @throws RuntimeException if a database access error occurs
     */
    @Override
    public boolean userIdExists(int idInput) {
        String query = "select 1 from account where user_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idInput);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Changes an accounts password based in id
     * @param id used to match with database
     * @param password new password
     * @return true if successfully changed password
     * @throws RuntimeException if a database access error occurs
     */
    @Override
    public boolean updatePassword(int id, String password) {
        String updatePasswordQuery = "update account set password = ? where user_id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement updatePasswordStmt = connection.prepareStatement(updatePasswordQuery)){
            updatePasswordStmt.setString(1, password);
            updatePasswordStmt.setInt(2, id);
            updatePasswordStmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to update password for user ID " + id + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes an account based on id
     * @param idInput used to match with database
     * @return true if successfully deleted account
     * @throws RuntimeException if a database access error occurs
     */
    @Override
    public boolean deleteAccount(int idInput) {
        String deleteAccountQuery = "delete from account where user_id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement createUserStmt = connection.prepareStatement(deleteAccountQuery)) {
            createUserStmt.setInt(1, idInput);
            createUserStmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to delete account with ID " + idInput + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Searches database for user id based on their username
     * @param name username used to match with database
     * @return user id if succesfull, -1 if no match found
     * @throws RuntimeException if a database access error occurs
     */
    @Override
    public int findIdByUsername(String name) {
        String idQuery = "select user_id from account where name = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement idStmt = connection.prepareStatement(idQuery)) {

            idStmt.setString(1, name);
            try (ResultSet rs = idStmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("user_id");
                } else {
                    return -1;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Searches database for username
     * @param name username used to match with database
     * @return true if found, false if not found
     * @throws RuntimeException if a database access error occurs
     */
    @Override
    public boolean usernameExists(String name) {
        String getUsernameQuery = "select name from account where name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement idStmt = connection.prepareStatement(getUsernameQuery)) {
            idStmt.setString(1, name);
            ResultSet rs = idStmt.executeQuery();
            if(rs.next()){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
