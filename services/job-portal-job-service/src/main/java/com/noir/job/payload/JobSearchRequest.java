package com.noir.job.payload;

import com.noir.job.domain.ExperienceLevel;
import com.noir.job.domain.JobStatus;
import com.noir.job.domain.JobType;
import com.noir.job.domain.WorkMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobSearchRequest {
    private String keyword;
    private Long categoryId;
    private List<Long> skillId;
    private List<Long> tagId;
    private Long companyId;
    private String location;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private JobType jobType;
    private WorkMode workMode;
    private ExperienceLevel experienceLevel;
    private JobStatus status;
    private Integer minOpenings;
    private Integer maxOpenings;

}
