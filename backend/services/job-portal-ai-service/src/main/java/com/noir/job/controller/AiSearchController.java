package com.noir.job.controller;

import com.noir.job.common.dto.response.ApiResponse;
import com.noir.job.dto.request.JobAlertSuggestRequest;
import com.noir.job.dto.request.JobMatchRequest;
import com.noir.job.dto.request.SearchEnhanceRequest;
import com.noir.job.dto.response.JobAlertSuggestResponse;
import com.noir.job.dto.response.JobMatchResponse;
import com.noir.job.dto.response.SearchEnhanceResponse;
import com.noir.job.service.SearchAiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/search")
@RequiredArgsConstructor
public class AiSearchController {

    private final SearchAiService searchAiService;

    /**
     * Phase 3: Convert natural language search query into structured filters.
     * Called by job-service POST /api/jobs/search (which already has the stub).
     * Also called directly from the Jobs search bar in the frontend.
     * POST /api/ai/search/enhance
     */
    @PostMapping("/enhance")
    public ResponseEntity<ApiResponse<SearchEnhanceResponse>> enhanceSearch(
            @Valid @RequestBody SearchEnhanceRequest request) {
        SearchEnhanceResponse response = searchAiService.enhanceSearch(request);
        return ResponseEntity.ok(ApiResponse.success("Search enhanced", response));
    }

    /**
     * Phase 4: Calculate how well a job matches a candidate's preferences.
     * Used in the SavedJobs and Jobs listing to show match percentage.
     * POST /api/ai/search/job-match
     */
    @PostMapping("/job-match")
    public ResponseEntity<ApiResponse<JobMatchResponse>> calculateJobMatch(
            @RequestBody JobMatchRequest request) {
        JobMatchResponse response = searchAiService.calculateJobMatch(request);
        return ResponseEntity.ok(ApiResponse.success("Job match calculated", response));
    }

    /**
     * Phase 4: Suggest optimal job alert criteria based on candidate profile.
     * Used when candidate creates a new Job Alert.
     * POST /api/ai/search/alert-suggestion
     */
    @PostMapping("/alert-suggestion")
    public ResponseEntity<ApiResponse<JobAlertSuggestResponse>> suggestAlertCriteria(
            @RequestBody JobAlertSuggestRequest request) {
        JobAlertSuggestResponse response = searchAiService.suggestJobAlertCriteria(request);
        return ResponseEntity.ok(ApiResponse.success("Alert criteria suggested", response));
    }
}
