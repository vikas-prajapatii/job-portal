package com.noir.job.service;

import com.noir.job.common.domain.UserRole;
import com.noir.job.common.dto.response.UserResponse;
import com.noir.job.common.exception.UserException;
import com.noir.job.dto.request.UpdateUserRequest;
import com.noir.job.entity.User;

import java.util.List;

public interface UserService {

    User getUserByEmail(String email) throws UserException;

    User getUserById(Long id) throws UserException;

    List<User> getUsersByRole(UserRole role) throws UserException;

    List<User> getAllUsers() throws UserException;

    UserResponse updateProfile(String email, UpdateUserRequest req) throws UserException;

    // ── Admin actions ──────────────────────────────────────────────────────────
    UserResponse suspendUser(Long id) throws UserException;

    UserResponse activateUser(Long id) throws UserException;

    UserResponse deleteUser(Long id) throws UserException;

    UserResponse changeUserRole(Long id, UserRole role) throws UserException;
}
