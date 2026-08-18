package com.noir.job.controller;

import com.noir.job.payload.*;
import com.noir.job.service.JobAiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/job")
@RequiredArgsConstructor
public class JobAiController {

    private final JobAiService jobAiService;
    @PostMapping("/describe")
    public ResponseEntity<AiTextResponse> generateJobDescription(
            @Valid @RequestBody JobDescriptionRequest request) throws Exception {
        AiTextResponse response = jobAiService.generateJobDescription(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/requirements")
    public ResponseEntity<AiTextResponse> generateJobRequirements(
            @RequestParam String title,
            @RequestParam(required = false) String category) throws Exception {
        AiTextResponse response = jobAiService.generateJobRequirements(title, category);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/salary-suggestion")
    public ResponseEntity<SalaryRangeResponse> suggestSalaryRange(
            @Valid @RequestBody SalaryRangeRequest request) throws Exception {
        SalaryRangeResponse response = jobAiService.suggestSalaryRange(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/skills-recommendation")
    public ResponseEntity<AiTextResponse> recommendSkillsForJob(
            @RequestParam String title,
            @RequestParam(required = false) String description) throws Exception {
        AiTextResponse response = jobAiService.recommendSkillsForJob(title, description);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/responsibilities")
    public ResponseEntity<AiTextResponse> generateJobResponsibilities(
            @RequestParam String title,
            @RequestParam(required = false) String category) throws Exception {
        AiTextResponse response = jobAiService.generateJobResponsibilities(title, category);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/benefits")
    public ResponseEntity<AiTextResponse> generateJobBenefits(
            @RequestParam String title,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String jobType) throws Exception {
        AiTextResponse response = jobAiService.generateJobBenefits(title, category, jobType);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/tags-recommendation")
    public ResponseEntity<AiTextResponse> recommendTagsForJob(
            @RequestParam String title,
            @RequestParam(required = false) String description) throws Exception {
        AiTextResponse response = jobAiService.recommendTagsForJob(title, description);
        return ResponseEntity.ok(response);
    }
}
