package com.noir.job.service.impl;

import com.noir.job.domain.CompanyStatus;
import com.noir.job.domain.CompanyType;
import com.noir.job.domain.IndustryType;
import com.noir.job.dto.CompanyRequest;
import com.noir.job.dto.CompanyResponse;
import com.noir.job.dto.SocialLinkResponse;
import com.noir.job.mapper.CompanyMapper;
import com.noir.job.model.Company;
import com.noir.job.model.SocialLink;
import com.noir.job.repository.CompanyRepository;
import com.noir.job.service.CompanyService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    @Override
    public CompanyResponse createCompany(Long ownerId, CompanyRequest req) throws Exception {
        if(companyRepository.existsByOwnerId(ownerId)){
            throw  new Exception("Company already exists." + "only one company per account is allowed");

        }
        if(companyRepository.existsByName(req.getName())){
            throw new Exception("Company already exists." + "plz use different name");
        }
        if(req.getRegistrationNumber() != null && companyRepository.existsByRegistrationNumber(req.getRegistrationNumber())){
            throw new Exception("Company already exists." + "plz use different registration number");
        }
        String slug = generateUniqueSlug(req.getName());

        Company company = Company.builder()
                .name(req.getName())
                .slug(slug)
                .tagLine(req.getTagLine())
                .description(req.getDescription())
                .logoUrl(req.getLogoUrl())
                .coverImageUrl(req.getCoverImageUrl())
                .website(req.getWebsite())
                .email(req.getEmail())
                .phone(req.getPhone())
                .foundedYear(req.getFoundedYear())
                .companySize(req.getCompanySize())
                .companyType(req.getCompanyType())
                .industryType(req.getIndustryType())
                .registrationNumber(req.getRegistrationNumber())
                .ownerId(ownerId)
                .socialLinks(mapSocialLinks(req.getSocialLinks()))
                .build();

        Company saved = companyRepository.save(company);
        return CompanyMapper.toResponse(saved);
    }

    private List<SocialLink> mapSocialLinks(List<SocialLinkResponse> socialLinks) {
        if(socialLinks == null || socialLinks.isEmpty()){
            return new ArrayList<SocialLink>();
        }
        return socialLinks.stream()
                .map(e -> new SocialLink(e.getUrl(), e.getPlatform()))
                .collect(Collectors.toList());
    }

    private String generateUniqueSlug(String name) {
        String base = name.toLowerCase()
                .replaceAll("[^a-zA-Z0-9]", "").trim()
                .replaceAll("[\\s-]", "-");
        if(!companyRepository.existsBySlug(base)){
            return base;
        }
        int counter = 1;
        while (companyRepository.existsBySlug(base+"-"+counter)){
            counter++;
        }
        return base+"-"+counter;

    }

    @Override
    public CompanyResponse getCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Company not found with id: " + id)
        );
        return CompanyMapper.toResponse(company);
    }

    @Override
    public CompanyResponse getMyCompany(Long ownerId) {
        Company company = companyRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new RuntimeException("Company not found for owner id: " + ownerId)
        );
        return CompanyMapper.toResponse(company);
    }

    @Override
    public List<CompanyResponse> getAllCompanies(CompanyType companyType, IndustryType industryType, CompanyStatus companyStatus) {
        return companyRepository.findByFilters(
                companyType,
                industryType,
                companyStatus
        ).stream()
                .map(CompanyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest req) throws Exception {
        Company company = getCompanyEntityById(companyId);
        if (!company.getOwnerId().equals(ownerId)) {
            throw new RuntimeException("You are not authorized to update this company");
        }

        if (!company.getName().equals(req.getName())) {
            if (companyRepository.existsByName(req.getName())) {
                throw new Exception("Company name already exists: " + req.getName());
            }
            company.setName(req.getName());
            company.setSlug(generateUniqueSlug(req.getName()));
        }
        if(req.getRegistrationNumber() != null
                && !req.getRegistrationNumber().equals(company.getRegistrationNumber())
                && companyRepository.existsByRegistrationNumber(req.getRegistrationNumber())){
                   throw new Exception("company already exists, plz use different registrationn number");
        }

        company.setTagLine(req.getTagLine());
        company.setDescription(req.getDescription());
        company.setLogoUrl(req.getLogoUrl());
        company.setCoverImageUrl(req.getCoverImageUrl());
        company.setWebsite(req.getWebsite());
        company.setEmail(req.getEmail());
        company.setPhone(req.getPhone());
        company.setFoundedYear(req.getFoundedYear());
        company.setCompanySize(req.getCompanySize());
        company.setCompanyType(req.getCompanyType());
        company.setIndustryType(req.getIndustryType());
        company.setRegistrationNumber(req.getRegistrationNumber());
        company.setSocialLinks(mapSocialLinks(req.getSocialLinks()));

        Company saved = companyRepository.save(company);
        return CompanyMapper.toResponse(saved);
    }

    @Override
    public CompanyResponse verifyCompany(Long companyId) {
        Company company = getCompanyEntityById(companyId);
        company.setCompanyStatus(CompanyStatus.ACTIVE);
        company.setVerified(true);
        Company saved = companyRepository.save(company);
        return CompanyMapper.toResponse(saved);
    }

    @Override
    public CompanyResponse deactivateCompany(Long companyId) {
        Company company = getCompanyEntityById(companyId);
        company.setCompanyStatus(CompanyStatus.SUSPENDED);
        company.setVerified(false);
        Company saved = companyRepository.save(company);
        return CompanyMapper.toResponse(saved);
    }

    @Override
    public void deleteCompany(Long ownerId,Long companyId) throws Exception {
        Company company = getCompanyEntityById(companyId);
        assertOwner(company,ownerId);
        companyRepository.delete(company);
    }

    private void assertOwner(Company company, Long ownerId) throws Exception {
        if(!company.getOwnerId().equals(ownerId)){
            throw new Exception("you are not the owner of this company");
        }
    }

    @Override
    public Company getCompanyEntityById(Long id) {
        return companyRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Company not found with id: " + id)
        );
    }
}
