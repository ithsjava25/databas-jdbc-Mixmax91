package com.example;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation for MoonMissionRepository interface
 *
 * Interacts with database to retrieve mission data
 * Uses parameter datasource as connection
 * MySql queries is sent for each operation
 */

public class MoonMissionRepositoryImpl implements MoonMissionRepository {
    private final DataSource dataSource;

    /**
     * Creates a MoonMissionRepositoryImpl that uses the provided DataSource to access the database.
     */
    public MoonMissionRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Retrieve and print the spacecraft names stored in the moon_mission table.
     *
     * @return `true` if at least one mission is found, `false` otherwise.
     * @throws RuntimeException if a database access error occurs while querying the table.
     */
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
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds and prints spacecraft name(s) for the specified mission id.
     *
     * @param id the mission_id to look up
     * @return `true` if a mission with the given id was found and its spacecraft name(s) printed, `false` otherwise
     * @throws RuntimeException if a database access error occurs
     */
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
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieve the number of moon missions launched in a given year.
     *
     * @param year the year to match against the launch_date year (e.g., 1969)
     * @return the count of missions launched in the specified year; 0 if none
     * @throws RuntimeException if a database access error occurs
     */
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
            throw new RuntimeException(e);
        }
    }


}