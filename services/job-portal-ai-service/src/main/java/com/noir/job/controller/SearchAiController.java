package com.noir.job.controller;

import com.noir.job.payload.*;
import com.noir.job.service.SearchAiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/search")
@RequiredArgsConstructor
public class SearchAiController {

    private final SearchAiService searchAiService;

    @PostMapping("/enhance")
    public ResponseEntity<SearchEnhanceResponse> enhanceSearch(
            @Valid @RequestBody SearchEnhanceRequest request) throws Exception {
        SearchEnhanceResponse response = searchAiService.enhanceSearch(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/job-match")
    public ResponseEntity<JobMatchResponse> calculateJobMatch(
            @RequestBody JobMatchRequest request) throws Exception {
        JobMatchResponse response = searchAiService.calculateJobMatch(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/alert-suggestion")
    public ResponseEntity<JobAlertSuggestResponse> suggestAlertCriteria(
            @RequestBody JobAlertSuggestRequest request) throws Exception {
        JobAlertSuggestResponse response = searchAiService.suggestJobAlertCriteria(request);
        return ResponseEntity.ok(response);
    }
}
