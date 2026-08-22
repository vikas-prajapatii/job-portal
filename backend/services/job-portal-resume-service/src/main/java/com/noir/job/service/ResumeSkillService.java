package com.noir.job.service;

import com.noir.job.common.dto.response.ResumeSkillResponse;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.AddResumeSkillRequest;

import java.util.List;

public interface ResumeSkillService {

    ResumeSkillResponse addSkill(Long resumeId, Long candidateId, AddResumeSkillRequest req)
            throws ResourceNotFoundException;

    List<ResumeSkillResponse> getSkills(Long resumeId) throws ResourceNotFoundException;

    ResumeSkillResponse updateSkill(Long skillId, Long resumeId, Long candidateId,
            AddResumeSkillRequest req) throws ResourceNotFoundException;

    void deleteSkill(Long skillId, Long resumeId, Long candidateId) throws ResourceNotFoundException;
}
