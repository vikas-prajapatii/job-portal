package com.noir.job.controller;

import com.noir.job.common.dto.response.JobPreferenceResponse;
import com.noir.job.dto.request.UpdateJobPreferenceRequest;
import com.noir.job.service.JobPreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences")
@RequiredArgsConstructor
public class JobPreferenceController {

    private final JobPreferenceService preferenceService;

    @GetMapping
    public ResponseEntity<JobPreferenceResponse> getOrCreatePreference(
            @RequestHeader("X-User-Id") Long candidateId) {
        return ResponseEntity.ok(preferenceService.getOrCreatePreference(candidateId));
    }

    @PutMapping
    public ResponseEntity<JobPreferenceResponse> updatePreference(
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody UpdateJobPreferenceRequest req) {
        return ResponseEntity.ok(preferenceService.updatePreference(candidateId, req));
    }
}
