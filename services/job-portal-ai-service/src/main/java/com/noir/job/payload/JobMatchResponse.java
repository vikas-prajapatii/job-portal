package com.noir.job.payload;

import lombok.Data;
import java.util.List;

@Data
public class JobMatchResponse {
    private int matchScore;
    private List<String> matchedCriteria;
    private List<String> unmatchedCriteria;
    private String recommendation;
    private String summary;
}
