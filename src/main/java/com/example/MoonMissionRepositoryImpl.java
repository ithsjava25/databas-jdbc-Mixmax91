package com.example;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
     * Create a MoonMissionRepositoryImpl using the provided DataSource.
     *
     * @param dataSource the DataSource used to obtain database connections
     * @throws IllegalArgumentException if {@code dataSource} is null
     */
    public MoonMissionRepositoryImpl(DataSource dataSource) {
        if(dataSource == null) {
            throw new IllegalArgumentException("DataSource must not be null");
        }
        this.dataSource = dataSource;
    }

    /**
     * Retrieve spacecraft names from the moon_mission table.
     *
     * @return a list of spacecraft names; empty if no rows are found
     * @throws RuntimeException if a database access error occurs
     */
    @Override
    public List<String> moonMissions() {
        String moonMissionsQuery = "select spacecraft from moon_mission";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement moonMissionsStmt = connection.prepareStatement(moonMissionsQuery)) {
            List<String> results = new ArrayList<>();
            try(ResultSet rs = moonMissionsStmt.executeQuery()){
                while(rs.next()){
                    results.add(rs.getString("spacecraft"));
                }
                return results;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve moon missions from database", e);
        }
    }

    /**
     * Retrieve the spacecraft name for the mission with the given database id.
     *
     * @param id the mission_id to look up
     * @return the spacecraft name if a mission with the given id exists, otherwise an empty string
     * @throws RuntimeException if a database access error occurs
     */
    @Override
    public String missionByID(int id) {
        String missionByIDQuery = "select spacecraft from moon_mission where mission_id = ?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement missionByIDStmt = connection.prepareStatement(missionByIDQuery)) {
            missionByIDStmt.setInt(1, id);
            try(ResultSet rs = missionByIDStmt.executeQuery()){
                if(rs.next()){
                    return rs.getString("spacecraft");
                } else {
                    return "";
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve moon mission by id from database", e);
        }
    }

    /**
     * Count missions launched in the specified year.
     *
     * @param year the year to match against the missions' launch_date
     * @return the number of missions launched in that year
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
            throw new RuntimeException("Failed to retrieve mission count data by year from database", e);
        }
    }


}