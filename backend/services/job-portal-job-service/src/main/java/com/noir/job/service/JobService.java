package com.noir.job.service;

import com.noir.job.common.dto.response.JobResponse;
import com.noir.job.common.dto.response.JobSummaryResponse;
import com.noir.job.common.exception.JobException;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.AiSearchRequest;
import com.noir.job.dto.request.BulkJobRequest;
import com.noir.job.dto.request.JobRequest;
import com.noir.job.dto.request.JobSearchRequest;
import com.noir.job.dto.response.BulkJobResponse;
import com.noir.job.entity.Job;

import java.util.List;

public interface JobService {

    JobResponse createJob(Long employerId, JobRequest req)
            throws ResourceNotFoundException, JobException;


    BulkJobResponse createJobsBulk(Long employerId, BulkJobRequest req);

    JobResponse getJobById(Long id) throws ResourceNotFoundException;

    JobSummaryResponse getJobSummaryById(Long id) throws ResourceNotFoundException;

    List<JobResponse> getJobs(JobSearchRequest req);


    List<JobResponse> aiSearch(AiSearchRequest req);

    List<JobResponse> getJobsByCompany(Long companyId);

    List<JobResponse> getJobsByEmployer(Long employerId);

    List<JobResponse> getJobsByCategory(Long categoryId);

    JobResponse updateJob(Long jobId, Long employerId, JobRequest req)
            throws ResourceNotFoundException, JobException;

    JobResponse publishJob(Long jobId, Long employerId)
            throws ResourceNotFoundException, JobException;

    JobResponse closeJob(Long jobId, Long employerId)
            throws ResourceNotFoundException, JobException;

    void deleteJob(Long jobId, Long employerId)
            throws ResourceNotFoundException, JobException;

    void incrementViewCount(Long jobId) throws ResourceNotFoundException;

    void incrementApplicationCount(Long jobId) throws ResourceNotFoundException;

    List<JobResponse> getAllJobsAdmin();

    Job getJobEntityById(Long id) throws ResourceNotFoundException;
}
