package com.semantiq.server.DTO;

import java.time.LocalDateTime;

public class ChatDTO {
    int id;
    int chatbotId;
    LocalDateTime datetime;
    String vote;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChatbotId() {
        return chatbotId;
    }

    public void setChatbotId(int chatbotId) {
        this.chatbotId = chatbotId;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }
}
