package com.noir.job.payload;

import lombok.Data;
import java.util.List;

@Data
public class SkillsGapRequest {
    private String jobTitle;
    private List<String> candidateSkills;
    private List<String> requiredSkills;
}
