package com.noir.job.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.noir.job.domain.JobType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkExperienceResponse {
    private Long id;
    private String companyName;
    private String companyLogoUrl;
    private String jobTitle;
    private JobType employmentType;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isCurrentJob;
    private String description;
    private List<String> technologies;
    private Integer displayOrder;
}
