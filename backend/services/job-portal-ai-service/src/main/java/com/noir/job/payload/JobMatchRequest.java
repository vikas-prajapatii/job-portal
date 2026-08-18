package com.noir.job.payload;

import lombok.Data;
import java.util.List;

@Data
public class JobMatchRequest {
    private List<String> candidateSkills;
    private List<String> jobSkills;
    private List<String> preferredWorkModes;
    private List<String> preferredJobTypes;
    private Double minSalary;
    private String candidateExperienceLevel;
    private String jobTitle;
    private String workMode;
    private String jobType;
    private Double salaryMin;
    private Double salaryMax;
    private String currency;
    private String industry;
    private String jobExperienceLevel;
}
