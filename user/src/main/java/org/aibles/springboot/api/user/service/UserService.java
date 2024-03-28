package org.aibles.springboot.api.user.service;

import org.aibles.springboot.api.user.dto.UserRequest;
import org.aibles.springboot.api.user.dto.UserResponse;
import org.aibles.springboot.api.user.entity.User;
import org.aibles.springboot.api.user.exception.UserNotFound;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest) ;
    List<UserResponse> getAllUser();
    UserResponse getUserById(Long id) throws UserNotFound;
    UserResponse updateUser(Long id, UserRequest userRequest) throws UserNotFound;
    void deleteUserById(Long id) throws UserNotFound;
}
