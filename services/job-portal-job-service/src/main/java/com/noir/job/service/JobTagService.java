package com.noir.job.service;

import com.noir.job.dto.JobTagResponse;
import com.noir.job.model.JobTags;
import com.noir.job.payload.JobTagRequest;

import java.util.List;
import java.util.Set;

public interface JobTagService {
    JobTagResponse createTag(JobTagRequest req) throws Exception;
    List<JobTagResponse> getAllTags();
    List<JobTagResponse> searchTags(String keyword);
    JobTagResponse getTagId(Long id) throws Exception;

    JobTagResponse updateTag(Long id, JobTagRequest req) throws Exception;

    void deleteTag(Long id) throws Exception;

    JobTags getTagEntitiesByIds(Long ids) throws Exception;
    Set<JobTags> getTagsByIds(Set<Long> ids) throws Exception;
}
