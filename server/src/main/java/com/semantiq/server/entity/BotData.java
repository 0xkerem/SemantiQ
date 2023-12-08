package com.semantiq.server.entity;

public class BotData {
    int id;
    ChatBot bot;
    int countPos = 0;
    int countNeg = 0;
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
}
