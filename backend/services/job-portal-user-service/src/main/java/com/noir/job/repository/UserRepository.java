package com.noir.job.repository;

import com.noir.job.common.domain.Role;
import com.noir.job.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    List<User> findByRole(Role role);

    boolean existsByEmail(String email);

    Optional<User> findByResetToken(String resetToken);
}
