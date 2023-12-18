package com.semantiq.server.repository;

import com.semantiq.server.entity.BotData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotDataRepo extends JpaRepository<BotData, Integer> {
}
