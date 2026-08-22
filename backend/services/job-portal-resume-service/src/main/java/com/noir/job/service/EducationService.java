package com.noir.job.service;

import com.noir.job.common.dto.response.EducationResponse;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.AddEducationRequest;

import java.util.List;

public interface EducationService {

    EducationResponse addEducation(Long resumeId, Long candidateId, AddEducationRequest req)
            throws ResourceNotFoundException;

    List<EducationResponse> getEducations(Long resumeId) throws ResourceNotFoundException;

    EducationResponse updateEducation(Long educationId, Long resumeId, Long candidateId,
            AddEducationRequest req) throws ResourceNotFoundException;

    void deleteEducation(Long educationId, Long resumeId, Long candidateId)
            throws ResourceNotFoundException;
}
