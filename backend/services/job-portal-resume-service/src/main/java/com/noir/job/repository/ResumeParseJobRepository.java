package com.noir.job.repository;

import com.noir.job.common.domain.ParseStatus;
import com.noir.job.entity.ResumeParseJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeParseJobRepository extends JpaRepository<ResumeParseJob, Long> {

    List<ResumeParseJob> findByCandidateIdOrderByCreatedAtDesc(Long candidateId);

    List<ResumeParseJob> findByStatus(ParseStatus status);
}
