package com.noir.job.payload;

import com.noir.job.domain.AiShortlistStatus;
import com.noir.job.domain.ApplicationStatus;
import lombok.Data;

@Data
public class CompanyApplicationFilterRequest {
    private Long jobId;
    private ApplicationStatus status;

    private boolean isStarred = false;
    private AiShortlistStatus aiShortlistStatus;
    private Integer minAiScore;
    private String sortBy;
}
