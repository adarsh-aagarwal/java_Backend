package com.exaple.quiet_Q.services;

import com.exaple.quiet_Q.modal.Chat;
import com.exaple.quiet_Q.modal.Community;
import com.exaple.quiet_Q.modal.Post;
import com.exaple.quiet_Q.repository.CommunityRepository;
import com.exaple.quiet_Q.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommunityRepository communityRepository;

    @Override
    public Post createPost(Chat chat, String communityName) {
        Community community = communityRepository.findByName(communityName);
        if (community == null) {
            throw new RuntimeException("Community not found");
        }

        Post post = new Post();
        post.setChat(chat);
        post.setCommunity(community);
        post.setCreatedAt(LocalDateTime.now());

        community.getPosts().add(post);
        communityRepository.save(community);

        return postRepository.save(post);
    }

    @Override
    public List<Post> getCommunityPosts(String communityName) {
        Community community = communityRepository.findByName(communityName);
        if (community == null) {
            throw new RuntimeException("Community not found");
        }

        return postRepository.findByCommunityIdAndCreatedAtAfter(community.getId(), LocalDateTime.now().minusHours(24));
    }

    @Scheduled(fixedRate = 3600000) // Every hour
    public void deleteOldPosts() {
        postRepository.deleteByCreatedAtBefore(LocalDateTime.now().minusHours(24));
    }
}
