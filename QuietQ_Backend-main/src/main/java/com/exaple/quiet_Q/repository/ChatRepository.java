package com.exaple.quiet_Q.repository;

import com.exaple.quiet_Q.modal.Chat;
import com.exaple.quiet_Q.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c JOIN c.users u WHERE u.id = :userId")
    List<Chat> findChatByUserId(@Param("userId") Long userId);

    @Query("SELECT c FROM Chat c WHERE :user MEMBER OF c.users AND :reqUser MEMBER OF c.users")
    Chat findChatByUsersId(@Param("user") User user, @Param("reqUser") User reqUser);

    @Query("select c from Chat c join c.users u1 join c.users u2 where u1.id = :userId1 and u2.id = :userId2 and c.isGroup = false")
    Chat singleChatByUserIds(@Param("userId1") Long userId1, @Param("userId2") Long userId2);
    @Query("SELECT c FROM Chat c WHERE c.timestamp < :cutoffTime")
    List<Chat> findChatsOlderThan(@Param("cutoffTime") LocalDateTime cutoffTime);
    List<Chat> findByIsGroupAndTagsContaining(boolean isGroup, List<String> tags);
}
