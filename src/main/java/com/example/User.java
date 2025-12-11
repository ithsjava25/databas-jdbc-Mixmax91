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

    /****
     * Creates a User with the provided identifier and personal details.
     *
     * @param id         the user's numeric identifier
     * @param name       the user's account or login name
     * @param password   the user's account password
     * @param first_name the user's first name
     * @param last_name  the user's last name
     * @param ssn        the user's social security number
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
     * Produce a string representation of the user including id, name, first_name, and last_name while omitting sensitive fields.
     *
     * @return the user's string representation containing id, name, first_name, and last_name
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
     * Gets the user's identifier.
     *
     * @return the user's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the user's identifier.
     *
     * @param id the user's unique identifier
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieve the user's name.
     *
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name.
     *
     * @param name the user's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the stored password for this user.
     *
     * @return the stored password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the new password for the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's first name.
     *
     * @return the user's first name.
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * Sets the user's first name.
     *
     * @param first_name the new first name
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
     * @param last_name the last name to store for the user
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * Retrieves the user's Social Security number.
     *
     * @return the user's Social Security number, or {@code null} if it has not been set
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * Set the user's Social Security number.
     *
     * @param ssn the Social Security Number to associate with this user, or {@code null} to clear it
     */
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
}