package com.exaple.quiet_Q.services;

import com.exaple.quiet_Q.exception.UserExcepition;
import com.exaple.quiet_Q.modal.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public void updateCurrentIdForUsers();
    public User findUserById(Long userId);
    public User findUserByCurrentId(String userCurrentId) ;
    public User findUserByEmail(String email);
    //public User followUser(Integer userId1,Integer userId2);
    public User updateUser(User user,Long userId) throws UserExcepition;
    List<User> searchUserByTag(List<String> tags);
    public User findUserByJwt( String token);
    public User updateTags(List<String> tag,Long userId);
}
