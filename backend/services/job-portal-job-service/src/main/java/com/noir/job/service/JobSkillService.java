package com.noir.job.service;

import com.noir.job.common.domain.SkillCategory;
import com.noir.job.common.dto.response.JobSkillResponse;
import com.noir.job.common.exception.JobException;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.BulkJobSkillRequest;
import com.noir.job.dto.request.JobSkillRequest;
import com.noir.job.dto.response.BulkJobSkillResponse;
import com.noir.job.entity.JobSkill;

import java.util.List;
import java.util.Set;

public interface JobSkillService {

    JobSkillResponse createSkill(JobSkillRequest req) throws JobException;

    BulkJobSkillResponse createSkillsBulk(BulkJobSkillRequest req);

    List<JobSkillResponse> getAllSkills();

    List<JobSkillResponse> getSkillsByCategory(SkillCategory category);

    List<JobSkillResponse> searchSkills(String keyword);

    JobSkillResponse getSkillById(Long id) throws ResourceNotFoundException;

    JobSkillResponse updateSkill(Long id, JobSkillRequest req)
            throws ResourceNotFoundException, JobException;

    void deleteSkill(Long id) throws ResourceNotFoundException;

    /** Used internally to load skills by IDs for job creation. */
    Set<JobSkill> getSkillEntitiesByIds(Set<Long> ids) throws ResourceNotFoundException;
}
