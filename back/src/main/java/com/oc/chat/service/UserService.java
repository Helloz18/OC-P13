package com.oc.chat.service;

import com.oc.chat.model.User;
import com.oc.chat.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
