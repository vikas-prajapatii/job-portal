package com.noir.job.dto;

import com.noir.job.domain.CompanySize;
import com.noir.job.domain.CompanyStatus;
import com.noir.job.domain.CompanyType;
import com.noir.job.domain.IndustryType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyResponse {
   private Long id;
   private String name;
   private String description;
   private String slug;
   private String tagLine;
   private String logoUrl;
   private String coverImageUrl;
   private String website;
   private String phone;
   private String email;
   private Integer founderYear;
   private CompanyType companyType;
   private CompanySize companySize;
   private IndustryType industryType;
   private CompanyStatus companyStatus;
   private boolean verified;
    private Long ownerId;

    private List<SocialLinkResponse> socialLinkResponses = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime verifiedAt;
    private LocalDateTime updatedAt;

}
