package com.noir.job.dto.response;

import com.noir.job.domain.UserRole;
import com.noir.job.domain.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UserResponse {
  private long id;
  private String fullName;
  private String email;
  private String phone;
  private String profileImage;
  private UserRole role;
  private LocalDateTime createdAt;
  private LocalDateTime lastLogin;
  private UserStatus status;
}
