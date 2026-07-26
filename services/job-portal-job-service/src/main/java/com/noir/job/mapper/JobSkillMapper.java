package com.noir.job.mapper;

import com.noir.job.dto.JobSkillResponse;
import com.noir.job.model.JobSkill;
import com.noir.job.payload.JobSkillRequest;

public class JobSkillMapper {
    public static JobSkillResponse toJobSkillResponse(JobSkill skill) {
        return JobSkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .slug(skill.getSlug())
                .category(skill.getCategory())
                .active(skill.getActive())
                .build();
    }
}
