package com.exaple.quiet_Q.controller;

import com.exaple.quiet_Q.exception.ChatException;
import com.exaple.quiet_Q.exception.MessageException;
import com.exaple.quiet_Q.exception.UserExcepition;

import com.exaple.quiet_Q.modal.Message;

import com.exaple.quiet_Q.request.SendMessageRequest;
import com.exaple.quiet_Q.response.ApiResponse;
import com.exaple.quiet_Q.services.ChatService;
import com.exaple.quiet_Q.services.MessageService;
import com.exaple.quiet_Q.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ChatService chatService;



    @PostMapping("/create")
    public ResponseEntity<Message> sendMessageHandler(@RequestBody SendMessageRequest req, @RequestHeader("Authorization") String jwt) throws ChatException, UserExcepition, ChatException, UserExcepition {
        req.setUserId(userService.findUserByJwt(jwt).getId());
        Message message = messageService.sendMessage(req);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getChatMessagesHandler(@PathVariable("chatId") Long chatId, @RequestHeader("Authorization") String jwt) throws ChatException, UserExcepition, UserExcepition {
        List<Message> messages = messageService.getChatMessages(chatId, userService.findUserByJwt(jwt));
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<Message> getMessagesByIdHandler(@PathVariable("messageId") Long messageId) throws  MessageException {
        Message message = messageService.findMessageById(messageId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{messageId}")
    public ResponseEntity<ApiResponse> deleteHandler(@PathVariable("messageId") Long messageId, @RequestHeader("Authorization") String jwt) throws UserExcepition, MessageException, UserExcepition {
        messageService.deleteMessageById(messageId, userService.findUserByJwt(jwt));
        return new ResponseEntity<>(new ApiResponse("Message deleted successfully", false), HttpStatus.OK);
    }

    @DeleteMapping("/delete-expired")
    public ResponseEntity<ApiResponse> deleteExpiredMessagesHandler() {
        messageService.deleteOldMessages();
        return new ResponseEntity<>(new ApiResponse("Expired messages deleted successfully", false), HttpStatus.OK);
    }



}
