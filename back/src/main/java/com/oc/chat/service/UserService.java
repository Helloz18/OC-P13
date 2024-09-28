package com.oc.chat.service;

import com.oc.chat.model.User;
import com.oc.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User connectUser(String username) {
        User user = userRepository.findByUsername(username);
            user.setStatus(true);
        userRepository.save(user);
        return user;
    }

    public User disconnectUser(String username) {
        User user = userRepository.findByUsername(username);
        user.setStatus(false);
        userRepository.save(user);
        return user;
    }
}
