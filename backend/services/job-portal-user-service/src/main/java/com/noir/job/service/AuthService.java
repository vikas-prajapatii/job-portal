package com.noir.job.service;

import com.noir.job.common.dto.response.AuthResponse;
import com.noir.job.common.exception.UserException;
import com.noir.job.dto.request.SignupRequest;
import com.noir.job.common.dto.request.LoginRequest;
import com.noir.job.dto.request.ForgotPasswordRequest;
import com.noir.job.dto.request.ResetPasswordRequest;
import com.noir.job.dto.request.VerifyOtpRequest;
import com.noir.job.dto.request.RefreshTokenRequest;
import com.noir.job.dto.response.ApiResponse;

public interface AuthService {

    AuthResponse signup(SignupRequest req) throws UserException;

    AuthResponse login(LoginRequest req) throws UserException;

    ApiResponse<String> forgotPassword(ForgotPasswordRequest req) throws UserException;

    ApiResponse<String> resetPassword(ResetPasswordRequest req) throws UserException;

    ApiResponse<String> verifyOtp(VerifyOtpRequest req) throws UserException;

    AuthResponse refreshToken(RefreshTokenRequest req) throws UserException;
}
