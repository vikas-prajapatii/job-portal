package com.noir.job.payload;

import lombok.Data;
import java.util.List;

@Data
public class ResumeParseResponse {
    private PersonalInfo personalInfo;
    private String summary;
    private List<WorkExperience> workExperiences;
    private List<Education> educations;
    private List<String> skills;
    private List<Certification> certifications;
    private List<Language> languages;

    @Data
    public static class PersonalInfo {
        private String fullName;
        private String email;
        private String phone;
        private String location;
        private String linkedIn;
        private String github;
        private String website;
    }

    @Data
    public static class WorkExperience {
        private String companyName;
        private String jobTitle;
        private String startDate;
        private String endDate;
        private boolean isCurrent;
        private String description;
    }

    @Data
    public static class Education {
        private String schoolName;
        private String degree;
        private String field;
        private String startDate;
        private String endDate;
    }

    @Data
    public static class Certification {
        private String name;
        private String issuer;
        private String issueDate;
    }

    @Data
    public static class Language {
        private String language;
        private String proficiency;
    }
}
