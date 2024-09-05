package com.exaple.quiet_Q.request;

import java.util.List;

public class GroupChatRequest {


    private List<Long> userId;
    private String chat_name;
    private String chat_image;
    public GroupChatRequest() {
    }

    @Override
    public String toString() {
        return "GroupChatRequest{" +
                "userId=" + userId +
                ", chat_name='" + chat_name + '\'' +
                ", chat_image='" + chat_image + '\'' +
                '}';
    }

    public List<Long> getUserIds() {
        return userId;
    }

    public void setUserId(List<Long> userId) {
        this.userId = userId;
    }

    public String getChat_name() {
        return chat_name;
    }

    public void setChat_name(String chat_name) {
        this.chat_name = chat_name;
    }

    public String getChat_image() {
        return chat_image;
    }

    public void setChat_image(String chat_image) {
        this.chat_image = chat_image;
    }

    public GroupChatRequest(List<Long> userId, String chat_name, String chat_image) {
        this.userId = userId;
        this.chat_name = chat_name;
        this.chat_image = chat_image;
    }
}
