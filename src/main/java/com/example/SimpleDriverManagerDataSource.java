package com.example;

public class DataSource {
    private final String URL;
    private final String USER;
    private final String PASSWORD;

    public SimpleDriverManagerDataSource(String url,  String username, String password) implements DataSource {
        URL = url;
        USER = username;
        PASSWORD = password;
        if (URL == null || USER == null || PASSWORD == null) {
            throw new IllegalStateException(
                    "Missing DB configuration. Provide APP_JDBC_URL, APP_DB_USER, APP_DB_PASS " +
                            "as system properties (-Dkey=value) or environment variables.");
        }
    }
}
