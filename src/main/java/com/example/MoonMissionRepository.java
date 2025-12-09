package com.example;

public interface MoonMissionRepository {
    boolean moonMissions();

    boolean missionByID(int id);

    int missionCountByYear(int year);

    boolean createUser(String firstName, String lastName, String ssn, String password, String userName);
}
