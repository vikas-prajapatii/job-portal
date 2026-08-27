package com.noir.job.service.impl;

import com.noir.job.entity.Otp;
import com.noir.job.entity.User;
import com.noir.job.common.domain.UserStatus;
import com.noir.job.repository.OtpRepository;
import com.noir.job.repository.UserRepository;
import com.noir.job.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final UserRepository userRepository;
    private final OtpRepository otpRepository;

    @Value("${app.otp.expiry:5}")
    private int otpExpiry;

    @Override
    @Transactional
    public Otp generateAndSaveOtp(User user) {
        String code = generateOtp();
        Otp otp = Otp.builder()
                .code(code)
                .user(user)
                .expiresAt(LocalDateTime.now().plusMinutes(otpExpiry))
                .verified(false)
                .build();
        return otpRepository.save(otp);
    }

    @Override
    @Transactional
    public void verifyOtp(String email, String code) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found with email: " + email);
        }

        Otp otp = otpRepository
                .findTopByUserOrderByCreatedAtDesc(user)
                .orElseThrow(() ->
                        new RuntimeException("OTP not found"));

        if (otp.isVerified()) {
            throw new RuntimeException("OTP already verified");
        }

        if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        if (!otp.getCode().equals(code)) {
            throw new RuntimeException("Invalid OTP");
        }

        otp.setVerified(true);
        user.setVerified(true);
        user.setStatus(UserStatus.ACTIVE);

        otpRepository.save(otp);
        userRepository.save(user);
    }

    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        return String.valueOf(
                100000 + random.nextInt(900000)
        );
    }
}
