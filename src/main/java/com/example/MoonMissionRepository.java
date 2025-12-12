package com.example;

import java.util.List;

/**
 * Defines the methods needed for requesting the correct
 * mission data from database based on user integration with menu
 */
public interface MoonMissionRepository {
    /**
 * Retrieve the available moon missions.
 *
 * @return a list of mission names or identifiers; an empty list if no missions are available
 */
List<String> moonMissions();

    /**
 * Retrieve the mission name for the given mission identifier.
 *
 * @param id the mission identifier to look up
 * @return the mission name associated with the given identifier
 */
String missionByID(int id);

    /**
 * Return the number of moon missions that took place in the specified calendar year.
 *
 * @param year the calendar year (e.g., 1969) to count missions for
 * @return the count of moon missions in the given year
 */
int missionCountByYear(int year);

}