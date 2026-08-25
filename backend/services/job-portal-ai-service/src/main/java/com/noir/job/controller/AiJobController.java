package com.noir.job.controller;

import com.noir.job.common.dto.response.ApiResponse;
import com.noir.job.dto.request.HiringInsightsRequest;
import com.noir.job.dto.request.JobDescriptionRequest;
import com.noir.job.dto.request.SalaryRangeRequest;
import com.noir.job.dto.response.AiTextResponse;
import com.noir.job.dto.response.HiringInsightsResponse;
import com.noir.job.dto.response.SalaryRangeResponse;
import com.noir.job.service.JobAiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/job")
@RequiredArgsConstructor
public class AiJobController {

    private final JobAiService jobAiService;

    @PostMapping("/describe")
    public ResponseEntity<ApiResponse<AiTextResponse>>
    generateJobDescription(
            @Valid @RequestBody JobDescriptionRequest request) {
        AiTextResponse response = jobAiService.generateJobDescription(request);
        return ResponseEntity.ok(ApiResponse.success("Job description generated", response));
    }

    @GetMapping("/requirements")
    public ResponseEntity<ApiResponse<AiTextResponse>> generateJobRequirements(
            @RequestParam String title,
            @RequestParam(required = false) String category) {
        AiTextResponse response = jobAiService.generateJobRequirements(title, category);
        return ResponseEntity.ok(ApiResponse.success("Requirements generated", response));
    }

    @PostMapping("/salary-suggestion")
    public ResponseEntity<ApiResponse<SalaryRangeResponse>> suggestSalary(
            @Valid @RequestBody SalaryRangeRequest request) {
        SalaryRangeResponse response = jobAiService.suggestSalaryRange(request);
        return ResponseEntity.ok(ApiResponse.success("Salary range suggested", response));
    }

    @GetMapping("/skills-recommendation")
    public ResponseEntity<ApiResponse<AiTextResponse>> recommendSkills(
            @RequestParam String title,
            @RequestParam(required = false) String description) {
        AiTextResponse response = jobAiService
                .recommendSkillsForJob(title, description);
        return ResponseEntity.ok(ApiResponse.success("Skills recommended", response));
    }

    @GetMapping("/responsibilities")
    public ResponseEntity<ApiResponse<AiTextResponse>> generateResponsibilities(
            @RequestParam String title,
            @RequestParam(required = false) String category) {
        AiTextResponse response = jobAiService.generateJobResponsibilities(title, category);
        return ResponseEntity.ok(ApiResponse.success("Responsibilities generated", response));
    }


    @GetMapping("/benefits")
    public ResponseEntity<ApiResponse<AiTextResponse>> generateBenefits(
            @RequestParam String title,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String jobType) {
        AiTextResponse response = jobAiService.generateJobBenefits(title, category, jobType);
        return ResponseEntity.ok(ApiResponse.success("Benefits generated", response));
    }

 
    @GetMapping("/tags-recommendation")
    public ResponseEntity<ApiResponse<AiTextResponse>> recommendTags(
            @RequestParam String title,
            @RequestParam(required = false) String description) {
        AiTextResponse response = jobAiService.recommendTagsForJob(title, description);
        return ResponseEntity.ok(ApiResponse.success("Tags recommended", response));
    }

}
