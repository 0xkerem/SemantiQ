package com.semantiq.server.entity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name ="chatbot")
public class ChatBot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    public String botName;
    @OneToOne
    private User owner;
    @OneToOne
    private BotData data;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Chat> chatList;

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

    public List<Chat> getChatList() {
        return chatList;
    }

    public void setChatList(List<Chat> chatList) {
        this.chatList = chatList;
    }
}
