package com.noir.job.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.noir.job.common.domain.ResumeTemplate;
import com.noir.job.common.domain.ResumeVisibility;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Shared resume DTO — used by other services (e.g. application-service) via Feign.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResumeResponse {

    private Long id;
    private Long candidateId;
    private String title;
    private ResumeTemplate template;
    private ResumeVisibility visibility;
    private Boolean isDefault;
    private String summary;
    private String uploadedFileUrl;
    private String uploadedFileName;
    private Integer completionScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Screening fields — populated by resume-service, used by application-service for AI scoring
    private List<ResumeSkillResponse> skills;
    private List<WorkExperienceResponse> workExperiences;
    private List<EducationResponse> educations;
}
