package com.example;

/**
 * Defines the methods needed for requesting the correct
 * mission data from database based on user integration with menu
 */
public interface MoonMissionRepository {
    /**
 * Indicates whether moon mission records are available in the repository.
 *
 * @return true if moon missions data is present, false otherwise.
 */
boolean moonMissions();

    /**
 * Checks whether a mission with the given identifier exists.
 *
 * @param id the mission identifier to look up
 * @return `true` if a mission with the given identifier exists, `false` otherwise
 */
boolean missionByID(int id);

    /**
 * Counts the moon missions that occurred in the specified year.
 *
 * @param year the calendar year to query for missions
 * @return the number of missions that took place in the given year
 */
int missionCountByYear(int year);

}