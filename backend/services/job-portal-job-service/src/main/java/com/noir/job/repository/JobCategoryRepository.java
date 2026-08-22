package com.noir.job.repository;

import com.noir.job.entity.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {

    Optional<JobCategory> findBySlug(String slug);

    List<JobCategory> findByActiveTrue();

    List<JobCategory> findByParentIsNullAndActiveTrue();

    List<JobCategory> findByParent_Id(Long parentId);

    boolean existsByName(String name);

    boolean existsBySlug(String slug);
}
