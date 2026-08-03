package com.noir.job.service;

import com.noir.job.dto.JobRequest;
import com.noir.job.dto.JobResponse;
import com.noir.job.payload.JobSearchRequest;

import java.util.List;

public interface JobService {
    JobResponse createJob(Long employerId, JobRequest req) throws Exception;
    JobResponse getJobById(Long id) throws Exception;
    List<JobResponse> getJobs(JobSearchRequest request);
    List<JobResponse> getJobByCompany(Long companyId);

    JobResponse updateJob(Long jobId,Long employerId, JobRequest req) throws Exception;

    JobResponse publishJob(Long jobId,Long employerId) throws Exception;
    void deleteJob(Long jobId,Long employerId) throws Exception;
    JobResponse closeJob(Long jobId,Long employerId) throws Exception;

    List<JobResponse> getAllJobsAdmin();
}
