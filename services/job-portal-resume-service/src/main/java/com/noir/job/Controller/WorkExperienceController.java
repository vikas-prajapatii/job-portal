package com.noir.job.Controller;

import com.noir.job.dto.ApiResponse;
import com.noir.job.dto.WorkExperienceResponse;
import com.noir.job.payload.AddWorkExperienceRequest;
import com.noir.job.repository.WorkExperienceRepository;
import com.noir.job.service.ResumeService;
import com.noir.job.service.WorkExperienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/work-experiences")
public class WorkExperienceController {
    private final WorkExperienceService workExperienceService;
    @PostMapping
    public ResponseEntity<WorkExperienceResponse> addWorkExperience(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid AddWorkExperienceRequest req) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workExperienceService.addWorkExperience(resumeId, candidateId, req));
    }

    @GetMapping
    public ResponseEntity<List<WorkExperienceResponse>> getWorkExperiences(
            @PathVariable Long resumeId) throws Exception {
        return ResponseEntity.ok(workExperienceService.getWorkExperiences(resumeId));
    }

    @PutMapping("/{experienceId}")
    public ResponseEntity<WorkExperienceResponse> updateWorkExperience(
            @PathVariable Long resumeId,
            @PathVariable Long experienceId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid AddWorkExperienceRequest req) throws Exception {
        return ResponseEntity.ok(
                workExperienceService.updateWorkExperience(experienceId, resumeId, candidateId, req));
    }

    @DeleteMapping("/{experienceId}")
    public ResponseEntity<ApiResponse> deleteWorkExperience(
            @PathVariable Long resumeId,
            @PathVariable Long experienceId,
            @RequestHeader("X-User-Id") Long candidateId) throws Exception {
        workExperienceService.deleteWorkExperience(experienceId, resumeId, candidateId);
        return ResponseEntity.ok(new ApiResponse("Work experience deleted successfully", true));
    }
}
