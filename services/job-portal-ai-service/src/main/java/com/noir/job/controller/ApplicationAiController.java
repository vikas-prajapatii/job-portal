package com.noir.job.controller;

import com.noir.job.payload.*;
import com.noir.job.service.ApplicationAiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai/application")
@RequiredArgsConstructor
public class ApplicationAiController {
    private final ApplicationAiService applicationAiService;
    @PostMapping("/cover-letter")
    public ResponseEntity<AiTextResponse> generateCoverLetter(
            @Valid @RequestBody CoverLetterRequest request) throws Exception {
        AiTextResponse response = applicationAiService.generateCoverLetter(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/screening-score")
    public ResponseEntity<ScreeningScoreResponse> scoreCandidate(
            @RequestBody ScreeningScoreRequest request) throws Exception {
        ScreeningScoreResponse response = applicationAiService.scoreCandidate(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/skills-gap")
    public ResponseEntity<SkillsGapResponse> analyzeSkillsGap(
            @Valid @RequestBody SkillsGapRequest request) throws Exception {
        SkillsGapResponse response = applicationAiService.analyzeSkillsGap(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/summarize-notes")
    public ResponseEntity<AiTextResponse> summarizeNotes(
            @RequestBody List<String> notes) throws Exception {
        AiTextResponse response = applicationAiService.summarizeApplicationNotes(notes);
        return ResponseEntity.ok(response);
    }
}
