package org.aibles.springboot.api.user.service.impl;

import org.aibles.springboot.api.user.dto.UserRequest;
import org.aibles.springboot.api.user.dto.UserResponse;
import org.aibles.springboot.api.user.entity.User;
import org.aibles.springboot.api.user.exception.UserNotFound;
import org.aibles.springboot.api.user.repository.UserRepository;
import org.aibles.springboot.api.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        log.error("(CREATE USER) userRequest: {}", userRequest);
        User createUser = new User();
        createUser.setUsername(userRequest.getUsername());
        createUser.setPassword(userRequest.getPassword());
        createUser.setEmail(userRequest.getEmail());
        createUser.setEmail(userRequest.getEmail());
        User saveUser = userRepository.save(createUser);
        return new UserResponse(saveUser.getId(),saveUser.getUsername(),saveUser.getEmail());
    }

    @Override
    @Transactional(readOnly = true, timeout = -1)
    public List<UserResponse> getAllUser() {
        log.info("GET ALL USER");
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
    }
    @Override
    @Transactional(readOnly = true, timeout = 30)
    public UserResponse getUserById(Long id) throws UserNotFound {
        log.info("GET USER WITH ID: {}",id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
        } else {
            throw new UserNotFound("User not found with id: " + id);
        }
    }
    @Override
    @Transactional(readOnly = false)
    public UserResponse updateUser(Long id, UserRequest userRequest) throws UserNotFound {
        log.info("UPDATE USER WITH ID: {}", id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(userRequest.getUsername());
            user.setPassword(userRequest.getPassword());
            User updateUser = userRepository.save(user);
            return new UserResponse(updateUser.getId(), updateUser.getUsername(), updateUser.getPassword());
        } else {
            throw new UserNotFound("User not found with id: " + id);
        }
    }

    @Override
    @Transactional(rollbackFor = {UserNotFound.class})
    public void deleteUserById(Long id) {
        log.info("DELETE USER BY ID: {}", id);
        userRepository.deleteById(id);
    }
}
