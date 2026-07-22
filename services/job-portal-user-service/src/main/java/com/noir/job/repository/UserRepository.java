package com.noir.job.repository;

import com.noir.job.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);

}
