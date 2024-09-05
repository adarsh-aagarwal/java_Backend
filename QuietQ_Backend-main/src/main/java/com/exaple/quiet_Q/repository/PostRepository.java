package com.exaple.quiet_Q.repository;

import com.exaple.quiet_Q.modal.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Find posts by community ID that were created within the last 24 hours
    List<Post> findByCommunityIdAndCreatedAtAfter(Long communityId, LocalDateTime timestamp);

    // Delete posts that are older than 24 hours
    void deleteByCreatedAtBefore(LocalDateTime timestamp);
}
