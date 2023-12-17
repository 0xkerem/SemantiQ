package com.semantiq.server.entity;

import jakarta.persistence.*;

import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    public String name;

    @Column
    public String surname;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private int verificationCode;

    @Column
    private boolean isVerified = false;

    @OneToOne
    private ChatBot bot;

    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.verificationCode = generateVerificationCode();
    }

    public User() {

    }

    // Method to generate a random 4-digit verification code
    public int generateVerificationCode() {
        return ThreadLocalRandom.current().nextInt(1000, 10000);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(int verificationCode) {
        this.verificationCode = verificationCode;
    }

    public ChatBot getBot() {
        return bot;
    }

    public void setBot(ChatBot bot) {
        this.bot = bot;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
