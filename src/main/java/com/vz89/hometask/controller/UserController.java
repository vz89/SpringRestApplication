package com.vz89.hometask.controller;

import com.vz89.hometask.dto.UserDTO;
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
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = userService.getUsers();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") User user) {
        UserDTO userDTO = userService.findById(user.getId());
        return userDTO != null
                ? new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK)
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

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUserPassword(@PathVariable("id") Long id, @RequestBody User user) {
        boolean updated = userService.update(id, user);
        return (updated) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") User user) {
        userService.delete(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
