package org.aibles.springboot.api.user.controller;

import org.aibles.springboot.api.user.dto.UserRequest;
import org.aibles.springboot.api.user.dto.UserResponse;
import org.aibles.springboot.api.user.exception.UserNotFound;
import org.aibles.springboot.api.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        log.info("CREATE USER");
        UserResponse userResponse = userService.createUser(userRequest);
        log.info("CREATE SUCCESSFUL: {}",userResponse);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        log.info("UPDATE USER BY ID: {}",userRequest);
        try {
            UserResponse userResponse = userService.updateUser(id, userRequest);
            log.info("UPDATE SUCCESSFUL: {}",userResponse);
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        } catch (UserNotFound e) {
            log.error("UPDATE FAILED: {}",e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUser() {
        log.info("GET ALL USER");
        List<UserResponse> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        log.info("GET USER BY ID: {}", id);
        try {
            UserResponse user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (UserNotFound e) {
            log.error("GET USER FAILED: {}",e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        log.info("DELETE USER BY ID: {}", id);
        try {
            userService.deleteUserById(id);
        }catch (UserNotFound e) {
            log.error("DELETE USER FAILED: {}", e.getMessage());
        }
        return ResponseEntity.ok("User with id " + id + " deleted successfully");
    }
}
