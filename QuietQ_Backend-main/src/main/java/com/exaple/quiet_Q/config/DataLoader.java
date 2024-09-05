package com.exaple.quiet_Q.config;

import com.exaple.quiet_Q.modal.Community;
import com.exaple.quiet_Q.repository.CommunityRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader {

    @Autowired
    private CommunityRepository communityRepository;

    @PostConstruct
    public void loadDefaultCommunities() {
        if (communityRepository.count() == 0) {
            List<Community> defaultCommunities = Arrays.asList(
                    new Community(1L, "Developers Commune", new ArrayList<>()),
                    new Community(2L, "Technology Commune", new ArrayList<>()),
                    new Community(3L, "Science Commune", new ArrayList<>()),
                    new Community(4L, "Group Study Commune", new ArrayList<>()),
                    new Community(5L, "Hobbies Commune", new ArrayList<>()),
                    new Community(6L, "Sports Commune", new ArrayList<>()),
                    new Community(7L, "Music Commune", new ArrayList<>()),
                    new Community(8L, "Entertainment Commune", new ArrayList<>()),
                    new Community(9L, "Gaming Commune", new ArrayList<>()),
                    new Community(10L, "News Commune", new ArrayList<>())
            );
            communityRepository.saveAll(defaultCommunities);
        }
    }
}
