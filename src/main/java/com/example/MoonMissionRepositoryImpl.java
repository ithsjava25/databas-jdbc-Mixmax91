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
     * Stores datasource in an instance
     * @param dataSource used to connect to database
     */
    public MoonMissionRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Retrieves and prints the names of all spacecraft from the moon_mission table.
     * @return true if atleast one mission is found. Returns false if no data mission found.
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
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param id used to search through mission_id for match
     * @return returns name of mission from spacecraft if match with id is found
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
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param year used to search through launch_date for missions
     * @return amount of missions for given year
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
