package com.semantiq.server.entity;

public class ChatBot {
    int id;
    String botName;
    User owner;
    BotData data;
    Chat[] chatList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public BotData getData() {
        return data;
    }

    public void setData(BotData data) {
        this.data = data;
    }

    public Chat[] getChatList() {
        return chatList;
    }

    public void setChatList(Chat[] chatList) {
        this.chatList = chatList;
    }
}
