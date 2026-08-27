package com.noir.job.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @NotBlank(message = "Token is mandatory")
    private String token;

    @NotBlank(message = "Password is mandatory")
    private String password;
}
