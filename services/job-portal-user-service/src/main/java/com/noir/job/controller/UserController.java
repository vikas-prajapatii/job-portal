package com.noir.job.controller;

import com.noir.job.dto.response.UserResponse;
import com.noir.job.mapper.UserMapper;
import com.noir.job.model.User;
import com.noir.job.payload.UpdateUserRequest;
import com.noir.job.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("api/users/profile")
    public ResponseEntity<UserResponse> getProfile(
            @RequestHeader("X-User-Email") String email
    ) throws Exception{
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }


    @PutMapping("api/users/profile")
    public ResponseEntity<UserResponse> updateProfile(
            @RequestHeader("X-User-Email") String email,
            @RequestBody UpdateUserRequest req)
            throws Exception{
        return ResponseEntity.ok(userService.updateProfile(email, req));
    }



    @PutMapping("api/users/{userId}")
    public ResponseEntity<UserResponse> getUserById(
            @RequestHeader("X-User-Email") String email,
            @RequestBody UpdateUserRequest req, @PathVariable Long userId)
            throws Exception{
        User user = userService.getUserById(userId);

        return ResponseEntity.ok(userService.updateProfile(email, req));
    }


    @GetMapping("api/users")
    public ResponseEntity<List<UserResponse>> getAllUsers()
            throws Exception{
        return ResponseEntity.ok(UserMapper.toDTOList(userService.getAllUsers()));
    }
    @PatchMapping("api/users/{userId}/suspend")
    public ResponseEntity<UserResponse> suspendUser(
            @PathVariable Long userId) throws Exception{
        return ResponseEntity.ok(userService.suspendUser(userId));
    }
    @PatchMapping("/api/users/{userId}/activated")
    public ResponseEntity<UserResponse> activateUser(
            @PathVariable Long userId) throws Exception{
        return ResponseEntity.ok(userService.activateUser(userId));
    }

}
