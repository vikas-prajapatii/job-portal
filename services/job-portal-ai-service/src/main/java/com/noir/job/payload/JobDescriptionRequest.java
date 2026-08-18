package com.noir.job.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Data
@Getter
@Setter

public class JobDescriptionRequest {
    private String title;
    private List<String> skill;
    private String experienceLevel;
    private String jobType;
    private String workMode;
    private String category;
    private String additionalContext;

}
