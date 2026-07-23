package com.noir.job.controller;

import com.noir.job.domain.CompanyStatus;
import com.noir.job.domain.CompanyType;
import com.noir.job.domain.IndustryType;
import com.noir.job.dto.ApiResponse;
import com.noir.job.dto.CompanyRequest;
import com.noir.job.dto.CompanyResponse;
import com.noir.job.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(
            @RequestHeader("X-User-Id") Long ownerId,
            @RequestBody @Valid CompanyRequest req) throws Exception {
        return ResponseEntity.ok(companyService.createCompany(ownerId, req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(
            @PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getCompanyById(id));
    }

    @GetMapping("/my")
    public ResponseEntity<CompanyResponse> getMyCompany(
            @RequestHeader("X-User-Id") Long ownerId) throws Exception {
        return ResponseEntity.ok(companyService.getMyCompany(ownerId));
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompanies(
            @RequestParam(required = false) CompanyType type,
            @RequestParam(required = false) IndustryType industry,
            @RequestParam(required = false) CompanyStatus status) {
        return ResponseEntity.ok(companyService.getAllCompanies(type, industry, status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long ownerId,
            @RequestBody @Valid CompanyRequest req) throws Exception {
        return ResponseEntity.ok(companyService.updateCompany(id, ownerId, req));
    }

    @PatchMapping("/{id}/verify")
    public ResponseEntity<CompanyResponse> verifyCompany(
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(companyService.verifyCompany(id));
    }

    @PatchMapping("/{companyId}/deactivate")
    public ResponseEntity<CompanyResponse> deactivateCompany(
            @PathVariable Long companyId) throws Exception {
        return ResponseEntity.ok(companyService.deactivateCompany(companyId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCompany(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long ownerId)
            throws Exception {
        companyService.deleteCompany(ownerId, id);
        return ResponseEntity.ok(new ApiResponse("company deleted successfully", true));
    }
}
