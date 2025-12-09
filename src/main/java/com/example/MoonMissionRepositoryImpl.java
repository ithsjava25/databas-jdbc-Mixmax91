package com.example;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MoonMissionRepositoryImpl implements MoonMissionRepository {
    private final DataSource dataSource;

    public MoonMissionRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean moonMissions() {
        String moonMissionsQuery = "select spacecraft from moon_mission";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement moonMissionsStmt = connection.prepareStatement(moonMissionsQuery)) {
            boolean result = false;
            try(ResultSet rs = moonMissionsStmt.executeQuery()){
                while(rs.next()){
                    System.out.println(rs.getString("spacecraft"));
                    result = true;
                }
                return result;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean missionByID(int id) {
        String missionByIDQuery = "select spacecraft from moon_mission where mission_id = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement missionByIDStmt = connection.prepareStatement(missionByIDQuery)) {
            missionByIDStmt.setInt(1, id);
            boolean result = false;
            try(ResultSet rs = missionByIDStmt.executeQuery()){
                while(rs.next()){
                    System.out.println(rs.getString("spacecraft"));
                    result = true;
                }
                return result;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public int missionCountByYear(int year) {
        String missionCountByYearQuery = "select count(*) as amount_year from moon_mission where year(launch_date) = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement missionCountByYearStmt = connection.prepareStatement(missionCountByYearQuery)) {
            missionCountByYearStmt.setInt(1, year);
            try(ResultSet rs = missionCountByYearStmt.executeQuery()){
                if(rs.next()){
                    return(rs.getInt("amount_year"));
                } else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            return 0;
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
}
