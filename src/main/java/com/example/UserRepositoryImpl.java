package com.example;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Implementation for UserRepository interface
 * Interacts with database to retrieve user data
 * Uses parameter datasource as connection
 * MySql queries is sent for each operation
 */

public class UserRepositoryImpl implements UserRepository {

    private final DataSource dataSource;

    /**
     * Create a UserRepositoryImpl that uses the provided DataSource for obtaining database connections.
     *
     * @param dataSource the DataSource used to obtain database connections
     * @throws IllegalArgumentException if dataSource is null
     */
    public UserRepositoryImpl(DataSource dataSource) {
        if(dataSource == null) {
            throw new IllegalArgumentException("DataSource must not be null");
        }
        this.dataSource = dataSource;

    }

    /**
     * Checks whether an account exists with the given username and password.
     *
     * @param username the account username to check
     * @param password the account password to check
     * @return true if an account with the supplied username and password exists, false otherwise
     * @throws RuntimeException if a database access error occurs
     */
    @Override
    public boolean validateCredentials(String username, String password) {
        String findUser = "select * from account where name = ? and password = ?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement findUserStatement = conn.prepareStatement(findUser)) {
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
     * Creates a new user account with the provided name, SSN, and credentials.
     *
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param ssn the user's social security number in the format "001122-3344"
     * @param password the password for the account
     * @param userName the username for the account
     * @return `true` if the account was created successfully, `false` otherwise
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
     * Determine whether a user with the given user_id exists in the database.
     *
     * @param idInput the user_id to check for existence
     * @return {@code true} if a row with the given user_id exists, {@code false} otherwise
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
     * Update the account password for the specified user ID.
     *
     * @param id the user_id of the account to update
     * @param password the new password to set for the account
     * @return true if the password was updated for at least one row, false otherwise
     */
    @Override
    public boolean updatePassword(int id, String password) {
        String updatePasswordQuery = "update account set password = ? where user_id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement updatePasswordStmt = connection.prepareStatement(updatePasswordQuery)){
            updatePasswordStmt.setString(1, password);
            updatePasswordStmt.setInt(2, id);
            int rowsUpdated = updatePasswordStmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Failed to update password for user ID " + id + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete the account with the specified user_id.
     *
     * @param idInput the user_id of the account to delete
     * @return `true` if an account with the given id was deleted, `false` otherwise
     */
    @Override
    public boolean deleteAccount(int idInput) {
        String deleteAccountQuery = "delete from account where user_id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement deleteAccountStmt = connection.prepareStatement(deleteAccountQuery)) {
            deleteAccountStmt.setInt(1, idInput);
            int rowsUpdated = deleteAccountStmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Failed to delete account with ID " + idInput + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Finds the numeric user_id for the given username.
     *
     * @param name the username to look up
     * @return the user's user_id if a match is found, -1 if no matching user exists
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
     * Checks whether a username exists in the account table.
     *
     * @param name the username to look up
     * @return `true` if a row with the given username exists, `false` otherwise
     * @throws RuntimeException if a database access error occurs
     */
    @Override
    public boolean usernameExists(String name) {
        String getUsernameQuery = "select name from account where name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement idStmt = connection.prepareStatement(getUsernameQuery)) {
            idStmt.setString(1, name);
            try(ResultSet rs = idStmt.executeQuery()) {
            return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}