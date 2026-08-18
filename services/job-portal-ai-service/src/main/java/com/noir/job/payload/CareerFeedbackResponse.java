package com.noir.job.payload;

import lombok.Data;
import java.util.List;

@Data
public class CareerFeedbackResponse {
    private int profileStrength;
    private List<String> shortlistingIssues;
    private List<Improvement> improvements;
    private List<TargetJob> targetJobs;
    private String overallSummary;

    @Data
    public static class Improvement {
        private String area;
        private String issue;
        private String action;
        private String priority;
    }

    @Data
    public static class TargetJob {
        private String jobTitle;
        private String reason;
        private String skillMatch;
    }
}
