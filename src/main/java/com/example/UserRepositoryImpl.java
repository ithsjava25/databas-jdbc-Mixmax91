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
     * Create a UserRepositoryImpl configured with the provided DataSource.
     *
     * @param dataSource the DataSource used to obtain database connections for repository operations
     */
    public UserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;

    }

    /**
     * Check whether the given username and password match an account in the database.
     *
     * @param username the username to look up
     * @param password the password to verify for the user
     * @return `true` if an account with the specified username and password exists, `false` otherwise
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
            throw new RuntimeException();
        }
    }

    /**
     * Create a new account record with the provided user details.
     *
     * @param firstName the user's first name
     * @param lastName  the user's last name
     * @param ssn       the user's SSN in the format `001122-3344`
     * @param password  the password for the account
     * @param userName  the username for the account
     * @return          `true` if the account was created successfully, `false` otherwise
     * @throws IllegalArgumentException if any argument is null
     * @throws RuntimeException         if a database access error occurs
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
            throw new RuntimeException(e);
        }
    }

    /**
     * Determines whether an account with the given user_id exists.
     *
     * @param idInput the user_id to look up
     * @return `true` if an account with the specified user_id exists, `false` otherwise
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
     * Update the password for the account identified by the given user id.
     *
     * @param id the account's user_id used to locate the record
     * @param password the new password to set for the account
     * @return true if the update statement executed successfully
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
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete the account with the given user_id.
     *
     * @param idInput the user_id of the account to delete
     * @return `true` if the delete statement completed without error
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
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds the account's user_id for the given username.
     *
     * @param name the username to look up
     * @return the `user_id` for the account matching `name`, or -1 if no matching account exists
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