package com.noir.job.client;

import com.noir.job.common.dto.response.ApiResponse;
import com.noir.job.dto.request.ScreeningScoreRequest;
import com.noir.job.dto.response.ScreeningScoreResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "job-portal-ai-service")
public interface AiClient {

    @PostMapping("/api/ai/application/screening-score")
    ApiResponse<ScreeningScoreResponse> scoreCandidate(@RequestBody ScreeningScoreRequest request);
}
