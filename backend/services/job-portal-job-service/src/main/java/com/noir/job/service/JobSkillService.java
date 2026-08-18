package com.noir.job.service;

import com.noir.job.dto.JobSkillResponse;
import com.noir.job.model.JobSkill;
import com.noir.job.payload.JobSkillRequest;

import java.util.List;
import java.util.Set;

public interface JobSkillService {
    JobSkillResponse createSkill(JobSkillRequest req) throws Exception;
    List<JobSkillResponse> getAllSkills();
    JobSkillResponse getSkillsById(Long id) throws Exception;

    JobSkillResponse updateSkill(Long id,JobSkillRequest req) throws Exception;
    void deleteSkill(Long id) throws Exception;

    Set<JobSkill> getSkillsByIds(Set<Long> ids);


}
