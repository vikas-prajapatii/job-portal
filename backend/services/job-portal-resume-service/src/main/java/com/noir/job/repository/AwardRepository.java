package com.noir.job.repository;

import com.noir.job.entity.Award;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AwardRepository extends JpaRepository<Award, Long> {

    List<Award> findByResume_IdOrderByDisplayOrderAsc(Long resumeId);
}
