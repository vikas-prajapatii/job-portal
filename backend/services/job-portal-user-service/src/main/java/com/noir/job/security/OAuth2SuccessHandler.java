package com.noir.job.security;

import com.noir.job.entity.User;
import com.noir.job.entity.Otp;
import com.noir.job.common.domain.UserStatus;
import com.noir.job.service.OAuth2Service;
import com.noir.job.service.OtpService;
import com.noir.job.service.MailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final OAuth2Service oauth2Service;
    private final JwtProvider jwtProvider;
    private final OtpService otpService;
    private final MailService mailService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        User user = oauth2Service.saveOrUpdate(oauthUser);

        if (!user.getVerified()) {
            try {
                Otp otp = otpService.generateAndSaveOtp(user);
                mailService.sendOtp(user.getEmail(), otp.getCode());
            } catch (Exception e) {
                // Allow flow to continue
            }
            response.sendRedirect("http://localhost:5173/verify-otp?email=" + user.getEmail());
            return;
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword() != null ? user.getPassword() : "")
                .authorities(user.getRole().name())
                .disabled(user.getStatus() != UserStatus.ACTIVE)
                .build();

        Authentication authToken = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), null, userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);

        String accessToken = jwtProvider.generateToken(authToken, user.getId());
        String refreshToken = jwtProvider.generateRefreshToken(authToken, user.getId());

        response.sendRedirect("http://localhost:5173/login?token=" + accessToken + "&refreshToken=" + refreshToken);
    }
}
