package com.noir.job.repository;

import com.noir.job.model.JobTags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobTagRepository extends JpaRepository<JobTags, Long> {
    boolean existsByName(String name);
    boolean existsBySlug(String slug);
}
