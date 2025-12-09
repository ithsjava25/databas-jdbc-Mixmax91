package com.example;

public interface MoonMissionRepository {
    boolean moonMissions();

    boolean missionByID(int id);

    int missionCountByYear(int year);

    int id(String name);

    boolean updatePassword(int id, String password);

    boolean createUser(String firstName, String lastName, String ssn, String password, String userName);

    boolean userIdExists(int idInput);

}
