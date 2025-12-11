package com.example;

/**
 * Defines the methods needed for requesting the correct
 * mission data from database based on user integration with menu
 */
public interface MoonMissionRepository {
    boolean moonMissions();

    boolean missionByID(int id);

    int missionCountByYear(int year);

}
