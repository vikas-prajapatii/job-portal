package com.noir.job.service.impl;

import com.noir.job.client.CompanyClient;
import com.noir.job.domain.JobStatus;
import com.noir.job.dto.CompanyResponse;
import com.noir.job.dto.JobRequest;
import com.noir.job.dto.JobResponse;
import com.noir.job.mapper.JobMapper;
import com.noir.job.model.Job;
import com.noir.job.model.JobCategory;
import com.noir.job.model.JobSkill;
import com.noir.job.model.JobTags;
import com.noir.job.model.embeddable.JobLocation;
import com.noir.job.model.embeddable.SalaryRange;
import com.noir.job.payload.JobSearchRequest;
import com.noir.job.repository.JobRepository;
import com.noir.job.repository.JobSpecification;
import com.noir.job.service.JobCategoryService;
import com.noir.job.service.JobService;
import com.noir.job.service.JobSkillService;
import com.noir.job.service.JobTagService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
@Transactional
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobCategoryService jobCategoryService;
    private final JobSkillService jobSkillService;
    private final JobTagService jobTagService;
    private final CompanyClient companyClient;

    @Override
    @CacheEvict(value = "jobs", allEntries = true)
    public JobResponse createJob(Long employerId, JobRequest req) throws Exception {
        JobCategory category = jobCategoryService.getCategoryEntityById(req.getCategoryId());
        Set<JobSkill> skills = req.getSkillIds() != null?
                jobSkillService.getSkillsByIds(req.getSkillIds())
                : Collections.emptySet();

        Set<JobTags> tags = req.getTagIds() != null?
                jobTagService.getTagsByIds(req.getTagIds())
                : Collections.emptySet();
        CompanyResponse company =  companyClient.getMyCompany(employerId);
        Long companyId = company.getId();
        Job job = Job.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .requirements(req.getRequirements())
                .responsibilities(req.getResponsibilities())
                .benefits(req.getBenefits())
                .location(buildLocation(req))
                .salaryRange(buildSalaryRange(req))
                .jobType(req.getJobType())
                .category(category)
                .skills(skills)
                .tags(tags)
                .workMode(req.getWorkMode())
                .employerId(employerId)
                .experienceLevel(req.getExperienceLevel())
                .openings(req.getOpenings() != null? req.getOpenings() : 1)
                .applicationDeadline(req.getApplicationDeadline())
                .expiresAt(req.getExpiresAt())
                .active(true)
                .status(JobStatus.DRAFT)
                .build();
        Job savedJob = jobRepository.save(job);
        return convertToResponse(savedJob);
    }


    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "jobs", key = "#id")
    public JobResponse getJobById(Long id) throws Exception {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new Exception("Job not found with id: " + id));
        return convertToResponse(job);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobResponse> getJobs(JobSearchRequest request) {
        List<Job> jobs = jobRepository.findAll(JobSpecification.build(request));
        return jobs.stream().map(
                this::convertToResponse
        ).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobResponse> getJobByCompany(Long companyId) {
        return jobRepository.findByCompanyId(companyId).stream()
                .map(JobMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "jobs", allEntries = true)
    public JobResponse updateJob(Long jobId, Long employerId, JobRequest req) throws Exception {
       Job job = jobRepository.findById(jobId).orElseThrow(
               ()->new Exception("Job not found")
       );
       assertEmployer(job,employerId);


        JobCategory category = jobCategoryService.getCategoryEntityById(req.getCategoryId());
        Set<JobSkill> skills = req.getSkillIds() != null?
                jobSkillService.getSkillsByIds(req.getSkillIds())
                : Collections.emptySet();

        Set<JobTags> tags = req.getTagIds() != null?
                jobTagService.getTagsByIds(req.getTagIds())
                : Collections.emptySet();


       job.setTitle(req.getTitle());
       job.setDescription(req.getDescription());
       job.setRequirements(req.getRequirements());
       job.setResponsibilities(req.getResponsibilities());
       job.setBenefits(req.getBenefits());

       job.setCategory(category);
       job.setSkills(skills);
       job.setTags(tags);
       job.setLocation(buildLocation(req));
       job.setSalaryRange(buildSalaryRange(req));
       job.setJobType(req.getJobType());

       job.setWorkMode(req.getWorkMode());
       job.setExperienceLevel(req.getExperienceLevel());
       job.setOpenings(req.getOpenings());
       job.setApplicationDeadline(req.getApplicationDeadline());
       job.setExpiresAt(req.getExpiresAt());
       Job savedJob = jobRepository.save(job);
       return convertToResponse(savedJob);

    }

    @Override
    @CacheEvict(value = "jobs", allEntries = true)
    public JobResponse publishJob(Long jobId, Long employerId) throws Exception {
        Job job = jobRepository.findById(jobId).orElseThrow(
                () -> new Exception("Job not found with id: " + jobId));
                assertEmployer(job,employerId);
                if(job.getStatus() == JobStatus.CLOSED || job.getStatus() == JobStatus.EXPIRED) {
                    throw new Exception("Job is already closed");
                }
                job.setStatus(JobStatus.ACTIVE);
                job.setPublishedAt(LocalDate.now());
                job.setActive(true);
        return convertToResponse(jobRepository.save(job));

    }

    private void assertEmployer(Job job, Long employerId) throws Exception {
        if(!job.getEmployerId().equals(employerId)) {
            throw new Exception("you are not the employer who posted this job");
        }
    }

    @Override
    @CacheEvict(value = "jobs", allEntries = true)
    public void deleteJob(Long jobId, Long employerId) throws Exception {
        Job job = jobRepository.findById(jobId).orElseThrow(
                () -> new Exception("Job not found with id: " + jobId));
        assertEmployer(job,employerId);
        jobRepository.delete(job);
    }

    @Override
    @CacheEvict(value = "jobs", allEntries = true)
    public JobResponse closeJob(Long jobId, Long employerId) throws Exception {
        Job job = jobRepository.findById(jobId).orElseThrow(
                () -> new Exception("Job not found with id: " + jobId));
        assertEmployer(job,employerId);

        job.setStatus(JobStatus.CLOSED);
        job.setClosedAt(LocalDate.now());
        job.setActive(false);
        return convertToResponse(jobRepository.save(job));
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobResponse> getAllJobsAdmin() {
        return jobRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    private JobResponse convertToResponse(Job savedJob) {
        CompanyResponse response = companyClient.getCompanyById(savedJob.getCompanyId());
        return JobMapper.toResponse(savedJob, response);
    }

    private SalaryRange buildSalaryRange(JobRequest req) {
        return SalaryRange.builder()
                .minSalary(req.getMinSalary())
                .maxSalary(req.getMaxSalary())
                .build();
    }
    private JobLocation buildLocation(JobRequest req) {
        return JobLocation.builder()
                .address(req.getAddress())
                .city(req.getCity())
                .state(req.getState())
                .country(req.getCountry())
                .zip(req.getZipCode())
                .build();
    }
}
