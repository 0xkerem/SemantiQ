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

    // Constructor method for LoginDTO
    public LoginDTO() {

    }

    // Get ve Set ler için yorumlar kaldırılabilir!!!

    // Get the email associated with the user
    public String getEmail() {
        return email;
    }

    // Set the email for the user
    public void setEmail(String email) {
        this.email = email;
    }

    // Get the password associated with email
    public String getPassword() {
        return password;
    }

    // Set the password for the user
    public void setPassword(String password) {
        this.password = password;
    }
}
