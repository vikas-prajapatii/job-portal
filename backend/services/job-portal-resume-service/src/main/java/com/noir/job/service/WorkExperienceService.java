package com.noir.job.service;

import com.noir.job.dto.WorkExperienceResponse;
import com.noir.job.payload.AddWorkExperienceRequest;

import java.util.List;

public interface WorkExperienceService {
    WorkExperienceResponse addWorkExperience(Long resumeId, Long candidateId, AddWorkExperienceRequest req) throws Exception;
    List<WorkExperienceResponse> getWorkExperiences(Long resumeId) throws Exception;

    WorkExperienceResponse updateWorkExperience(
            Long experienceId, Long resumeId, Long candidateId,
            AddWorkExperienceRequest req) throws Exception;
    void deleteWorkExperience(Long experienceId, Long resumeId,
                              Long candidateId) throws Exception;
}
