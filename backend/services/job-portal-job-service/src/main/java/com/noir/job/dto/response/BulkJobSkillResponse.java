package com.noir.job.dto.response;

import com.noir.job.common.dto.response.JobSkillResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BulkJobSkillResponse {

    private int totalRequested;
    private int totalSucceeded;
    private int totalFailed;

    /** Successfully created skills. */
    private List<JobSkillResponse> succeeded;

    /** Skills that failed, with their index and reason. */
    private List<BulkJobSkillFailure> failed;
}
