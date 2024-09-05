package com.exaple.quiet_Q.request;

public class SendMessageRequest {
    private Long userId;
    private Long chatId;
    private String content;
    private String image;  // New field for image
    private String video;  // New field for video

    // Constructors
    public SendMessageRequest() {
    }

    public SendMessageRequest(Long userId, Long chatId, String content, String image, String video) {
        this.userId = userId;
        this.chatId = chatId;
        this.content = content;
        this.image = image;
        this.video = video;
    }

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "SendMessageRequest{" +
                "userId=" + userId +
                ", chatId=" + chatId +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                ", video='" + video + '\'' +
                '}';
    }
}
