package com.noir.job.service.serviceimpl;

import com.noir.job.domain.UserRole;
import com.noir.job.domain.UserStatus;
import com.noir.job.mapper.UserMapper;
import com.noir.job.model.User;
import com.noir.job.payload.AuthResponse;
import com.noir.job.payload.LoginRequest;
import com.noir.job.payload.SignUpRequest;
import com.noir.job.repository.UserRepository;
import com.noir.job.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

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
                .password(req.getPassword())
                .role(req.getRole())
                .phone(req.getPhone())
                .status(UserStatus.ACTIVE)
                .lastLogin(LocalDateTime.now())
                .build();
          User savedUser =  userRepository.save(user);
          AuthResponse response= new AuthResponse();
          response.setTitle("Welcome:"+savedUser.getFullName());
          response.setMessage("Register successfully");
          response.setJwt("jwt");
          response.setUser(UserMapper.toDTO(savedUser));
          return response;

    }

    @Override
    public AuthResponse login(LoginRequest req) {

        return null;
    }
}
