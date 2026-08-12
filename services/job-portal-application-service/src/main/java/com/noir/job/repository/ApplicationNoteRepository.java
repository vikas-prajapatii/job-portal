package com.noir.job.repository;

import com.noir.job.model.ApplicationNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationNoteRepository extends JpaRepository<ApplicationNote, Long> {
    List<ApplicationNote> findByApplicationIdOrderByCreatedAtDesc(Long applicationId);

    void deleteByApplicationId(Long applicationId);
}
