package com.semantiq.server.repository;

import com.semantiq.server.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface ChatRepo extends JpaRepository<Chat, Integer> {
  Chat findById(int id);
  List<Chat> findAllByBotId(int botId);

    @Query("SELECT DATE(c.datetime) AS date, COUNT(c) AS chatCount " +
            "FROM Chat c " +
            "WHERE c.bot.id = :botId " +
            "AND DATE(c.datetime) BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE(c.datetime)")
    Map<LocalDate, Long> getChatCountByBotAndDateRange(@Param("botId") int botId,
                                                       @Param("startDate") LocalDate startDate,
                                                       @Param("endDate") LocalDate endDate);
}
