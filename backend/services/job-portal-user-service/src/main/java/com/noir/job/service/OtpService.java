package com.noir.job.service;

import com.noir.job.entity.Otp;
import com.noir.job.entity.User;

public interface OtpService {
    Otp generateAndSaveOtp(User user);
    void verifyOtp(String email, String code);
}
