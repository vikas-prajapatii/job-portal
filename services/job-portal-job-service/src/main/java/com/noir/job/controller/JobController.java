package com.noir.job.controller;

import com.noir.job.dto.ApiResponse;
import com.noir.job.dto.JobRequest;
import com.noir.job.dto.JobResponse;
import com.noir.job.payload.JobSearchRequest;
import com.noir.job.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<JobResponse> createJob(
            @RequestHeader("X-User-Id") Long employerId,
            @RequestBody @Valid JobRequest req) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body((jobService.createJob(employerId, req)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(
            @PathVariable Long id) throws Exception{
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @GetMapping
    public ResponseEntity<List<JobResponse>> getJobs(
            JobSearchRequest request) {
        return ResponseEntity.ok(jobService.getJobs(request));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobResponse>> getJobByCompany(
            @PathVariable Long companyId) {
        return ResponseEntity.ok(jobService.getJobByCompany(companyId));
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<JobResponse> updateJob(
            @PathVariable Long jobId,
            @RequestHeader("X-User-Id") Long employerId,
            @RequestBody @Valid JobRequest req) throws Exception {
        return ResponseEntity.ok(jobService.updateJob(jobId, employerId, req));
    }

    @PatchMapping("/{jobId}/publish")
    public ResponseEntity<JobResponse> publishJob(
            @PathVariable Long jobId,
            @RequestHeader("X-User-Id") Long employerId) throws Exception {
        return ResponseEntity.ok(jobService.publishJob(jobId, employerId));
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<ApiResponse> deleteJob(
            @PathVariable Long jobId,
            @RequestHeader("X-User-Id") Long employerId) throws Exception {
        jobService.deleteJob(jobId, employerId);
        return ResponseEntity.ok(new ApiResponse("Job deleted successfully", true));
    }

    @PatchMapping("/{jobId}/close")
    public ResponseEntity<JobResponse> closeJob(
            @PathVariable Long jobId,
            @RequestHeader("X-User-Id") Long employerId) throws Exception {
        return ResponseEntity.ok(jobService.closeJob(jobId, employerId));
    }

    @GetMapping("/admin")
    public ResponseEntity<List<JobResponse>> getAllJobsAdmin() {
        return ResponseEntity.ok(jobService.getAllJobsAdmin());
    }
}
