package com.noir.job.service;

import com.noir.job.common.dto.response.JobTagResponse;
import com.noir.job.common.exception.JobException;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.BulkJobTagRequest;
import com.noir.job.dto.request.JobTagRequest;
import com.noir.job.dto.response.BulkJobTagResponse;
import com.noir.job.entity.JobTag;

import java.util.List;
import java.util.Set;

public interface JobTagService {

    JobTagResponse createTag(JobTagRequest req) throws JobException;

    BulkJobTagResponse createTagsBulk(BulkJobTagRequest req);

    List<JobTagResponse> getAllTags();

    List<JobTagResponse> searchTags(String keyword);

    JobTagResponse getTagById(Long id) throws ResourceNotFoundException;

    JobTagResponse updateTag(Long id, JobTagRequest req)
            throws ResourceNotFoundException, JobException;

    void deleteTag(Long id) throws ResourceNotFoundException;

    /** Used internally to load tags by IDs for job creation. */
    Set<JobTag> getTagEntitiesByIds(Set<Long> ids) throws ResourceNotFoundException;
}
