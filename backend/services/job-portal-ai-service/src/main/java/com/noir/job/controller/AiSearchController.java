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


    @PostMapping("/enhance")
    public ResponseEntity<ApiResponse<SearchEnhanceResponse>> enhanceSearch(
            @Valid @RequestBody SearchEnhanceRequest request) {
        SearchEnhanceResponse response = searchAiService.enhanceSearch(request);
        return ResponseEntity.ok(ApiResponse.success("Search enhanced", response));
    }


    @PostMapping("/job-match")
    public ResponseEntity<ApiResponse<JobMatchResponse>> calculateJobMatch(
            @RequestBody JobMatchRequest request) {
        JobMatchResponse response = searchAiService.calculateJobMatch(request);
        return ResponseEntity.ok(ApiResponse.success("Job match calculated", response));
    }


    @PostMapping("/alert-suggestion")
    public ResponseEntity<ApiResponse<JobAlertSuggestResponse>> suggestAlertCriteria(
            @RequestBody JobAlertSuggestRequest request) {
        JobAlertSuggestResponse response = searchAiService.suggestJobAlertCriteria(request);
        return ResponseEntity.ok(ApiResponse.success("Alert criteria suggested", response));
    }
}
