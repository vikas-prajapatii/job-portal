package com.noir.job.payload;

import lombok.Data;
import java.util.List;

@Data
public class SalaryRangeRequest {
    private String title;
    private List<String> skills;
    private String experienceLevel;
    private String jobType;
    private String location;
}
