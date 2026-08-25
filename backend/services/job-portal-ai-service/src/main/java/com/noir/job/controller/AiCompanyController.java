package com.noir.job.controller;

import com.noir.job.common.dto.response.ApiResponse;
import com.noir.job.dto.request.CompanyDescriptionRequest;
import com.noir.job.dto.request.CompanyTaglineRequest;
import com.noir.job.dto.response.AiTextResponse;
import com.noir.job.dto.response.CompanyTaglineResponse;
import com.noir.job.service.CompanyAiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/company")
@RequiredArgsConstructor
public class AiCompanyController {

    private final CompanyAiService companyAiService;


    @PostMapping("/describe")
    public ResponseEntity<ApiResponse<AiTextResponse>> generateCompanyDescription(
            @Valid @RequestBody CompanyDescriptionRequest request) {
        AiTextResponse response = companyAiService.generateCompanyDescription(request);
        return ResponseEntity.ok(ApiResponse.success("Company description generated", response));
    }


    @PostMapping("/taglines")
    public ResponseEntity<ApiResponse<CompanyTaglineResponse>> generateTaglines(
            @Valid @RequestBody CompanyTaglineRequest request) {
        CompanyTaglineResponse response = companyAiService.generateCompanyTaglines(request);
        return ResponseEntity.ok(ApiResponse.success("Taglines generated", response));
    }
}
