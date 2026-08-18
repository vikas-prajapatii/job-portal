package com.noir.job.mapper;

import com.noir.job.dto.JobTagResponse;
import com.noir.job.model.JobTags;

public class JobTagMapper {
    public static JobTagResponse toResponse(JobTags jobTags) {
        return JobTagResponse.builder()
                .id(jobTags.getId())
                .name(jobTags.getName())
                .slug(jobTags.getSlug())
                .build();
    }
}
