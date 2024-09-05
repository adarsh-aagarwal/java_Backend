package com.exaple.quiet_Q.request;

public class CreatePostRequest {
    private Long chatId;
    private String communityName;

    // Getters and setters
    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }
}
