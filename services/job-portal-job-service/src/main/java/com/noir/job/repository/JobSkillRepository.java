package com.noir.job.repository;

import com.noir.job.model.JobSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSkillRepository extends JpaRepository<JobSkill,Long> {
    List<JobSkill> findByActiveTrue();
    boolean existsByName(String name);
    boolean existBySlug(String slug);
}
