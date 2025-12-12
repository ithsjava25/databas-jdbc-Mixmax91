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
     * @param url      the JDBC URL for the target database
     * @param username the database username
     * @param password the database password
     * @throws IllegalStateException if `url`, `username`, or `password` is null
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
     * Obtain a database connection using the configured URL and credentials.
     *
     * @return a new JDBC Connection connected to the configured URL authenticated with the configured username and password
     * @throws SQLException if a database access error occurs or the connection cannot be established
     */
    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Obtain a new JDBC Connection to the configured URL using the provided credentials.
     *
     * @param username the username to authenticate the connection
     * @param password the password to authenticate the connection
     * @return a new {@link Connection} connected to the configured URL authenticated with the provided username and password
     * @throws SQLException if a database access error occurs or the connection cannot be established
     */
    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return DriverManager.getConnection(URL, username, password);
    }

    /**
     * Get the PrintWriter used for logging by this DataSource.
     *
     * @return null because this DataSource does not provide a log writer.
     */
    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    /**
     * Accepts a PrintWriter for the DataSource log writer but does nothing.
     *
     * <p>This DataSource does not support a log writer; the provided writer is ignored.
     *
     * @param out the PrintWriter to set; ignored by this implementation
     */
    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    /**
     * Ignores requests to change the login timeout.
     *
     * <p>This DataSource does not support configurable login timeouts; the provided value is ignored.</p>
     *
     * @param seconds the requested login timeout in seconds (ignored)
     */
    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    /**
     * Get the configured login timeout for obtaining connections from this data source.
     *
     * @return the login timeout in seconds; zero indicates no timeout is configured
     */
    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    /**
     * Create a ConnectionBuilder for creating connections from this DataSource.
     *
     * @return a ConnectionBuilder configured to create connections using this DataSource
     * @throws SQLException if a database access error occurs while obtaining the builder
     */
    @Override
    public ConnectionBuilder createConnectionBuilder() throws SQLException {
        return DataSource.super.createConnectionBuilder();
    }

    /**
     * Provide the parent Logger for this DataSource when available.
     *
     * @return the parent Logger, or `null` if none is provided
     */
    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    /**
     * Creates a builder for constructing database sharding keys.
     *
     * @return a {@link ShardingKeyBuilder} instance for building sharding keys
     */
    @Override
    public ShardingKeyBuilder createShardingKeyBuilder() throws SQLException {
        return DataSource.super.createShardingKeyBuilder();
    }

    /**
     * Indicates this DataSource does not support unwrapping to other interfaces.
     *
     * @param <T>   target interface type
     * @param iface the interface class to unwrap to
     * @return the wrapped object implementing {@code iface}, or {@code null} since unwrapping is not supported
     */
    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    /**
     * Indicates whether this DataSource is a wrapper for the given interface.
     *
     * @param iface the interface to check for unwrap compatibility
     * @return `true` if this data source can be unwrapped to `iface`, `false` otherwise.
     * This implementation always returns `false`.
     */
    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}