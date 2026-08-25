package com.noir.job.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.noir.job.common.domain.AuthProvider;
import com.noir.job.common.domain.Role;
import com.noir.job.common.domain.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String profileImage;
    private Role role;
    private AuthProvider authProvider;
    private UserStatus status;
    private Boolean verified;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
}
