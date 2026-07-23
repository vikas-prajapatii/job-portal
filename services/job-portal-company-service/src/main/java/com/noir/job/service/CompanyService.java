package com.noir.job.service;

import com.noir.job.domain.CompanyStatus;
import com.noir.job.domain.CompanyType;
import com.noir.job.domain.IndustryType;
import com.noir.job.dto.CompanyRequest;
import com.noir.job.dto.CompanyResponse;
import com.noir.job.model.Company;

import java.util.List;

public interface CompanyService {
    CompanyResponse createCompany(Long ownerId, CompanyRequest req) throws Exception;
    CompanyResponse getCompanyById(Long id);
    CompanyResponse getMyCompany(Long ownerId);
    List<CompanyResponse> getAllCompanies(CompanyType companyType,
                                          IndustryType industryType,
                                          CompanyStatus companyStatus);
    CompanyResponse updateCompany(Long companyId,Long ownerId, CompanyRequest req) throws Exception;
    CompanyResponse verifyCompany(Long companyId);
    CompanyResponse deactivateCompany(Long companyId);
    void deleteCompany(Long ownerId,Long companyId) throws Exception;
    Company getCompanyEntityById(Long id);

}
