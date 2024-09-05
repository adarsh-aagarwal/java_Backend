package com.exaple.quiet_Q.modal;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id", nullable = false)
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String image;
    private String video;
    private LocalDateTime timeStamp;

    // Default constructor
    public Message() {}

    // Parameterized constructor
    public Message(Long id, String content, Chat chat, User user, String image, String video, LocalDateTime timeStamp) {
        this.id = id;
        this.content = content;
        this.chat = chat;
        this.user = user;
        this.image = image;
        this.video = video;
        this.timeStamp = timeStamp;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", chat=" + chat +
                ", user=" + user +
                ", image='" + image + '\'' +
                ", video='" + video + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
