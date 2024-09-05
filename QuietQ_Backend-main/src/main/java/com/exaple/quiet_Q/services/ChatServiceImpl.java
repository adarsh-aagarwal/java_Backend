package com.exaple.quiet_Q.services;

import com.exaple.quiet_Q.exception.ChatException;
import com.exaple.quiet_Q.exception.UserExcepition;
import com.exaple.quiet_Q.modal.Chat;
import com.exaple.quiet_Q.modal.User;
import com.exaple.quiet_Q.repository.ChatRepository;
import com.exaple.quiet_Q.repository.UserRepository;
import com.exaple.quiet_Q.request.GroupChatRequest;

//import jdk.jshell.spi.ExecutionControl.UserException;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Chat createChat(User reqUser, Long userId2) throws UserExcepition {
        try {
            User user = userService.findUserById(userId2);
            //System.out.print(user+" /n "+ reqUser);

            Chat isChatExists = findSingleChatByUserIds(reqUser.getId(), user.getId());
            if (isChatExists != null) {
                // chat exists so return the chat
                return isChatExists;
            }
            Chat chat = new Chat();
            chat.setCreatedBy(reqUser);
            chat.getUsers().add(user);
            chat.getUsers().add(reqUser);
            chat.setGroup(false);
            chat.setTimestamp(LocalDateTime.now());


            return chatRepository.save(chat);
        } catch (Exception e) {
            throw new UserExcepition("Error creating chat"+e);
        }
    }

    @Override
    public Chat findChatById(Long chatId) throws ChatException {
        Optional<Chat> opt = chatRepository.findById(chatId);
        if (opt.isPresent())
            return opt.get();
        throw new ChatException("Chat not found with id " + chatId);
    }

    @Override
    public List<Chat> findAllChatByUserId(Long userId) throws UserExcepition {
        try {
            return chatRepository.findChatByUserId(userId);
        } catch (Exception e) {
            throw new UserExcepition("Error finding chats by user id");
        }
    }

    @Override
    public Chat createGroup(GroupChatRequest req, User reqUser) throws UserExcepition {
        try {
            Chat chatGroup = new Chat();
            chatGroup.setCreatedBy(reqUser);
            chatGroup.setGroup(true);
            chatGroup.setChatName(req.getChat_name());
            chatGroup.setChatImage(req.getChat_image());
            chatGroup.getAdmins().add(reqUser);
            chatGroup.setTimestamp(LocalDateTime.now());

            for (Long userId : req.getUserIds()) {
                chatGroup.getUsers().add(userService.findUserById(userId));
            }
            return chatRepository.save(chatGroup);
        } catch (Exception e) {
            throw new UserExcepition("Error creating group chat");
        }
    }
    @Override
    public List<Chat> searchGroupChatsByTags(List<String> tags) {
        return chatRepository.findByIsGroupAndTagsContaining(true, tags);
    }

    @Override
    public Chat addUserToGroup(Long chatId, Long userId, User reqUser) throws UserExcepition, ChatException {
        try {
            Optional<Chat> chat = chatRepository.findById(chatId);
            if (!chat.isPresent()) {
                throw new ChatException("Chat group does not exist with id " + chatId);
            }
            User user = userService.findUserById(userId);
            if (!chat.get().getAdmins().contains(reqUser)) {
                throw new UserExcepition("User is not an admin of this chat group");
            }
            chat.get().getUsers().add(user);
            return chatRepository.save(chat.get());
        } catch (Exception e) {
            throw new UserExcepition("Error adding user to group");
        }
    }

    @Override
    public Chat renameGroup(Long chatId, String groupName, User reqUser) throws ChatException, UserExcepition {
        try {
            Optional<Chat> chat = chatRepository.findById(chatId);
            if (!chat.isPresent()) {
                throw new ChatException("Chat group does not exist with id " + chatId);
            }
            if (!chat.get().getUsers().contains(reqUser)) {
                throw new UserExcepition("User is not a member of this chat group");
            }
            chat.get().setChatName(groupName);
            return chatRepository.save(chat.get());
        } catch (Exception e) {
            throw new UserExcepition("Error renaming group");
        }
    }

    @Override
    public Chat removeFromGroup(Long userId, Long chatId, User reqUser) throws UserExcepition, ChatException {
        try {
            Optional<Chat> chat = chatRepository.findById(chatId);
            if (!chat.isPresent()) {
                throw new ChatException("Chat group does not exist with id " + chatId);
            }
            User user = userService.findUserById(userId);
            if (!chat.get().getAdmins().contains(reqUser)) {
                if (!chat.get().getUsers().contains(user) && !user.equals(reqUser)) {
                    throw new UserExcepition("Bad credentials");
                }
            }
            if (!chat.get().getUsers().contains(user)) {
                throw new UserExcepition("User is not a member of this chat group");
            }
            chat.get().getUsers().remove(user);
            return chatRepository.save(chat.get());
        } catch (Exception e) {
            throw new UserExcepition("Error removing user from group");
        }
    }

    @Override
    public void deleteChat(Long chatId, Long userId) throws ChatException, UserExcepition {
        try {
            Optional<Chat> chatOptional = chatRepository.findById(chatId);
            if (!chatOptional.isPresent()) {
                throw new ChatException("Chat group does not exist with id " + chatId);
            }

            Chat chat = chatOptional.get();
            if (chat.getAdmins().contains(userService.findUserById(userId))) {
                chatRepository.delete(chat);
            } else {
                throw new UserExcepition("User does not have the required permissions to delete the chat");
            }
        } catch (Exception e) {
            throw new UserExcepition("Error deleting chat");
        }
    }

    @Override
    public Chat findSingleChatByUserIds(Long userId1, Long userId2) throws ChatException {
        try {
            Chat chat = chatRepository.findChatByUsersId(userRepository.findById(userId1).get(), userRepository.findById(userId2).get());
            if (chat != null) {
                return chat;
            } else {
                return null;
                //throw new ChatException("No chat found between users with ids " + userId1 + " and " + userId2);
            }
        } catch (Exception e) {
            throw new ChatException("Error finding chat by user ids");
        }
    }
    @Override
    public List<Chat> findChatsOlderThan(LocalDateTime cutoffTime) {
        return chatRepository.findChatsOlderThan(cutoffTime);
    }

    @Override
    @Scheduled(cron = "0 0 * * * *")  // Runs every hour at the start of the hour
    public void deleteExpiredChats() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24);
        List<Chat> expiredChats = findChatsOlderThan(cutoffTime);
        chatRepository.deleteAll(expiredChats);
    }
    @Override
    public Chat addTagsToChat(Long chatId, List<String> tags) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));
        if (chat.isGroup()) {
            List<String> currentTags = chat.getTags();
            currentTags.addAll(tags);
            chat.setTags(currentTags);
            return chatRepository.save(chat);
        } else {
            throw new RuntimeException("Cannot add tags to a non-group chat");
        }
    }
    @Override
    public List<User> findTenRandomUsersNotInChat(Long userId) throws ExecutionControl.UserException {
        try {
            // Fetch all users in chats of the current user
            List<Chat> userChats = chatRepository.findChatByUserId(userId);
            Set<Long> userChatIds = userChats.stream()
                    .flatMap(chat -> chat.getUsers().stream())
                    .map(User::getId)
                    .collect(Collectors.toSet());

            // Add the current user's ID to the set
            userChatIds.add(userId);

            // Find all users excluding those in userChatIds
            List<User> potentialUsers = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                    .filter(user -> !userChatIds.contains(user.getId()))
                    .collect(Collectors.toList());

            if (potentialUsers.isEmpty()) {
                throw new ExecutionControl.UserException("No available users to create a chat with", "NoUsersAvailableException", new StackTraceElement[0]);
            }

            // Shuffle the list to ensure randomness and select up to 10 users
            Collections.shuffle(potentialUsers);
            return potentialUsers.stream().limit(10).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ExecutionControl.UserException("Error finding random users not in chat", e.getClass().getName(), e.getStackTrace());
        }
    }

    // Other existing methods...

}
