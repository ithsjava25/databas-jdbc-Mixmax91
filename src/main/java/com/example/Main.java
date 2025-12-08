package com.example;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

/** Labb 3 ITHS 2025, JUV25D
 *
 * Prompt for Username: and then Password: on startup and validate them against the account table (name + password)
 * If the login is invalid, print a message containing the word invalid and allow exiting via option 0
 * If the login is valid, present a menu with options
 * Use the DB settings provided via APP_JDBC_URL, APP_DB_USER, APP_DB_PASS (already resolved in Main).
 *
 * @Author Daniel Marton
 */

public class Main {

    private int MAX_ATTEMPTS = 5;

    static void main(String[] args) {
        if (isDevMode(args)) {
            DevDatabaseInitializer.start();
        }
        new Main().run();
    }

    public void run() {
        // Resolve DB settings with precedence: System properties -> Environment variables
        String jdbcUrl = resolveConfig("APP_JDBC_URL", "APP_JDBC_URL");
        String dbUser = resolveConfig("APP_DB_USER", "APP_DB_USER");
        String dbPass = resolveConfig("APP_DB_PASS", "APP_DB_PASS");

        SimpleDriverManagerDataSource dataSource = new SimpleDriverManagerDataSource(jdbcUrl, dbUser, dbPass);

        //Todo: Starting point for your code
        Logic.initialize(dataSource);
        boolean loggedIn = Logic.login(MAX_ATTEMPTS);
        if (loggedIn) {
            Logic.menu();
        }


    }

    /**
     * Determines if the application is running in development mode based on system properties,
     * environment variables, or command-line arguments.
     *
     * @param args an array of command-line arguments
     * @return {@code true} if the application is in development mode; {@code false} otherwise
     */
    private static boolean isDevMode(String[] args) {
        if (Boolean.getBoolean("devMode"))  //Add VM option -DdevMode=true
            return true;
        if ("true".equalsIgnoreCase(System.getenv("DEV_MODE")))  //Environment variable DEV_MODE=true
            return true;
        return Arrays.asList(args).contains("--dev"); //Argument --dev
    }

    /**
     * Reads configuration with precedence: Java system property first, then environment variable.
     * Returns trimmed value or null if neither source provides a non-empty value.
     */
    private static String resolveConfig(String propertyKey, String envKey) {
        String v = System.getProperty(propertyKey);
        if (v == null || v.trim().isEmpty()) {
            v = System.getenv(envKey);
        }
        return (v == null || v.trim().isEmpty()) ? null : v.trim();
    }






}
