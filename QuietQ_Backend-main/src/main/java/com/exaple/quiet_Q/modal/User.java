package com.exaple.quiet_Q.modal;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String aliasName;
    private String email;
    private String profileImg;
    private String idDescription;
    private String currentId;
    private String password;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdate;
    private String gender;

    @ElementCollection
    private List<String> tags;

    // Default constructor
    public User() {
        this.currentId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.lastUpdate = LocalDateTime.now();
    }

    // Parameterized constructors
    public User(String aliasName, String email, String idDescription, String password, String gender, List<String> tags) {
        this.aliasName = aliasName;
        this.email = email;
        this.idDescription = idDescription;
        this.password = password;
        this.gender = gender;
        this.currentId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.lastUpdate = LocalDateTime.now();
        this.tags = tags;
    }

    public User(String aliasName, String email, String profileImg, String idDescription, String currentId, String password, LocalDateTime lastLogin, LocalDateTime createdAt, LocalDateTime lastUpdate, String gender, List<String> tags) {
        this.aliasName = aliasName;
        this.email = email;
        this.profileImg = profileImg;
        this.idDescription = idDescription;
        this.currentId = currentId != null ? currentId : UUID.randomUUID().toString();
        this.password = password;
        this.lastLogin = lastLogin;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.lastUpdate = lastUpdate != null ? lastUpdate : LocalDateTime.now();
        this.gender = gender;
        this.tags = tags;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getIdDescription() {
        return idDescription;
    }

    public void setIdDescription(String idDescription) {
        this.idDescription = idDescription;
    }

    public String getCurrentId() {
        return currentId;
    }

    public void setCurrentId(String currentId) {
        this.currentId = currentId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", aliasName='" + aliasName + '\'' +
                ", email='" + email + '\'' +
                ", profileImg='" + profileImg + '\'' +
                ", idDescription='" + idDescription + '\'' +
                ", currentId='" + currentId + '\'' +
                ", password='" + password + '\'' +
                ", lastLogin=" + lastLogin +
                ", createdAt=" + createdAt +
                ", lastUpdate=" + lastUpdate +
                ", gender='" + gender + '\'' +
                ", tags=" + tags +
                '}';
    }
}
