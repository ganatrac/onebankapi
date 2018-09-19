package com.infosys.onebank.dto;

/**
 * Created by chirag.ganatra on 9/19/2018.
 */
public class UserDTO {

    private String userId;
    private String email;
    private String username;

    public UserDTO(String userId, String email, String username) {
        this.userId = userId;
        this.email = email;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
