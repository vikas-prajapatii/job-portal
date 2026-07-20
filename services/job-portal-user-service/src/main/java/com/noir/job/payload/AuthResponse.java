package com.noir.job.payload;

import com.noir.job.dto.response.UserResponse;
import lombok.Data;

@Data
public class AuthResponse {
    private String Jwt;
    private String title;
    private String message;
    private UserResponse user;;
}
