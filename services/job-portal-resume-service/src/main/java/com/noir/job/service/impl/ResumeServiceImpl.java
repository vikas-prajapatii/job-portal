package com.noir.job.service.impl;

import com.noir.job.dto.*;
import com.noir.job.mapper.WorkExperienceMapper;
import com.noir.job.model.PersonalInfo;
import com.noir.job.model.Resume;
import com.noir.job.mapper.ResumeMapper;
import com.noir.job.model.WorkExperience;
import com.noir.job.payload.CreateResumeRequest;
import com.noir.job.repository.*;
import com.noir.job.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.hibernate.boot.model.internal.BinderHelper.isDefault;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final EducationRepository educationRepository;
    private final ResumeSkillRepository resumeSkillRepository;
    private final ProjectRepository projectRepository;
    private final LanguageRepository languageRepository;

    @Override
    public ResumeResponse createResume(Long candidateId, CreateResumeRequest req) {
        if (Boolean.TRUE.equals(req.getIsDefault())) {
            resumeRepository.findByCandidateIdAndIsDefaultTrue(candidateId)
                    .ifPresent(existing -> {
                        existing.setIsDefault(false);
                        resumeRepository.save(existing);
                    });
        }
        Resume resume = Resume.builder()
                .candidateId(candidateId)
                .title(req.getTitle())
                .template(req.getTemplate() != null ? req.getTemplate() : com.noir.job.domain.ResumeTemplate.PROFESSIONAL)
                .visibility(req.getVisibility() != null ? req.getVisibility() : com.noir.job.domain.ResumeVisibility.PRIVATE)
                .isDefault(Boolean.TRUE.equals(req.getIsDefault()))
                .isActive(true)
                .build();
        Resume savedResume = resumeRepository.save(resume);
        return buildFullResponse(savedResume);
    }

    @Override
    public ResumeResponse getResumeById(Long resumeId, Long candidateId) throws Exception {
        Resume resume = getResumeEntity(resumeId);
        assertOwner(resume, candidateId);
        return buildFullResponse(resume);
    }

    @Override
    public List<ResumeResponse> getMyResumes(Long candidateId) {
        return resumeRepository.findByCandidateIdAndIsActiveTrue(candidateId)
                .stream()
                .map(this::buildFullResponse)
                .toList();
    }

    @Override
    public ResumeResponse updatePersonalInfo(Long resumeId, Long candidateId, PersonalInfoResponse req) throws Exception {
        Resume resume = getResumeEntity(resumeId);
        assertOwner(resume, candidateId);

        PersonalInfo info = resume.getPersonalInfo();
        if (info == null) info = new PersonalInfo();

        if (req.getFirstName() != null)
            info.setFirstName(req.getFirstName());
        if (req.getLastName() != null) info.setLastName(req.getLastName());
        if (req.getHeadLine() != null) info.setHeadLine(req.getHeadLine());
        if (req.getEmail() != null) info.setEmail(req.getEmail());
        if (req.getPhone() != null) info.setPhone(req.getPhone());
        if (req.getCity() != null) info.setCity(req.getCity());
        if (req.getCountry() != null) info.setCountry(req.getCountry());
        if (req.getLinkedinUrl() != null) info.setLinkedinUrl(req.getLinkedinUrl());
        if (req.getGithubUrl() != null) info.setGithubUrl(req.getGithubUrl());
        if (req.getPortfolioUrl() != null) info.setPortfolioUrl(req.getPortfolioUrl());
        if (req.getWebsiteUrl() != null) info.setWebsiteUrl(req.getWebsiteUrl());
        resume.setPersonalInfo(info);
        resume = resumeRepository.save(resume);
        return buildFullResponse(resume);
    }

    @Override
    public ResumeResponse updateSummary(Long resumeId, Long candidateId, String summary) throws Exception {
        Resume resume = getResumeEntity(resumeId);
        assertOwner(resume, candidateId);
        resume.setSummary(summary);
        resume = resumeRepository.save(resume);
        return buildFullResponse(resume);
    }

    @Override
    public ResumeResponse setDefaultResume(Long resumeId, Long candidateId) throws Exception {
        Resume resume = getResumeEntity(resumeId);
        assertOwner(resume, candidateId);
        resumeRepository.findByCandidateIdAndIsDefaultTrue(candidateId)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(resumeId)) {
                        existing.setIsDefault(false);
                        resumeRepository.save(existing);
                    }
                });
        resume.setIsDefault(true);
        resume = resumeRepository.save(resume);
        return buildFullResponse(resume);
    }

    @Override
    public void deleteResume(Long resumeId, Long candidateId) throws Exception {
        Resume resume = getResumeEntity(resumeId);
        assertOwner(resume, candidateId);
        resume.setIsActive(false);
        resume.setIsDefault(false);
        resumeRepository.save(resume);
    }

    @Override
    public Resume getResumeEntity(Long resumeId) throws Exception {
        return resumeRepository.findById(resumeId)
                .orElseThrow(() -> new Exception("Resume not found with id: " + resumeId));
    }
    private ResumeResponse buildFullResponse(Resume resume) {
        Long resumeId = resume.getId();

        List<WorkExperienceResponse> workExperiences = workExperienceRepository
                .findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream().map(WorkExperienceMapper::toWorkExperienceResponse).toList();
        List<EducationResponse> educationResponses = educationRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream()
                .map(ResumeMapper::toEducationResponse)
                .toList();
        List<ResumeSkillResponse> resumeSkillResponses = resumeSkillRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream().map(ResumeMapper::toSkillResponse).toList();
        List<ProjectResponse> projectResponses = projectRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream().map(ResumeMapper::toProjectResponse).toList();
        List<LanguageResponse> languageResponses = languageRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream().map(ResumeMapper::toLanguageResponse).toList();
        return ResumeMapper.toResponse(resume,workExperiences, educationResponses, resumeSkillResponses, projectResponses, languageResponses);
    }
    private void assertOwner(Resume resume, Long candidateId) throws Exception {
        if (!resume.getCandidateId().equals(candidateId)) {
            throw new Exception("Resume not found with id: " + resume.getId());
        }
    }
}
