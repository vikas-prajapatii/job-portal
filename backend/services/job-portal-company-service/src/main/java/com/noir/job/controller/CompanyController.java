package com.noir.job.controller;

import com.noir.job.common.domain.CompanyStatus;
import com.noir.job.common.domain.CompanyType;
import com.noir.job.common.domain.IndustryType;
import com.noir.job.common.dto.response.ApiResponse;
import com.noir.job.common.dto.response.CompanyResponse;
import com.noir.job.common.dto.response.CompanySummaryResponse;
import com.noir.job.common.exception.CompanyException;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.CompanyRequest;
import com.noir.job.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    // ── Create ────────────────────────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(
            @RequestHeader("X-User-Id") Long ownerId,
            @RequestBody @Valid CompanyRequest req) throws CompanyException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(companyService.createCompany(ownerId, req));
    }

    // ── Read ──────────────────────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(
            @PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @GetMapping("/summary/{id}")
    public ResponseEntity<CompanySummaryResponse> getCompanySummaryById(
            @PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(companyService.getCompanySummaryById(id));
    }


    @GetMapping("/my")
    public ResponseEntity<CompanyResponse> getMyCompany(
            @RequestHeader("X-User-Id") Long ownerId) throws ResourceNotFoundException {
        System.out.println("OwnerId = " + ownerId);

        return ResponseEntity.ok(companyService.getMyCompany(ownerId));
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompanies(
            @RequestParam(required = false) CompanyType companyType,
            @RequestParam(required = false) IndustryType industryType,
            @RequestParam(required = false) CompanyStatus status) {
        return ResponseEntity.ok(companyService.getAllCompanies(companyType, industryType, status));
    }

    // ── Update ────────────────────────────────────────────────────────────────

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long ownerId,
            @RequestBody @Valid CompanyRequest req)
            throws ResourceNotFoundException, CompanyException {
        return ResponseEntity.ok(companyService.updateCompany(id, ownerId, req));
    }

    // ── Admin actions ─────────────────────────────────────────────────────────

    @PatchMapping("/{id}/verify")
    public ResponseEntity<CompanyResponse> verifyCompany(
            @PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(companyService.verifyCompany(id));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<CompanyResponse> deactivateCompany(
            @PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(companyService.deactivateCompany(id));
    }

    // ── Delete ────────────────────────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCompany(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long ownerId)
            throws ResourceNotFoundException, CompanyException {
        companyService.deleteCompany(id, ownerId);
        return ResponseEntity.ok(new ApiResponse("Company deleted successfully", true));
    }
}
