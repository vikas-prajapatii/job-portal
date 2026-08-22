package com.noir.job.service;

import com.noir.job.common.domain.CompanyStatus;
import com.noir.job.common.domain.CompanyType;
import com.noir.job.common.domain.IndustryType;
import com.noir.job.common.dto.response.CompanyResponse;
import com.noir.job.common.dto.response.CompanySummaryResponse;
import com.noir.job.common.exception.CompanyException;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.CompanyRequest;
import com.noir.job.entity.Company;

import java.util.List;

public interface CompanyService {

    CompanyResponse createCompany(Long ownerId, CompanyRequest req) throws CompanyException;

    CompanyResponse getCompanyById(Long id) throws ResourceNotFoundException;

    CompanySummaryResponse getCompanySummaryById(Long id) throws ResourceNotFoundException;

    CompanyResponse getMyCompany(Long ownerId) throws ResourceNotFoundException;

    List<CompanyResponse> getAllCompanies(
            CompanyType companyType,
            IndustryType industryType,
            CompanyStatus status);

    CompanyResponse updateCompany(Long companyId,
                                  Long ownerId, CompanyRequest req)
            throws ResourceNotFoundException, CompanyException;

    CompanyResponse verifyCompany(Long companyId) throws ResourceNotFoundException;

    CompanyResponse deactivateCompany(Long companyId) throws ResourceNotFoundException;

    void deleteCompany(Long companyId, Long ownerId) throws ResourceNotFoundException, CompanyException;

    /** Used internally by other services (e.g. location service). */
    Company getCompanyEntityById(Long id) throws ResourceNotFoundException;
}
