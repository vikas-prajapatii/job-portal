package com.noir.job.repository;

import com.noir.job.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findByCandidateIdAndActiveTrue(Long candidateId);

    Optional<Resume> findByCandidateIdAndIsDefaultTrue(Long candidateId);

    boolean existsByCandidateIdAndIsDefaultTrue(Long candidateId);

    long countByCandidateIdAndActiveTrue(Long candidateId);
}
