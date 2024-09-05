package com.exaple.quiet_Q.services;

import com.exaple.quiet_Q.modal.Chat;
import com.exaple.quiet_Q.modal.Post;
import java.util.List;

public interface PostService {

 //   Post createPost(Long chatId, String communityName);

    Post createPost(Chat chat, String communityName);

    List<Post> getCommunityPosts(String communityName);
}
