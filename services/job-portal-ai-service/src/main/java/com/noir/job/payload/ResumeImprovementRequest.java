package com.noir.job.payload;

import lombok.Data;

@Data
public class ResumeImprovementRequest {
    private String targetJobTitle;
    private String resumeContent;
}
