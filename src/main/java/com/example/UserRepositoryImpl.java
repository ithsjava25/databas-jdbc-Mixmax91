package com.example;

import javax.sql.DataSource;
import java.sql.*;

public class UserRepositoryImpl implements UserRepository {

    private final DataSource dataSource;
    public UserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;

    }
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

    @Override
    public boolean createUser(String firstName, String lastName, String ssn, String password, String userName) {
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
            return false;
        }
    }

    @Override
    public boolean userIdExists(int idInput) {
        String userIdExistsQuery = "select * from account where user_id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement createUserStmt = connection.prepareStatement(userIdExistsQuery)) {
            createUserStmt.setInt(1, idInput);
            ResultSet rs = createUserStmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

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
            return false;
        }
    }

    @Override
    public boolean deleteAccount(int idInput) {
        String deleteAccountQuery = "delete from account where user_id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement createUserStmt = connection.prepareStatement(deleteAccountQuery)) {
            createUserStmt.setInt(1, idInput);
            createUserStmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public int id(String name) {
        int result = -1;
        String idQuery = "select user_id from account where name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement idStmt = connection.prepareStatement(idQuery)) {
            idStmt.setString(1, name);
            ResultSet rs = idStmt.executeQuery();
            if(rs.next()){
                return rs.getInt("user_id");
            } else {
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

}
