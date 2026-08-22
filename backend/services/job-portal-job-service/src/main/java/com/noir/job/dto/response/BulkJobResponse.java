package com.noir.job.dto.response;

import com.noir.job.common.dto.response.JobResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BulkJobResponse {

    private int totalRequested;
    private int totalSucceeded;
    private int totalFailed;

    /** Successfully created jobs. */
    private List<JobResponse> succeeded;

    /** Jobs that failed, with their index and reason. */
    private List<BulkJobFailure> failed;
}
