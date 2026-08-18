package com.noir.job.payload;

import lombok.Data;
import java.util.List;

@Data
public class JobAlertSuggestRequest {
    private List<String> skills;
    private List<String> previousJobTitles;
    private List<String> educations;
    private String experienceLevel;
}
