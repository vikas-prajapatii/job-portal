package com.noir.job.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter

public class ResumeSummaryRequest {
    private String targetJobTitle;
    private List<WorkExperienceInfo> workExperience;
    private List<EducationInfo> education;
    private List<String> skills;
    private Integer yearOfExperience;
    @Data
    public static class WorkExperienceInfo {
       private String jobTitle;
       private String company;
       private String description;
    }
    @Data
    public static class EducationInfo {
      private String degree;
      private String institutionName;
      private String fieldOfStudy;
    }
}
