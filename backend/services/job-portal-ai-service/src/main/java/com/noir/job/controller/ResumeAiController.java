package com.noir.job.controller;

import com.noir.job.payload.*;
import com.noir.job.service.ResumeAiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/resume")
@RequiredArgsConstructor
public class ResumeAiController {

    private final ResumeAiService resumeAiService;

    @PostMapping("/summary")
    public ResponseEntity<AiTextResponse> generateSummary(
            @RequestBody ResumeSummaryRequest request) throws Exception {
        AiTextResponse response = resumeAiService.generateProfessionalSummary(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/experience-bullets")
    public ResponseEntity<WorkExperienceBulletsResponse> generateBullets(
            @Valid @RequestBody WorkExperienceBulletRequest request) throws Exception {
        WorkExperienceBulletsResponse response = resumeAiService.generateWorkExperienceBullets(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/parse")
    public ResponseEntity<ResumeParseResponse> parseResume(
            @Valid @RequestBody ResumeParseRequest request) throws Exception {
        ResumeParseResponse response = resumeAiService.parseResume(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/improvements")
    public ResponseEntity<ResumeImprovementResponse> getImprovements(
            @Valid @RequestBody ResumeImprovementRequest request) throws Exception {
        ResumeImprovementResponse response = resumeAiService.getResumeImprovementTips(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/career-feedback")
    public ResponseEntity<CareerFeedbackResponse> getCareerFeedback(
            @Valid @RequestBody CareerFeedbackRequest request) throws Exception {
        CareerFeedbackResponse response = resumeAiService.getCareerFeedback(request);
        return ResponseEntity.ok(response);
    }
}
