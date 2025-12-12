package com.example;

/**
 * this class is for future use
 * to store logged in account as cache
 */
public class User {
    int id;
    String name;
    String password;
    String first_name;
    String last_name;
    String ssn;

    /**
     * Creates a User with the specified identifier, account name, password, first name, last name, and social security number.
     *
     * @param id numeric identifier for the user
     * @param name account username
     * @param password account password
     * @param first_name user's first name
     * @param last_name user's last name
     * @param ssn user's social security number
     */
    public User(int id, String name, String password, String first_name, String last_name, String ssn) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.ssn = ssn;
    }

    /**
     * String representation of the user including id, name, first name, and last name.
     *
     * @return a String containing the user's id, name, first name, and last name; excludes password and SSN
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }

    /**
     * Gets the user's id.
     *
     * @return the id of the user
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the user's identifier.
     *
     * @param id the identifier to assign to this user
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the user's account name.
     *
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's account name.
     *
     * @param name the account name to assign to this user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user's password.
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the password value to assign to this user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's first name.
     *
     * @return the user's first name, or {@code null} if not set
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * Sets the user's first name.
     *
     * @param first_name the first name to assign to this user
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * Gets the user's last name.
     *
     * @return the user's last name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * Sets the user's last name.
     *
     * @param last_name the new last name for the user
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * Retrieves the user's Social Security Number.
     *
     * @return the user's Social Security Number
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * Sets the user's Social Security number (SSN).
     *
     * @param ssn the Social Security number to store for the user
     */
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
}