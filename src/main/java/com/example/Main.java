package com.example;

import java.util.Arrays;

/** Labb 3 ITHS 2025, JUV25D
 * Notes:
 * Program asks user for username and password.
 * If login is successful a menu is presented.
 *  1) List moon missions.
 *  2) Get a moon mission by ID.
 *  3) Count missions for a given year.
 *  4) Create an account.
 *  5) Update an account password.
 *  6) Delete an account.
 *  0) Exit.
 *  Each option calls MenuMethodImpl who then connects to either
 *  UserRepositoryImpl or MoonMissionRepositoryImpl.
 *  A simple driver manager datasource is created in Main and is sent to MenuMethodsImpl
 *  which then initializes UserRepositoryImpl or MoonMissionRepositoryImpl with the dataSource
 *  Validation checks and exception handling is integrated and the three different interface classes
 *  makes for easy testing.
 *
 * @author Daniel Marton
 */

public class Main {
    public static final int MAX_ATTEMPTS = 5;

    /**
     * Application entry point that initializes development helpers when appropriate and starts the application.
     *
     * <p>If development mode is detected, starts the development database initializer before creating a Main
     * instance and invoking its run method.</p>
     *
     * @param args command-line arguments; inspected to determine development mode (e.g. "--dev")
     */
    static void main(String[] args) {
        if (isDevMode(args)) {
            DevDatabaseInitializer.start();
        }
        new Main().run();
    }

    /**
     * Starts the application's runtime flow: resolves database configuration, initializes a data source,
     * performs user login attempts, and displays the main menu on successful login.
     *
     * <p>Configuration is resolved from system properties first, then environment variables for
     * APP_JDBC_URL, APP_DB_USER, and APP_DB_PASS. If any are missing, the method fails immediately.</p>
     *
     * @throws IllegalStateException if any required database configuration value (URL, user, or password)
     *         is not provided via system properties or environment variables
     */
    public void run() {
        // Resolve DB settings with precedence: System properties -> Environment variables
        String jdbcUrl = resolveConfig("APP_JDBC_URL", "APP_JDBC_URL");
        String dbUser = resolveConfig("APP_DB_USER", "APP_DB_USER");
        String dbPass = resolveConfig("APP_DB_PASS", "APP_DB_PASS");
        if (jdbcUrl == null || dbUser == null || dbPass == null) {
            throw new IllegalStateException(
                    "Missing DB configuration. Provide APP_JDBC_URL, APP_DB_USER, APP_DB_PASS " +
                            "as system properties (-Dkey=value) or environment variables.");
        }

        // The datasource is created once
        SimpleDriverManagerDataSource dataSource = new SimpleDriverManagerDataSource(jdbcUrl, dbUser, dbPass);
        Logic logic = new Logic(dataSource);

        //Calls login method for user login prompts and validation with a max attempts set
        boolean loggedIn = logic.login(MAX_ATTEMPTS);
        //If loggedIn returns true the menu is presented.
        if (loggedIn) {
            logic.menu();
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