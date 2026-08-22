package com.noir.job.repository;

import com.noir.job.common.domain.UserRole;
import com.noir.job.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    List<User> findByRole(UserRole role);

    boolean existsByEmail(String email);
}
