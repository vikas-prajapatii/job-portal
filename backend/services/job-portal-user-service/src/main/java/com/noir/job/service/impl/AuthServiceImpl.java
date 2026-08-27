package com.noir.job.service.impl;

import com.noir.job.common.domain.Role;
import com.noir.job.common.dto.request.LoginRequest;
import com.noir.job.common.dto.response.AuthResponse;
import com.noir.job.common.exception.UserException;
import com.noir.job.dto.request.SignupRequest;
import com.noir.job.dto.request.ForgotPasswordRequest;
import com.noir.job.dto.request.ResetPasswordRequest;
import com.noir.job.dto.request.VerifyOtpRequest;
import com.noir.job.dto.request.RefreshTokenRequest;
import com.noir.job.dto.response.ApiResponse;
import com.noir.job.entity.Otp;
import com.noir.job.entity.User;
import com.noir.job.mapper.UserMapper;
import com.noir.job.repository.UserRepository;
import com.noir.job.security.CustomUserDetailsService;
import com.noir.job.security.JwtProvider;
import com.noir.job.service.AuthService;
import com.noir.job.service.MailService;
import com.noir.job.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final OtpService otpService;
    private final MailService mailService;

    @Override
    @Transactional
    public AuthResponse signup(SignupRequest req) throws UserException {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new UserException("Email already registered: " + req.getEmail());
        }

        if (req.getRole() == Role.ROLE_ADMIN) {
            throw new UserException("Cannot self-register as ROLE_ADMIN");
        }

        User user = new User();
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setPhone(req.getPhone());
        user.setRole(req.getRole());
        user.setLastLogin(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        // Generate OTP and send email
        try {
            Otp otp = otpService.generateAndSaveOtp(savedUser);
            mailService.sendOtp(savedUser.getEmail(), otp.getCode());
        } catch (Exception e) {
            // Log and allow signup to continue so user is not blocked
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(savedUser.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                savedUser.getEmail(), null, userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication, savedUser.getId());
        String refreshToken = jwtProvider.generateRefreshToken(authentication, savedUser.getId());

        AuthResponse response = new AuthResponse();
        response.setTitle("Welcome " + savedUser.getFullName());
        response.setMessage("Registration successful. Verification email sent.");
        response.setJwt(jwt);
        response.setRefreshToken(refreshToken);
        response.setUser(UserMapper.toDTO(savedUser));
        return response;
    }

    @Override
    public AuthResponse login(LoginRequest req) throws UserException {
        Authentication authentication = authenticate(req.getEmail(), req.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(req.getEmail());
        String token = jwtProvider.generateToken(authentication, user.getId());
        String refreshToken = jwtProvider.generateRefreshToken(authentication, user.getId());

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse response = new AuthResponse();
        response.setTitle("Login successful");
        response.setMessage("Welcome back, " + user.getFullName());
        response.setJwt(token);
        response.setRefreshToken(refreshToken);
        response.setUser(UserMapper.toDTO(user));
        return response;
    }

    @Override
    @Transactional
    public ApiResponse<String> forgotPassword(ForgotPasswordRequest request) throws UserException {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new UserException("User not found with email: " + request.getEmail());
        }

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(15));
        userRepository.save(user);

        String resetLink = "http://localhost:5173/reset-password?token=" + token;
        mailService.sendPasswordResetLink(user.getEmail(), resetLink);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Password reset link sent to your email")
                .data("Reset email sent")
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<String> resetPassword(ResetPasswordRequest request) throws UserException {
        User user = userRepository.findByResetToken(request.getToken())
                .orElseThrow(() -> new UserException("Invalid or expired reset token"));

        if (user.getResetTokenExpiry() == null || user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new UserException("Reset token has expired");
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Password reset successfully")
                .data("Password updated")
                .build();
    }

    @Override
    @Transactional
    public AuthResponse verifyOtp(VerifyOtpRequest request) throws UserException {
        otpService.verifyOtp(request.getEmail(), request.getOtp());
        
        User user = userRepository.findByEmail(request.getEmail());
        
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), null, userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication, user.getId());
        String refreshToken = jwtProvider.generateRefreshToken(authentication, user.getId());

        AuthResponse response = new AuthResponse();
        response.setTitle("Account verified");
        response.setMessage("Welcome to JobPortal");
        response.setJwt(jwt);
        response.setRefreshToken(refreshToken);
        response.setUser(UserMapper.toDTO(user));
        return response;
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) throws UserException {
        String email = jwtProvider.extractEmail(request.getRefreshToken());
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        if (!jwtProvider.isTokenValid(request.getRefreshToken(), userDetails)) {
            throw new UserException("Invalid or expired refresh token");
        }

        User user = userRepository.findByEmail(email);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), null, userDetails.getAuthorities()
        );

        String accessToken = jwtProvider.generateToken(authentication, user.getId());

        AuthResponse response = new AuthResponse();
        response.setJwt(accessToken);
        response.setRefreshToken(request.getRefreshToken());
        response.setUser(UserMapper.toDTO(user));
        return response;
    }

    private Authentication authenticate(String email, String password) throws UserException {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        if (userDetails == null) {
            throw new UserException("User not found with email: " + email);
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new UserException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(email,
                null, userDetails.getAuthorities());
    }
}
