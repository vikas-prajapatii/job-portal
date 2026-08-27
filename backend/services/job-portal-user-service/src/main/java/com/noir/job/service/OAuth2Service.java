package com.noir.job.service;

import com.noir.job.entity.User;
import com.noir.job.common.domain.AuthProvider;
import com.noir.job.common.domain.Role;
import com.noir.job.common.domain.UserStatus;
import com.noir.job.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OAuth2Service {

    private final UserRepository userRepository;

    @Transactional
    public User saveOrUpdate(OAuth2User oauthUser) {
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");
        String sub = oauthUser.getAttribute("sub");

        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            existingUser.setGoogleId(sub);
            existingUser.setAuthProvider(AuthProvider.GOOGLE);
            existingUser.setLastLogin(LocalDateTime.now());
            return userRepository.save(existingUser);
        }

        User user = new User();
        user.setEmail(email);
        user.setFullName(name);
        user.setAuthProvider(AuthProvider.GOOGLE);
        user.setGoogleId(sub);
        user.setVerified(false);
        user.setStatus(UserStatus.INACTIVE);
        user.setRole(Role.ROLE_JOB_SEEKER);
        user.setLastLogin(LocalDateTime.now());
        
        return userRepository.save(user);
    }
}
