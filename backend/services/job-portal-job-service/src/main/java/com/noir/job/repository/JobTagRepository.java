package com.noir.job.repository;

import com.noir.job.entity.JobTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobTagRepository extends JpaRepository<JobTag, Long> {

    Optional<JobTag> findBySlug(String slug);

    List<JobTag> findByNameContainingIgnoreCase(String keyword);

    boolean existsByName(String name);

    boolean existsBySlug(String slug);
}
