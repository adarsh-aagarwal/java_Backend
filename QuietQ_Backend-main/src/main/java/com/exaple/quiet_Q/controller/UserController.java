package com.exaple.quiet_Q.controller;

import com.exaple.quiet_Q.modal.User;
import com.exaple.quiet_Q.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<User> getUserByToken(@RequestHeader("Authorization") String jwt) {
        try {
            Long userId = userService.findUserByJwt(jwt).getId();
            User user = userService.findUserById(userId);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/search/profile")
    public ResponseEntity<User> searchUserByCurrentId(@RequestParam("Identity") String currentId) {
        try {
           // Integer.parseInt(currentId); // This will throw NumberFormatException if the format is invalid
            User user = userService.findUserByCurrentId(currentId);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update/profile")
    public ResponseEntity<User> updateUserDetails(@RequestHeader("Authorization") String jwt, @RequestBody User user) {
        try {
            Long userId = userService.findUserByJwt(jwt).getId();
            User updatedUser = userService.updateUser(user, userId);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update/tags")
    public ResponseEntity<User> updateTags(@RequestHeader("Authorization") String jwt,
                                           @RequestBody List<String> tags) {
        try {
            Long userId = userService.findUserByJwt(jwt).getId();
            User updatedUser = userService.updateTags(tags, userId);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/search/users")
    public ResponseEntity<List<User>> searchUsersByTags(@RequestBody List<String> tags) {
        try {
            List<User> users = userService.searchUserByTag(tags);
            if (users != null && !users.isEmpty()) {
                return ResponseEntity.ok(users);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
