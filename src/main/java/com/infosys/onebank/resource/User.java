package com.infosys.onebank.resource;

/**
 * Created by chirag.ganatra on 9/19/2018.
 */
public class User {

    private final String username;
    private final String password;
    private final String email;
    private final String first_name;
    private final String last_name;

    public User(String username, String password, String email, String fname, String lname) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.first_name = fname;
        this.last_name = lname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }
}
