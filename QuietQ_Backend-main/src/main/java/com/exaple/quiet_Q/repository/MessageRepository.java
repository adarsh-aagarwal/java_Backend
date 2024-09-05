package com.exaple.quiet_Q.repository;

import com.exaple.quiet_Q.modal.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
    @Query("SELECT m FROM Message m WHERE m.chat.id = :chatId")
    List<Message> findByChatId(@Param("chatId")Long chatId);

    @Query("SELECT m FROM Message m WHERE m.timeStamp < :cutoffTime")
    List<Message> findMessagesOlderThan(LocalDateTime cutoffTime);
    List<Message> findByTimeStampBefore(LocalDateTime timeStamp);
}
