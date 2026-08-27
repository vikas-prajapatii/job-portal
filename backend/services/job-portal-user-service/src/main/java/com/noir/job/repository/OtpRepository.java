package com.noir.job.repository;

import com.noir.job.entity.Otp;
import com.noir.job.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findTopByUserOrderByCreatedAtDesc(User user);
}
