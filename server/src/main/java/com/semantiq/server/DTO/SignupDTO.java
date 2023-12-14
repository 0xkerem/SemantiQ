package com.semantiq.server.DTO;

/**
 * Data Transfer Object (DTO) representing signup information for a user.
 * This class encapsulates the necessary data required for user signup.
 */
public class SignupDTO {

    // The name of the user
    private String name;

    //The surname of the user
    private String surname;

    // The email of the user
    private String email;

    //The password of the user
    private String password;

    // Constructor method for SignupDTO
    public SignupDTO() {

    }

    // Get ve Set ler için yorumlar kaldırılabilir!!!

    // Get the name associated with the user
    public String getName() {
        return name;
    }

    // Set the name for the user
    public void setName(String name) {
        this.name = name;
    }

    // Get the surname associated with the user
    public String getSurname() {
        return surname;
    }

    // Set the surname for the user
    public void setSurname(String surname) {
        this.surname = surname;
    }

    // Get the email associated with the user
    public String getEmail() {
        return email;
    }

    // Set the email for the user
    public void setEmail(String email) {
        this.email = email;
    }

    // Get the password associated with the user
    public String getPassword() {
        return password;
    }

    // Set the password for the user
    public void setPassword(String password) {
        this.password = password;
    }
}
