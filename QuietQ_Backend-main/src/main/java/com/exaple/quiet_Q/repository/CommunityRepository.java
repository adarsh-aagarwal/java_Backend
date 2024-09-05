package com.exaple.quiet_Q.repository;

import com.exaple.quiet_Q.modal.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    Community findByName(String name);
}
