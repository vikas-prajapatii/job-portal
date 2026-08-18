package com.noir.job.payload;

import lombok.Data;

@Data
public class SaveJobRequest {
    private Long jobId;
    private Long companyId;
    private String notes;
}
