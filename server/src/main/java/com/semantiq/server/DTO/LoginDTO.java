package com.semantiq.server.DTO;

/**
 * Data Transfer Object (DTO) representing login information for a user.
 * This class encapsulates the necessary data required for user login.
 */
public class LoginDTO {

    // The email of the user
    private String email;

    // The password associated with email
    private String password;

    // Constructor for LoginDTO
    public LoginDTO() {

    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
