package com.noir.job.service.impl;

import com.noir.job.dto.PersonalInfoResponse;
import com.noir.job.dto.ResumeResponse;
import com.noir.job.model.PersonalInfo;
import com.noir.job.model.Resume;
import com.noir.job.mapper.ResumeMapper;
import com.noir.job.payload.CreateResumeRequest;
import com.noir.job.repository.ResumeRepository;
import com.noir.job.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.hibernate.boot.model.internal.BinderHelper.isDefault;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;

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
                .template(req.getTemplate())
                .visibility(req.getVisibility())
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
//        List<WorkExperience> workExperiences = workExperienceRepository
//                .findByResume_IdOrderByDisplayOrderAsc(resume.getId());
//        List<Education> educations = educationRepository
//                .findByResume_IdOrderByDisplayOrderAsc(resume.getId());
//        List<ResumeSkill> skills = skillRepository
//                .findByResume_IdOrderByDisplayOrderAsc(resume.getId());
//        List<Project> projects = projectRepository
//                .findByResume_IdOrderByDisplayOrderAsc(resume.getId());
//        List<Certification> certifications = certificationRepository
//                .findByResume_IdOrderByDisplayOrderAsc(resume.getId());
//        List<Award> awards = awardRepository
//                .findByResume_IdOrderByDisplayOrderAsc(resume.getId());
//        List<Language> languages = languageRepository
//                .findByResume_IdOrderByDisplayOrderAsc(resume.getId());
//
//        return ResumeMapper.toResponse(resume, workExperiences, educations, skills,
//                projects, certifications, awards, languages);
        return ResumeMapper.toResponse(resume);
    }
    private void assertOwner(Resume resume, Long candidateId) throws Exception {
        if (!resume.getCandidateId().equals(candidateId)) {
            throw new Exception("Resume not found with id: " + resume.getId());
        }
    }
}
