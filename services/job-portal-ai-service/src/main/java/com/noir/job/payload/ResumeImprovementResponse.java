package com.noir.job.payload;

import lombok.Data;
import java.util.List;

@Data
public class ResumeImprovementResponse {
    private int overallScore;
    private List<Improvement> improvements;
    private List<String> strengths;
    private String summary;

    @Data
    public static class Improvement {
        private String section;
        private String issue;
        private String suggestion;
        private String priority;
    }
}
