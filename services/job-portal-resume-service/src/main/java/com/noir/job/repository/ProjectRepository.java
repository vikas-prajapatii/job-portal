package com.noir.job.repository;

import com.noir.job.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByResume_IdOrderByDisplayOrderAsc(Long resumeId);

}
