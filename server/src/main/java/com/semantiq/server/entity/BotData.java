package com.semantiq.server.entity;

public class BotData {
    int id;
    ChatBot bot;
    int CountPos = 0;
    int CountNeg = 0;
    String formData;

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
        return CountPos;
    }

    public void setCountPos(int countPos) {
        CountPos = countPos;
    }

    public int getCountNeg() {
        return CountNeg;
    }

    public void setCountNeg(int countNeg) {
        CountNeg = countNeg;
    }

    public String getFormData() {
        return formData;
    }

    public void setFormData(String formData) {
        this.formData = formData;
    }
}
