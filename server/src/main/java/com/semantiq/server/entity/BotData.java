package com.semantiq.server.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "botdata")
public class BotData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private ChatBot bot;

    @Column
    private int countPos = 0;

    @Column
    private int countNeg = 0;

    @Column
    private String formData;

    @Column
    private String apiId;

    @Column
    private LocalDate lastUpdated;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ChatBot getBot() {
        return bot;
    }

    public void setBot(ChatBot bot) {
        this.bot = bot;
    }

    public int getCountPos() {
        return countPos;
    }

    public void setCountPos(int countPos) {
        this.countPos = countPos;
    }

    public int getCountNeg() {
        return countNeg;
    }

    public void setCountNeg(int countNeg) {
        this.countNeg = countNeg;
    }

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
