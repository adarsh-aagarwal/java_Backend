package com.exaple.quiet_Q.services;


import com.exaple.quiet_Q.exception.ChatException;
import com.exaple.quiet_Q.exception.UserExcepition;
import com.exaple.quiet_Q.modal.Chat;
import com.exaple.quiet_Q.modal.User;
import com.exaple.quiet_Q.request.GroupChatRequest;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatService {
    public Chat createChat(User reqUser, Long userId2) throws UserExcepition;
    public Chat findSingleChatByUserIds(Long user1,Long user2) throws ChatException;
    public Chat findChatById(Long chatId)throws ChatException;
    public List<Chat> findAllChatByUserId(Long userId)throws UserExcepition;
    public Chat createGroup(GroupChatRequest req, User reqUser) throws UserExcepition;
    public Chat addUserToGroup(Long chatId,Long userId ,User reqUser)throws UserExcepition,ChatException;
    public Chat renameGroup(Long chatId,String groupName,User reqUser)throws ChatException,UserExcepition;
    public Chat removeFromGroup(Long userId,Long chatId,User reqUser)throws UserExcepition,ChatException;
    public void deleteChat(Long chatId,Long userId)throws ChatException,UserExcepition;

    List<Chat> findChatsOlderThan(LocalDateTime cutoffTime);
    List<Chat> searchGroupChatsByTags(List<String> tags);
    @Scheduled(cron = "0 0 * * * *")  // Runs every hour at the start of the hour
    void deleteExpiredChats();

    //User findRandomUserNotInChat(Long userId) throws ExecutionControl.UserException, ExecutionControl.UserException;

    Chat addTagsToChat(Long chatId, List<String> tags);

    List<User> findTenRandomUsersNotInChat(Long userId) throws ExecutionControl.UserException;
}
