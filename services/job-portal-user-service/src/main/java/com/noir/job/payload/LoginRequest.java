package com.noir.job.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @Email(message = "email should be valid")
    @NotBlank(message = "email must be enter")
    private String email;
    @NotBlank(message = "password is mandatory")
    private String password;
}
