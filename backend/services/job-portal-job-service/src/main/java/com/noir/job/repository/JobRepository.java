package com.noir.job.repository;

import com.noir.job.common.domain.ExperienceLevel;
import com.noir.job.common.domain.JobStatus;
import com.noir.job.common.domain.JobType;
import com.noir.job.common.domain.WorkMode;
import com.noir.job.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long>,
        JpaSpecificationExecutor<Job> {

    List<Job> findByEmployerId(Long employerId);

    List<Job> findByCompanyId(Long companyId);

    List<Job> findByActiveTrue();

    List<Job> findByStatusAndActiveTrue(JobStatus status);

    List<Job> findByCategory_Id(Long categoryId);

    List<Job> findByJobTypeAndActiveTrue(JobType jobType);

    List<Job> findByWorkModeAndActiveTrue(WorkMode workMode);

    List<Job> findByExperienceLevelAndActiveTrue(ExperienceLevel experienceLevel);

    List<Job> findByLocation_CityIgnoreCaseAndActiveTrue(String city);

    List<Job> findByLocation_CountryIgnoreCaseAndActiveTrue(String country);

    List<Job> findByCompanyIdAndActiveTrue(Long companyId);

    List<Job> findByEmployerIdAndStatus(Long employerId, JobStatus status);
}
