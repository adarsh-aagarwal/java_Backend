package com.exaple.quiet_Q.services;

import com.exaple.quiet_Q.config.JwtProvider;
import com.exaple.quiet_Q.modal.User;
import com.exaple.quiet_Q.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Scheduled(cron = "0 0 * * * *") // Every hour at the start of the hour
    public void updateCurrentIdForUsers() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusDays(1); // 24 hours ago
        List<User> users = userRepository.findUsersForIdUpdate(cutoffTime);

        for (User user : users) {
            user.setCurrentId(generateNewCurrentId());
            user.setLastUpdate(LocalDateTime.now()); // Update lastUpdate to current time
            userRepository.save(user);
        }
    }

    private String generateNewCurrentId() {
        return UUID.randomUUID().toString(); // Generates a random UUID
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User findUserByCurrentId(String userCurrentId) {
        return userRepository.findByCurrentId(userCurrentId);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User updateUser(User user, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return null; // Or handle it as you see fit
        }
        User existingUser = userOptional.get();
        if (user.getAliasName() != null && !user.getAliasName().trim().isEmpty())
            existingUser.setAliasName(user.getAliasName());
        if (user.getIdDescription() != null && !user.getIdDescription().trim().isEmpty())
            existingUser.setIdDescription(user.getIdDescription());
        if (user.getEmail() != null && !user.getEmail().trim().isEmpty())
            existingUser.setEmail(user.getEmail());
        if (user.getPassword() != null && !user.getPassword().isEmpty())
            existingUser.setPassword(user.getPassword());
        if (user.getGender() != null && !user.getGender().trim().isEmpty())
            existingUser.setGender(user.getGender());
        if (user.getProfileImg() != null && !user.getProfileImg().trim().isEmpty())
            existingUser.setProfileImg(user.getProfileImg());

        return userRepository.save(existingUser);
    }

    @Override
    public List<User> searchUserByTag(List<String> tags) {
        return userRepository.findByTags(tags);
    }

    @Override
    public User findUserByJwt(String token) {
        String email = JwtProvider.getEmailFromJwtToken(token);
        return userRepository.findByEmail(email);
    }

    @Override
    public User updateTags(List<String> tags, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return null;
        }
        User existingUser = userOptional.get();
        existingUser.setTags(tags);
        return userRepository.save(existingUser);
    }
}
