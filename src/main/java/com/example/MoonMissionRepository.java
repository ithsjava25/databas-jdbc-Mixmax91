package com.example;

public interface MoonMissionRepository {
    boolean moonMissions();

    boolean missionByID(int id);

    int missionCountByYear(int year);

}
