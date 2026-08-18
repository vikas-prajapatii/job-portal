package com.noir.job.payload;

import com.noir.job.domain.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AddWorkExperienceRequest {

    @NotBlank(message = "Company name is required")
    private String companyName;

    private String companyLogoUrl;

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    private JobType employmentType;
    private String location;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate endDate;

    @Builder.Default
    private Boolean isCurrentJob = false;

    private String description;
    private List<String> technologies;
    private Integer displayOrder;
}
