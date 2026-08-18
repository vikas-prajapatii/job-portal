package com.noir.job.mapper;

import com.noir.job.dto.SavedJobResponse;
import com.noir.job.entity.SavedJob;

public class PreferenceMapper {
    public static SavedJobResponse toSavedJobResponse(SavedJob savedJob) {
        if (savedJob == null) return null;
        return SavedJobResponse.builder()
                .id(savedJob.getId())
                .candidateId(savedJob.getCandidateId())
                .jobId(savedJob.getJobId())
                .companyId(savedJob.getCompanyId())
                .notes(savedJob.getNotes())
                .savedAt(savedJob.getSavedAt())
                .build();
    }
}
