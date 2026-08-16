package com.noir.job.mapper;

import com.noir.job.dto.*;
import com.noir.job.model.Job;
import com.noir.job.model.JobTags;
import com.noir.job.model.embeddable.JobLocation;
import com.noir.job.model.embeddable.SalaryRange;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static com.noir.job.dto.JobResponse.*;

public class JobMapper {

    public static JobResponse toResponse(Job job) {
        return toResponse(job, null);
    }

    public static JobResponse toResponse(Job job, CompanyResponse companyResponse) {
        JobLocation location = job.getLocation();
        SalaryRange salary = job.getSalaryRange();
        Set<JobSkillResponse> skills = job.getSkills() == null?
                Collections.emptySet()
                :job.getSkills().stream().map(JobSkillMapper::toJobSkillResponse).collect(Collectors.toSet());

        Set<JobTagResponse> tags = job.getTags() == null?
                Collections.emptySet(): job.getTags().stream().map(JobTagMapper::toResponse)
                                        .collect(Collectors.toSet());
        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .requirements(job.getRequirements())
                .responsibilities(job.getResponsibilities())
                .employerId(job.getEmployerId())
                .benefits(job.getBenefits())
                .company(companyResponse)
                .skills(skills)
                .tags(tags)
                .category(JobCategoryMapper.toJobCategoryResponse(job.getCategory(),false))
                .employerId(job.getEmployerId())
                .address(location != null ? location.getAddress(): null)
                .city(location != null ? location.getCity(): null)
                .state(location != null ? location.getState(): null)
                .country(location != null ? location.getCountry(): null)
                .zipCode(location != null ? location.getZip(): null)
                .minSalary(salary != null ? salary.getMinSalary(): null)
                .maxSalary(salary != null ? salary.getMaxSalary(): null)
                .jobType(job.getJobType())
                .workMode(job.getWorkMode())
                .experienceLevel(job.getExperienceLevel())
                .status(job.getStatus())
                .openings(job.getOpenings())
                .applicationDeadline(job.getApplicationDeadline())
                .expiresAt(job.getExpiresAt())
                .active(job.getActive())
                .createdAt(job.getCreatedAt())
                .updatedAt(job.getUpdatedAt())
                .publishedAt(job.getPublishedAt())
                .closedAt(job.getClosedAt())
                .build();
    }

    public static Job toEntity(Long employerId, JobRequest req) {
        if (req == null) {
            return null;
        }

        JobLocation location = JobLocation.builder()
                .address(req.getAddress())
                .city(req.getCity())
                .state(req.getState())
                .country(req.getCountry())
                .zip(req.getZipCode())
                .build();

        SalaryRange salaryRange = SalaryRange.builder()
                .minSalary(req.getMinSalary())
                .maxSalary(req.getMaxSalary())
                .build();

        return Job.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .requirements(req.getRequirements())
                .responsibilities(req.getResponsibilities())
                .benefits(req.getBenefits())
                .companyId(null)
                .employerId(employerId)
                .categoryId(req.getCategoryId())
                .skillIds(req.getSkillIds())
                .tagIds(req.getTagIds())
                .location(location)
                .salaryRange(salaryRange)
                .currency(req.getCurrency())
                .salaryPeriod(req.getSalaryPeriod())
                .salaryNegotiable(req.getSalaryNegotiable())
                .salaryDisclosed(req.getSalaryDisclosed())
                .jobType(req.getJobType())
                .workMode(req.getWorkMode())
                .experienceLevel(req.getExperienceLevel())
                .status(com.noir.job.domain.JobStatus.DRAFT) // Newly created jobs start as DRAFT
                .openings(req.getOpenings())
                .applicationDeadline(req.getApplicationDeadline())
                .expiresAt(req.getExpiresAt())
                .active(true)
                .build();
    }

    public static void updateEntity(Job job, JobRequest req) {
        if (req == null || job == null) {
            return;
        }

        job.setTitle(req.getTitle());
        job.setDescription(req.getDescription());
        job.setRequirements(req.getRequirements());
        job.setResponsibilities(req.getResponsibilities());
        job.setBenefits(req.getBenefits());
        job.setCategoryId(req.getCategoryId());
        job.setSkillIds(req.getSkillIds());
        job.setTagIds(req.getTagIds());

        JobLocation location = job.getLocation();
        if (location == null) {
            location = new JobLocation();
        }
        location.setAddress(req.getAddress());
        location.setCity(req.getCity());
        location.setState(req.getState());
        location.setCountry(req.getCountry());
        location.setZip(req.getZipCode());
        job.setLocation(location);

        SalaryRange salaryRange = job.getSalaryRange();
        if (salaryRange == null) {
            salaryRange = new SalaryRange();
        }
        salaryRange.setMinSalary(req.getMinSalary());
        salaryRange.setMaxSalary(req.getMaxSalary());
        job.setSalaryRange(salaryRange);

        job.setCurrency(req.getCurrency());
        job.setSalaryPeriod(req.getSalaryPeriod());
        job.setSalaryNegotiable(req.getSalaryNegotiable());
        job.setSalaryDisclosed(req.getSalaryDisclosed());
        job.setJobType(req.getJobType());
        job.setWorkMode(req.getWorkMode());
        job.setExperienceLevel(req.getExperienceLevel());
        job.setOpenings(req.getOpenings());
        job.setApplicationDeadline(req.getApplicationDeadline());
        job.setExpiresAt(req.getExpiresAt());
    }


}
