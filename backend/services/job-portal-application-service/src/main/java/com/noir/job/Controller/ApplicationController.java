package com.noir.job.Controller;

import com.noir.job.dto.ApplicationResponse;
import com.noir.job.payload.CompanyApplicationFilterRequest;
import com.noir.job.payload.CreateApplicationRequest;
import com.noir.job.payload.UpdateApplicationStatusRequest;
import com.noir.job.payload.WithdrawApplicationRequest;
import com.noir.job.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid CreateApplicationRequest req) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(applicationService.createApplication(candidateId, req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponse> getApplicationById(
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ApplicationResponse>> getMyApplications(
            @RequestHeader("X-User-Id") Long candidateId) {
        return ResponseEntity.ok(applicationService.getMyApplications(candidateId));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsForJob(
            @PathVariable Long jobId) {
        return ResponseEntity.ok(applicationService.getApplicationsForJob(jobId));
    }

    @GetMapping("/company")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsForCompany(
            @RequestHeader("X-User-Id") Long userId,
            CompanyApplicationFilterRequest filter) {
        return ResponseEntity.ok(applicationService.getApplicationsForCompany(userId, filter));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApplicationResponse> updateStatus(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long employerId,
            @RequestBody @Valid UpdateApplicationStatusRequest req) throws Exception {
        return ResponseEntity.ok(applicationService.updateStatus(id, employerId, req.getStatus()));
    }

    @PatchMapping("/{id}/withdraw")
    public ResponseEntity<ApplicationResponse> withdraw(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid WithdrawApplicationRequest req) throws Exception {
        return ResponseEntity.ok(applicationService.withdraw(id, candidateId, req));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<ApplicationResponse> markAsRead(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long employerId) throws Exception {
        return ResponseEntity.ok(applicationService.markAsRead(id, employerId));
    }

    @PatchMapping("/{id}/star")
    public ResponseEntity<ApplicationResponse> toggleStar(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long employerId) throws Exception {
        return ResponseEntity.ok(applicationService.toggleStar(id, employerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long candidateId) throws Exception {
        applicationService.deleteApplication(id, candidateId);
        return ResponseEntity.noContent().build();
    }
}
