package com.semantiq.server.repository;

import com.semantiq.server.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface ChatRepo extends JpaRepository<Chat, Integer> {
    Chat findById(int id);
    List<Chat> findAllByBotId(int botId);

    @Query(value = "SELECT DATE(datetime) as date, COUNT(*) as totalUsage " +
            "FROM Chat " +
            "WHERE chatbot_id = :chatBotId AND DATE(datetime) BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE(datetime) " +
            "ORDER BY DATE(datetime) DESC " +
            "LIMIT 15", nativeQuery = true)
    List<Map<String, Object>> findChatsCountLast15Days(@Param("chatBotId") int chatBotId,
                                                       @Param("startDate") LocalDate startDate,
                                                       @Param("endDate") LocalDate endDate);

    @Query("SELECT c FROM Chat c WHERE c.bot.id = :botId AND c.datetime >= :startDate")
    List<Chat> findAllByBotIdAndCreatedAtAfter(
            @Param("botId") int botId,
            @Param("startDate") LocalDateTime startDate
    );
}


