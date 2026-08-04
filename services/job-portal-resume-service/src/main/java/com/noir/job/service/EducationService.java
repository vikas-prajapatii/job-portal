package com.noir.job.service;

import com.noir.job.dto.EducationResponse;
import com.noir.job.payload.AddEducationRequest;

import java.util.List;

public interface EducationService {
    EducationResponse addEducation(Long resumeId, Long candidateId, AddEducationRequest req)
            throws Exception;

    List<EducationResponse> getEducations(Long resumeId) throws Exception;

    EducationResponse updateEducation(Long educationId, Long resumeId, Long candidateId,
                                      AddEducationRequest req) throws Exception;

    void deleteEducation(Long educationId, Long resumeId, Long candidateId)
            throws Exception;
}
