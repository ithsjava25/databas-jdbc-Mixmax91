package com.example;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Logger;

/**
 * Creates and returns driver manager connection as datasource for
 * connecting with database.
 */

public class SimpleDriverManagerDataSource  implements DataSource {
    private final String URL;
    private final String USER;
    private final String PASSWORD;

    /**
     * Create a DataSource backed by DriverManager using the provided JDBC URL and credentials.
     *
     * @param url      the JDBC connection URL
     * @param username the database username
     * @param password the database password
     * @throws IllegalStateException if {@code url}, {@code username}, or {@code password} is {@code null}
     */
    public SimpleDriverManagerDataSource(String url,  String username, String password){
        URL = url;
        USER = username;
        PASSWORD = password;
        if (URL == null || USER == null || PASSWORD == null) {
            throw new IllegalStateException(
                    "Missing DB configuration. Provide APP_JDBC_URL, APP_DB_USER, APP_DB_PASS " +
                            "as system properties (-Dkey=value) or environment variables.");
        }
    }

    /**
     * Obtain a JDBC Connection using the configured URL, username, and password.
     *
     * @return a new JDBC Connection for the configured URL and credentials
     * @throws SQLException if a database access error occurs or a connection cannot be established
     */
    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Obtain a JDBC Connection using this DataSource's configured URL and credentials.
     *
     * @param username ignored; this DataSource's configured username is used instead
     * @param password ignored; this DataSource's configured password is used instead
     * @return a new JDBC {@link Connection} connected with the DataSource's configured URL and credentials
     * @throws SQLException if a database access error occurs
     */
    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Retrieve the PrintWriter used for logging by this DataSource.
     *
     * @return the configured PrintWriter, or `null` if no log writer is provided
     */
    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    /**
     * No-op implementation that ignores the provided log writer and leaves logging configuration unchanged.
     *
     * @param out the log writer to set (ignored)
     */
    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    /**
     * Ignores the requested login timeout; this DataSource does not support configuring a login timeout.
     *
     * @param seconds the requested login timeout in seconds (ignored)
     */
    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    /**
     * Get the login timeout in seconds.
     *
     * @return 0 indicating no login timeout is configured.
     */
    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    /**
     * Create a ConnectionBuilder preconfigured for this DataSource.
     *
     * @return a ConnectionBuilder configured to build Connections for this DataSource
     */
    @Override
    public ConnectionBuilder createConnectionBuilder() throws SQLException {
        return DataSource.super.createConnectionBuilder();
    }

    /**
     * Obtain the parent Logger for this DataSource.
     *
     * @return {@code null} indicating no parent logger is provided for this DataSource.
     */
    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    /**
     * Creates a builder for constructing sharding keys used for routing decisions.
     *
     * @return a ShardingKeyBuilder to construct sharding keys
     */
    @Override
    public ShardingKeyBuilder createShardingKeyBuilder() throws SQLException {
        return DataSource.super.createShardingKeyBuilder();
    }

    /**
     * Return an object that implements the requested wrapper interface or null when unwrapping is not supported.
     *
     * @param <T>   the target wrapper type
     * @param iface the Class object of the interface to unwrap to
     * @return an instance of the requested type, or null if this DataSource does not implement the interface
     */
    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    /**
     * Determines whether this DataSource is a wrapper for the supplied interface.
     *
     * @param iface the interface to check for wrapper compatibility
     * @return true if this DataSource implements or can be unwrapped as the given interface, false otherwise
     */
    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}