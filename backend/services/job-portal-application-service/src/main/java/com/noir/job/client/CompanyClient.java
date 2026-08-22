package com.noir.job.client;

import com.noir.job.common.dto.response.CompanyResponse;
import com.noir.job.common.dto.response.CompanySummaryResponse;
import com.noir.job.common.exception.ResourceNotFoundException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="job-portal-company-service")
public interface CompanyClient {

    @GetMapping("/api/companies/my")
    CompanyResponse getMyCompany(
            @RequestHeader("X-User-Id") Long ownerId) throws ResourceNotFoundException;

    @GetMapping("/api/companies/summary/{id}")
    CompanySummaryResponse getCompanySummaryById(@PathVariable("id") Long id);
}
