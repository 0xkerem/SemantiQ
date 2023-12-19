package com.semantiq.server.DTO;

/**
 * Data Transfer Object (DTO) representing signup information for a user.
 * This class encapsulates the necessary data required for user signup.
 */
public class SignupDTO {

    // The name of the user
    private String name;

    // The surname of the user
    private String surname;

    // The company of the user
    private String company;

    // The email of the user
    private String email;

    // The password of the user
    private String password;

    // Constructor for SignupDTO
    public SignupDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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
