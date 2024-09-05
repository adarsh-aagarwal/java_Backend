package com.exaple.quiet_Q.repository;

import com.exaple.quiet_Q.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
       User findByCurrentId(String currentId);
       User findByEmail(String email);
       @Query("SELECT u FROM User u WHERE u.createdAt < :cutoffTime")
       List<User> findUsersForIdUpdate(LocalDateTime cutoffTime);
       @Query("SELECT u FROM User u JOIN u.tags t WHERE t IN :tags")
       List<User> findByTags(@Param("tags") List<String> tags);
}
