package com.noir.job.client;

import com.noir.job.common.dto.response.JobResponse;
import com.noir.job.common.dto.response.JobSummaryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "job-portal-job-service")
public interface JobClient {

    @GetMapping("/api/jobs/{id}")
    JobResponse getJobById(@PathVariable("id") Long id);

    @GetMapping("/api/jobs/{id}/summary")
    JobSummaryResponse getJobSummaryById(@PathVariable("id") Long id);
}
