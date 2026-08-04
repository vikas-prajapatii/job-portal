package com.noir.job.service;

import com.noir.job.dto.ResumeSkillResponse;
import com.noir.job.payload.AddResumeSkillRequest;

import java.util.List;

public interface ResumeSkillService {
    ResumeSkillResponse addSkill(Long resumeId, Long candidateId, AddResumeSkillRequest req) throws Exception;

    List<ResumeSkillResponse> getSkills(Long resumeId) throws Exception;

    ResumeSkillResponse updateSkill(Long skillId, Long resumeId, Long candidateId,
                                    AddResumeSkillRequest req) throws Exception;

    void deleteSkill(Long skillId, Long resumeId, Long candidateId) throws Exception;
}
