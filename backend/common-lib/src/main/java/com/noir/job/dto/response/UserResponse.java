package com.noir.job.dto.response;

import com.noir.job.domain.UserRole;
import com.noir.job.domain.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
