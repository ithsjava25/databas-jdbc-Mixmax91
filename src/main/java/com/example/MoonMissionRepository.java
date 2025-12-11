package com.example;

import java.util.List;

/**
 * Defines the methods needed for requesting the correct
 * mission data from database based on user integration with menu
 */
public interface MoonMissionRepository {
    List<String> moonMissions();

    String missionByID(int id);

    int missionCountByYear(int year);

}
