package com.noir.job.service;

import com.noir.job.dto.PersonalInfoResponse;
import com.noir.job.dto.ResumeResponse;
import com.noir.job.entity.Resume;
import com.noir.job.payload.CreateResumeRequest;

import java.util.List;

public interface ResumeService {
    ResumeResponse createResume(Long candidateId, CreateResumeRequest req);
    ResumeResponse getResumeById(Long resumeId, Long candidateId) throws Exception;
    List<ResumeResponse> getMyResumes(Long candidateId);
    ResumeResponse updatePersonalInfo(
            Long resumeId, Long candidateId,
            PersonalInfoResponse req) throws Exception;
    ResumeResponse updateSummary(Long resumeId, Long candidateId, String summary) throws Exception;
    ResumeResponse setDefaultResume(Long resumeId, Long candidateId) throws Exception;
//    ResumeResponse updateResume(Long resumeId, Long candidateId, UpdateResumeRequest req);
    void deleteResume(Long resumeId, Long candidateId) throws Exception;
    Resume getResumeEntity(Long resumeId) throws Exception;

}
