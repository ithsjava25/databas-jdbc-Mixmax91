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
    public boolean createUser(String username, String password) {
        return false;
    }

    @Override
    public User findUser(String username, String password) {
        return null;
    }
}
