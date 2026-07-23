package com.noir.job.mapper;

import com.noir.job.domain.CompanyStatus;
import com.noir.job.dto.CompanyResponse;
import com.noir.job.dto.SocialLinkResponse;
import com.noir.job.model.Company;
import com.noir.job.model.SocialLink;

import java.util.Collections;
import java.util.List;

public class CompanyMapper {

    public static SocialLinkResponse toSocialLinkResponse(SocialLink socialLink) {
        if (socialLink == null) {
            return null;
        }
        return SocialLinkResponse.builder()
                .platform(socialLink.getPlatform())
                .url(socialLink.getUrl())
                .build();
    }

    public static CompanyResponse toResponse(Company company) {
        if (company == null) {
            return null;
        }

        List<SocialLinkResponse> socialLinks = company.getSocialLinks() == null
                ? Collections.emptyList()
                : company.getSocialLinks().stream()
                .map(CompanyMapper::toSocialLinkResponse)
                .toList();

        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .slug(company.getSlug())
                .description(company.getDescription())
                .tagLine(company.getTagLine())
                .logoUrl(company.getLogoUrl())
                .coverImageUrl(company.getCoverImageUrl())
                .website(company.getWebsite())
                .email(company.getEmail())
                .phone(company.getPhone())
                .founderYear(company.getFoundedYear())
                .companySize(company.getCompanySize())
                .companyType(company.getCompanyType())
                .industryType(company.getIndustryType())
                .companyStatus(company.getCompanyStatus())
                .verified(company.isVerified())
                .ownerId(company.getOwnerId())
                .socialLinkResponses(socialLinks)
                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())
                .build();
    }
}
