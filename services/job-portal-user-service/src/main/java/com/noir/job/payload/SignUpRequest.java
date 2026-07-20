package com.noir.job.payload;

import com.noir.job.domain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignUpRequest {
    @NotBlank(message = "name is mandatory")
    private String fullName;
    @NotBlank(message = "email is mandatory")
    @Email(message = "email should be valid")
    private String email;
    @NotBlank(message = "password is mandatory")
    private String password;
    @NotNull(message = "role is mandatory")
    private UserRole role;

    private String phone;

}
