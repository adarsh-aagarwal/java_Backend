package com.exaple.quiet_Q.services;

import com.exaple.quiet_Q.exception.ChatException;
import com.exaple.quiet_Q.exception.MessageException;
import com.exaple.quiet_Q.exception.UserExcepition;
import com.exaple.quiet_Q.modal.Message;
import com.exaple.quiet_Q.modal.User;
import com.exaple.quiet_Q.request.SendMessageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface MessageService {
    Message sendMessage(SendMessageRequest req) throws UserExcepition, ChatException;
    List<Message> getChatMessages(Long chatId, User reqUser) throws ChatException, UserExcepition;
    Message findMessageById(Long messageId) throws MessageException;
    void deleteMessageById(Long messageId, User reqUser) throws MessageException, UserExcepition;
    void deleteOldMessages();  // New method to delete messages older than 24 hours
}
