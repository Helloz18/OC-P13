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

    /**
     * create a User
     * @param user
     * @return
     */
    @PostMapping("/user")
    public ResponseEntity createUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok().body(user);
    }

    /**
     * Get the list of existing users in database
     * @return
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> findUser() {
        List<User>
                users = userService.findAllUsers();
        return ResponseEntity.ok().body(users);
    }

    /**
     * Change a status of a user to connected
     * @param username
     * @return
     */
    @PostMapping("/user/connect")
    public User connectUser(@RequestParam String username) {
        return userService.connectUser(username);
    }

    /**
     * Change a status of a user to disconnected
     * @param username
     * @return
     */
    @PostMapping("/user/disconnect")
    public ResponseEntity<ResponseInfo> disconnectUser(@RequestParam String username) {
        userService.disconnectUser(username);
        return ResponseEntity.ok().body(new ResponseInfo(username + " is disconnected"));
    }

    /**
     * Not used in this demo, but this method allows a user to be added to the list of users
     * connected to the chat
     * @param user
     * @return
     */
    @MessageMapping("/user.addUser")
    @SendTo("/user/chat")
    public User addUser(@Payload User user) {
        userService.saveUser(user);
        return user;
    }
}
