package com.vz89.hometask.controller;

import com.vz89.hometask.model.User;
import com.vz89.hometask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") User user) {
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        userService.create(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/users/{id}/activate")
    public ResponseEntity<?> activateUser(@PathVariable("id") User user, @RequestParam Integer code) {
        return userService.activate(user, code)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>("code is not active", HttpStatus.NOT_FOUND);
    }
    @GetMapping("/users/{id}/getActivationCode")
    public ResponseEntity<?> getActivationCode(@PathVariable("id") User user) {
        userService.getNewActivationCode(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
