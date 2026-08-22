package com.noir.job.service;

import com.noir.job.common.dto.response.WorkExperienceResponse;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.AddWorkExperienceRequest;

import java.util.List;

public interface WorkExperienceService {

    WorkExperienceResponse addWorkExperience(Long resumeId,
                                             Long candidateId,
                                             AddWorkExperienceRequest req)
            throws ResourceNotFoundException;

    List<WorkExperienceResponse> getWorkExperiences(Long resumeId) throws ResourceNotFoundException;

    WorkExperienceResponse updateWorkExperience(
            Long experienceId, Long resumeId, Long candidateId,
            AddWorkExperienceRequest req) throws ResourceNotFoundException;

    void deleteWorkExperience(Long experienceId, Long resumeId,
                              Long candidateId)
            throws ResourceNotFoundException;
}
