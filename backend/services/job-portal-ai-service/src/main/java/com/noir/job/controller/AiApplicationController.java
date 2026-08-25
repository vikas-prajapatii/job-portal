package com.noir.job.controller;

import com.noir.job.common.dto.response.ApiResponse;
import com.noir.job.dto.request.BulkScreeningRequest;
import com.noir.job.dto.request.CoverLetterRequest;
import com.noir.job.dto.request.InterviewQuestionsRequest;
import com.noir.job.dto.request.ScreeningScoreRequest;
import com.noir.job.dto.request.SkillsGapRequest;
import com.noir.job.dto.response.AiTextResponse;
import com.noir.job.dto.response.BulkScreeningResponse;
import com.noir.job.dto.response.InterviewQuestionsResponse;
import com.noir.job.dto.response.ScreeningScoreResponse;
import com.noir.job.dto.response.SkillsGapResponse;
import com.noir.job.service.ApplicationAiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai/application")
@RequiredArgsConstructor
public class AiApplicationController {

    private final ApplicationAiService applicationAiService;


    @PostMapping("/cover-letter")
    public ResponseEntity<ApiResponse<AiTextResponse>> generateCoverLetter(
            @Valid @RequestBody CoverLetterRequest request) {
        AiTextResponse response = applicationAiService.generateCoverLetter(request);
        return ResponseEntity.ok(ApiResponse.success("Cover letter generated", response));
    }


    @PostMapping("/screening-score")
    public ResponseEntity<ApiResponse<ScreeningScoreResponse>> scoreCandidate(
            @RequestBody ScreeningScoreRequest request) {
        ScreeningScoreResponse response = applicationAiService.scoreCandidate(request);
        return ResponseEntity.ok(ApiResponse.success("Candidate screened", response));
    }



    @PostMapping("/skills-gap")
    public ResponseEntity<ApiResponse<SkillsGapResponse>> analyzeSkillsGap(
            @Valid @RequestBody SkillsGapRequest request) {
        SkillsGapResponse response = applicationAiService.analyzeSkillsGap(request);
        return ResponseEntity.ok(ApiResponse.success("Skills gap analyzed", response));
    }

    @PostMapping("/summarize-notes")
    public ResponseEntity<ApiResponse<AiTextResponse>> summarizeNotes(
            @RequestBody List<String> notes) {
        AiTextResponse response = applicationAiService.summarizeApplicationNotes(notes);
        return ResponseEntity.ok(ApiResponse.success("Notes summarized", response));
    }
}
