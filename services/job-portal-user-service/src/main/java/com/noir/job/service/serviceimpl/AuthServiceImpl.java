package com.noir.job.service.serviceimpl;

import com.noir.job.domain.UserRole;
import com.noir.job.domain.UserStatus;
import com.noir.job.mapper.UserMapper;
import com.noir.job.model.User;
import com.noir.job.payload.AuthResponse;
import com.noir.job.payload.LoginRequest;
import com.noir.job.payload.SignUpRequest;
import com.noir.job.repository.UserRepository;
import com.noir.job.security.CustomUserDetailService;
import com.noir.job.security.JwtProvider;
import com.noir.job.service.AuthService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailService customUserDetailService;

    @Override
    public AuthResponse signup(SignUpRequest req) throws Exception {
        if(userRepository.existsByEmail(req.getEmail())){
            throw new Exception("Email already exist, use different email:"+ req.getEmail());
        }
        if(req.getRole() == UserRole.ROLE_ADMIN){

            throw new Exception("Only admins can use this");
        }
        User user = User.builder()
                .fullName(req.getFullName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(req.getRole())
                .phone(req.getPhone())
                .status(UserStatus.ACTIVE)
                .lastLogin(LocalDateTime.now())
                .build();
          User savedUser =  userRepository.save(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication,savedUser.getId());
          AuthResponse response= new AuthResponse();
          response.setTitle("Welcome:"+savedUser.getFullName());
          response.setMessage("Register successfully");
          response.setJwt(jwt);
          response.setUser(UserMapper.toDTO(savedUser));
          return response;
    }

    @Override
    public AuthResponse login(LoginRequest req) throws Exception {
        Authentication authentication = authenticate(
                req.getEmail(), req.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByEmail(req.getEmail());
        String jwt = jwtProvider.generateToken(authentication,user.getId());
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        AuthResponse response= new AuthResponse();
        response.setTitle("Welcome-back-----:"+user.getFullName());
        response.setMessage("Login successfully");
        response.setJwt(jwt);
        response.setUser(UserMapper.toDTO(user));
        return response;
    }

    private Authentication authenticate(String email,String password) throws Exception {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(email);
        if(userDetails==null) {
            throw new Exception("user not found with email"+ email);
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())) {
            throw new Exception("password doesn't match");
        }
        return new UsernamePasswordAuthenticationToken
                (userDetails, null, userDetails.getAuthorities());
    }
}
