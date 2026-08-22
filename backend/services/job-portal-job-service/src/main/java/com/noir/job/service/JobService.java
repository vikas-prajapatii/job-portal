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

    /**
     * Creates multiple jobs in one request.
     * Uses partial-success: each job is saved in its own transaction.
     * If some fail (e.g. invalid categoryId), the rest still succeed.
     * Returns a summary of succeeded and failed items.
     */
    BulkJobResponse createJobsBulk(Long employerId, BulkJobRequest req);

    JobResponse getJobById(Long id) throws ResourceNotFoundException;

    /** Lightweight summary — used by other services (e.g. application-service) via Feign. */
    JobSummaryResponse getJobSummaryById(Long id) throws ResourceNotFoundException;

    /**
     * Unified search + filter for jobs.
     * All params in {@link JobSearchRequest} are optional.
     * When no params are provided, returns all OPEN active jobs.
     */
    List<JobResponse> getJobs(JobSearchRequest req);

    /**
     * AI semantic search — placeholder for vector-embedding based search.
     * TODO: implement with embeddings when AI search is ready.
     */
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

    /** Admin: returns all jobs regardless of status or active flag. */
    List<JobResponse> getAllJobsAdmin();

    /** Used internally by other services. */
    Job getJobEntityById(Long id) throws ResourceNotFoundException;
}
