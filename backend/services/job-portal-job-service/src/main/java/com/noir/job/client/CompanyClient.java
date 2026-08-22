package com.noir.job.client;

import com.noir.job.common.dto.response.CompanyResponse;
import com.noir.job.common.dto.response.CompanySummaryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "job-portal-company-service")
public interface CompanyClient {

    @GetMapping("/api/companies/my")
    CompanyResponse getMyCompany(@RequestHeader("X-User-Id") Long ownerId);

    @GetMapping("/api/companies/{id}")
    CompanyResponse getCompanyById(@PathVariable Long id);

    @GetMapping("/api/companies/summary/{id}")
    CompanySummaryResponse getCompanySummaryById(@PathVariable Long id);
}
