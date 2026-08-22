package com.noir.job.service;

import com.noir.job.common.dto.response.AuthResponse;
import com.noir.job.common.exception.UserException;
import com.noir.job.dto.request.SignupRequest;
import com.noir.job.common.dto.request.LoginRequest;

public interface AuthService {

    AuthResponse signup(SignupRequest req) throws UserException;

    AuthResponse login(LoginRequest req) throws UserException;
}
