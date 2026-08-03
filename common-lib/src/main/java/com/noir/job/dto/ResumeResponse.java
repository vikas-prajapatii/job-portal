package com.noir.job.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.noir.job.domain.ResumeTemplate;
import com.noir.job.domain.ResumeVisibility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private PersonalInfoResponse personalInfo;
    private String summary;
    private Integer completionScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

//    private List<ResumeSkillResponse> skills;
//    private List<WorkExperienceResponse> workExperiences;
//    private List<EducationResponse> educations;

}
