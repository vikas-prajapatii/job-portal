package com.noir.job.payload;

import lombok.Data;
import java.util.List;

@Data
public class CoverLetterRequest {
    private String jobTitle;
    private String jobDescription;
    private String targetCompanyName;
    private String candidateName;
    private String candidateSummary;
    private List<String> candidateSkills;
    private List<String> candidateExperience;
}
