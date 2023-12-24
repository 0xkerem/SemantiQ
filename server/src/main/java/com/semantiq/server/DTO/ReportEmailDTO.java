package com.semantiq.server.DTO;

public class ReportEmailDTO {
    private int chatId;
    private String userEmail;
    private int chatBotId;

    // Constructors
    public ReportEmailDTO() {
    }

    public ReportEmailDTO(int chatId, String userEmail, int chatBotId) {
        this.chatId = chatId;
        this.userEmail = userEmail;
        this.chatBotId = chatBotId;
    }

    // Getters and setters
    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getChatBotId() {
        return chatBotId;
    }

    public void setChatBotId(int chatBotId) {
        this.chatBotId = chatBotId;
    }
}
