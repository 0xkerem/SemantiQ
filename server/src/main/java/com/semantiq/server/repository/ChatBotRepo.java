package com.semantiq.server.repository;

import com.semantiq.server.entity.ChatBot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatBotRepo extends JpaRepository<ChatBot, Integer> {
}
