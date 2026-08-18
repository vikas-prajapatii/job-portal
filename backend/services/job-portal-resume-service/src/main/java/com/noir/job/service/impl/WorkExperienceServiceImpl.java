package com.noir.job.service.impl;

import com.noir.job.dto.WorkExperienceResponse;
import com.noir.job.model.Resume;
import com.noir.job.model.WorkExperience;
import com.noir.job.mapper.WorkExperienceMapper;
import org.springframework.transaction.annotation.Transactional;
import com.noir.job.payload.AddWorkExperienceRequest;
import com.noir.job.repository.WorkExperienceRepository;
import com.noir.job.service.ResumeService;
import com.noir.job.service.WorkExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class WorkExperienceServiceImpl implements WorkExperienceService {
    private final WorkExperienceRepository workExperienceRepository;
    private final ResumeService resumeService;
    @Override
    public WorkExperienceResponse addWorkExperience(Long resumeId, Long candidateId, AddWorkExperienceRequest req) throws Exception {
        Resume resume = resumeService.getResumeEntity(resumeId);
        assertOwner(resume, candidateId, resumeId);

        WorkExperience exp = WorkExperience.builder()
                .resume(resume)
                .companyName(req.getCompanyName())
                .companyLogoUrl(req.getCompanyLogoUrl())
                .jobTitle(req.getJobTitle())
                .employmentType(req.getEmploymentType())
                .location(req.getLocation())
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .isCurrentJob(Boolean.TRUE.equals(req.getIsCurrentJob()))
                .description(req.getDescription())
                .technologies(req.getTechnologies() != null ? req.getTechnologies() : List.of())
                .displayOrder(req.getDisplayOrder() != null ? req.getDisplayOrder() : 0)
                .build();
        WorkExperience saved = workExperienceRepository.save(exp);
        return WorkExperienceMapper.toWorkExperienceResponse(workExperienceRepository.save(exp));

    }


    @Override
    @Transactional(readOnly = true)
    public List<WorkExperienceResponse> getWorkExperiences(Long resumeId) throws Exception {
        resumeService.getResumeEntity(resumeId);
        return workExperienceRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream().map(WorkExperienceMapper::toWorkExperienceResponse).toList();

    }

    @Override
    public WorkExperienceResponse updateWorkExperience(Long experienceId, Long resumeId, Long candidateId, AddWorkExperienceRequest req) throws Exception {
        WorkExperience exp = getExperienceEntity(experienceId, resumeId);
        assertOwner(exp.getResume(), candidateId, resumeId);

        exp.setCompanyName(req.getCompanyName());
        exp.setCompanyLogoUrl(req.getCompanyLogoUrl());
        exp.setJobTitle(req.getJobTitle());
        exp.setEmploymentType(req.getEmploymentType());
        exp.setLocation(req.getLocation());
        exp.setStartDate(req.getStartDate());
        exp.setEndDate(req.getEndDate());
        exp.setIsCurrentJob(Boolean.TRUE.equals(req.getIsCurrentJob()));
        exp.setDescription(req.getDescription());
        if (req.getTechnologies() != null) exp.setTechnologies(req.getTechnologies());
        if (req.getDisplayOrder() != null) exp.setDisplayOrder(req.getDisplayOrder());

        return WorkExperienceMapper.toWorkExperienceResponse(workExperienceRepository.save(exp));
    }

    @Override
    @Transactional
    public void deleteWorkExperience(Long experienceId, Long resumeId, Long candidateId) throws Exception {
        WorkExperience exp = getExperienceEntity(experienceId, resumeId);
        assertOwner(exp.getResume(), candidateId, resumeId);
        workExperienceRepository.delete(exp);
    }

    private void assertOwner(Resume resume, Long candidateId, Long resumeId)
            throws Exception {
        if (!resume.getCandidateId().equals(candidateId)) {
            throw new Exception("Resume not found with id: " + resumeId);
        }
    }
    private WorkExperience getExperienceEntity(Long experienceId, Long resumeId)
            throws Exception {
        WorkExperience exp = workExperienceRepository.findById(experienceId)
                .orElseThrow(() -> new Exception(
                        "Work experience not found with id: " + experienceId));
        if (!exp.getResume().getId().equals(resumeId)) {
            throw new Exception("Work experience not found with id: " + experienceId);
        }
        return exp;
    }
}
