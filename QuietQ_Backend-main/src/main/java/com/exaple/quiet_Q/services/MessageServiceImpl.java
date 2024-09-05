package com.exaple.quiet_Q.services;

import com.exaple.quiet_Q.exception.ChatException;
import com.exaple.quiet_Q.exception.MessageException;
import com.exaple.quiet_Q.exception.UserExcepition;
import com.exaple.quiet_Q.modal.Message;
import com.exaple.quiet_Q.modal.User;
import com.exaple.quiet_Q.repository.MessageRepository;
import com.exaple.quiet_Q.request.SendMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message sendMessage(SendMessageRequest req) throws UserExcepition,  ChatException {
        User user = userService.findUserById(req.getUserId());

        Message message = new Message();
        message.setChat(chatService.findChatById(req.getChatId()));
        message.setUser(user);
        message.setContent(req.getContent());
        message.setImage(req.getImage());
        message.setVideo(req.getVideo());
        message.setTimeStamp(LocalDateTime.now());

        return messageRepository.save(message);
    }

    @Override
    public List<Message> getChatMessages(Long chatId, User reqUser) throws ChatException, UserExcepition {
        if (!chatService.findChatById(chatId).getUsers().contains(reqUser))
            throw new UserExcepition("user does not belong to this chat");

        return messageRepository.findByChatId(chatId);
    }

    @Override
    public Message findMessageById(Long messageId) throws MessageException {
        Optional<Message> opt = messageRepository.findById(messageId);
        if (opt.isPresent())
            return opt.get();
        throw new MessageException("message does not exist");
    }

    @Override
    public void deleteMessageById(Long messageId, User reqUser) throws MessageException, UserExcepition {
        Optional<Message> opt = messageRepository.findById(messageId);
        if (!opt.isPresent())
            throw new MessageException("message does not exist");
        if (!opt.get().getUser().equals(reqUser))
            throw new UserExcepition("user does not belong to this particular message");
        messageRepository.delete(opt.get());
    }

    @Scheduled(cron = "0 0 * * * *")  // Scheduled to run every hour
    @Override
    public void deleteOldMessages() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24);
        List<Message> oldMessages = messageRepository.findByTimeStampBefore(cutoffTime);
        messageRepository.deleteAll(oldMessages);
    }
}
