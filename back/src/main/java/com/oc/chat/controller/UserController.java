package com.oc.chat.controller;

import com.oc.chat.model.ResponseInfo;
import com.oc.chat.model.User;
import com.oc.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService
            userService;

    @PostMapping("/user")
    public ResponseEntity createUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findUser() {
        List<User>
                users = userService.findAllUsers();
        return ResponseEntity.ok().body(users);
    }

    @PostMapping("/user/connect")
    public ResponseEntity<ResponseInfo> connectUser(@RequestParam String username) {
        userService.connectUser(username);
        return ResponseEntity.ok().body(new ResponseInfo(username + " is connected"));
    }
    @PostMapping("/user/disconnect")
    public ResponseEntity<ResponseInfo> disconnectUser(@RequestParam String username) {
        userService.connectUser(username);
        return ResponseEntity.ok().body(new ResponseInfo(username + " is disconnected"));
    }

    @MessageMapping("/user.addUser")
    @SendTo("/user/chat")
    public User addUser(@Payload User user) {
        userService.saveUser(user);
        return user;
    }
}
