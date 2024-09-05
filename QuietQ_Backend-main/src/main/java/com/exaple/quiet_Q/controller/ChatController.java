package com.exaple.quiet_Q.controller;

import com.exaple.quiet_Q.exception.ChatException;
import com.exaple.quiet_Q.exception.UserExcepition;
import com.exaple.quiet_Q.modal.Chat;
import com.exaple.quiet_Q.modal.Post;
import com.exaple.quiet_Q.request.CreatePostRequest;
import com.exaple.quiet_Q.request.GroupChatRequest;
import com.exaple.quiet_Q.request.SingleChatRequest;
import com.exaple.quiet_Q.response.ApiResponse;
import com.exaple.quiet_Q.services.ChatService;
import com.exaple.quiet_Q.services.PostService;
import com.exaple.quiet_Q.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;






import java.util.List;

    @RestController
    @RequestMapping("/api/chats")
    public class ChatController {
        @Autowired
        private UserService userService;
        @Autowired
        private ChatService chatService;
        @Autowired
        private PostService postService;

        @PostMapping("/single")
        public ResponseEntity<Chat> chatCreateHandler(@RequestBody SingleChatRequest singleChatRequest, @RequestHeader("Authorization") String jwt) throws UserExcepition, UserExcepition {
           // System.out.print("cc"+singleChatRequest.getId());
            Chat chat=chatService.createChat(userService.findUserByJwt(jwt),singleChatRequest.getId());
            return new ResponseEntity<>(chat, HttpStatus.CREATED);
        }
        @PostMapping("/group")
        public ResponseEntity<Chat> groupChatCreateHandler(@RequestBody GroupChatRequest groupeChatRequest, @RequestHeader("Authorization") String jwt) throws UserExcepition, UserExcepition {
            Chat chat=chatService.createGroup(groupeChatRequest,userService.findUserByJwt(jwt));
            return new ResponseEntity<>(chat, HttpStatus.CREATED);
        }
        @GetMapping("/{chatId}")
        public ResponseEntity<Chat> findChatByIdHandler(@PathVariable("chatId") Long chatId) throws ChatException, ChatException {
            Chat chat=chatService.findChatById(chatId);
            return new ResponseEntity<>(chat,HttpStatus.OK);
        }
        @GetMapping("/user")
        public ResponseEntity<List<Chat>> findChatByUserIdHandler(@RequestHeader("Authorization") String jwt) throws UserExcepition, UserExcepition {
            List<Chat> chat=chatService.findAllChatByUserId(userService.findUserByJwt(jwt).getId());
            return new ResponseEntity<>(chat, HttpStatus.CREATED);
        }
        @PutMapping("/{chatId}/add/{userId}")
        public ResponseEntity<Chat> addUserToGroupHandler(@PathVariable("chatId")Long chatId,@PathVariable ("userId")Long userId,@RequestHeader ("Authentication")String jwt) throws UserExcepition, ChatException, UserExcepition {
            Chat chat=chatService.addUserToGroup(chatId,userId,userService.findUserByJwt(jwt));
            return new ResponseEntity<>(chat,HttpStatus.OK);
        }
        @PutMapping("/{chatId}/rename/{groupName}")
        public ResponseEntity<Chat> renameHandler(@PathVariable("chatId")Long chatId,@PathVariable ("groupName")String groupName,@RequestHeader ("Authentication")String jwt) throws UserExcepition, ChatException, UserExcepition {
            Chat chat=chatService.renameGroup(chatId,groupName,userService.findUserByJwt(jwt));
            return new ResponseEntity<>(chat,HttpStatus.OK);
        }

        @PutMapping("/{chatId}/rename/{userId}")
        public ResponseEntity<Chat> removeFromGroupHandler(@PathVariable("chatId")Long chatId,@PathVariable ("userId")Long userId,@RequestHeader ("Authentication")String jwt) throws UserExcepition, ChatException, UserExcepition {
            Chat chat=chatService.removeFromGroup(userId,chatId,userService.findUserByJwt(jwt));
            return new ResponseEntity<>(chat,HttpStatus.OK);
        }
        @DeleteMapping("/delete/{chatId}")
        public ResponseEntity<ApiResponse> chatDeleteHandler(@PathVariable("chatId")Long chatId, @RequestHeader ("Authentication")String jwt) throws UserExcepition, ChatException, UserExcepition {
            chatService.deleteChat(chatId,userService.findUserByJwt(jwt).getId());
            ApiResponse apiResponse=new ApiResponse("Chat deleted Successfully...",true);
            return new ResponseEntity<>(apiResponse,HttpStatus.OK);
        }
        @PostMapping("/{chatId}/tags")
        public ResponseEntity<Chat> addTagsToChat(@PathVariable Long chatId, @RequestBody List<String> tags) {
            Chat updatedChat = chatService.addTagsToChat(chatId, tags);
            return new ResponseEntity<>(updatedChat, HttpStatus.OK);
        }
        @PostMapping("/create/post")
        public ResponseEntity<ApiResponse> createPostHandler(@RequestBody CreatePostRequest createPostRequest) {
            try {
                Chat chat = chatService.findChatById(createPostRequest.getChatId());
                if (chat == null || !chat.isGroup()) {
                    return new ResponseEntity<>(new ApiResponse("Chat is not a group chat or does not exist.", false), HttpStatus.BAD_REQUEST);
                }

                Post post = postService.createPost(chat, createPostRequest.getCommunityName());
                return new ResponseEntity<>(new ApiResponse("Post created successfully.", true), HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse("Error creating post: " + e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @GetMapping("/community/{communityName}")
        public ResponseEntity<List<Post>> getPostsByCommunity(@PathVariable String communityName) {
            try {
                List<Post> posts = postService.getCommunityPosts(communityName);
                return new ResponseEntity<>(posts, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

}
