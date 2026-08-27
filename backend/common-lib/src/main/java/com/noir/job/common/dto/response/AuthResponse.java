package com.noir.job.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {
    private String jwt;
    private String refreshToken;
    private String title;
    private String message;
    private UserResponse user;
}
