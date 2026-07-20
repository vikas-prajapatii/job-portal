package com.noir.job.service;

import com.noir.job.payload.AuthResponse;
import com.noir.job.payload.LoginRequest;
import com.noir.job.payload.SignUpRequest;

public interface AuthService {
    AuthResponse signup(SignUpRequest req) throws Exception;

    AuthResponse login(LoginRequest req);

}
