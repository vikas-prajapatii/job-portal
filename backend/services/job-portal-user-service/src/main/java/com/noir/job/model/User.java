package com.noir.job.model;

import com.noir.job.domain.UserRole;
import com.noir.job.domain.UserStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable= false)
    private String fullName;
    @Column(nullable= false, unique = true)
    private String email;
    @Column(nullable= false)
    private String password;
    @Column(nullable= false)
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.ROLE_JOB_SEEKER;
    private String phone;
    @Enumerated(EnumType.STRING)
    @Column(nullable= false)
    private UserStatus status = UserStatus.ACTIVE;
    private String profileImage;
    @CreationTimestamp
    @Column(nullable= false, updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable= false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;
    private LocalDateTime suspendedAt;
    private LocalDateTime deletedAt;
}
