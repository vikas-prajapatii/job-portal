package com.noir.job.service.impl;

import com.noir.job.common.dto.response.ResumeSkillResponse;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.AddResumeSkillRequest;
import com.noir.job.entity.Resume;
import com.noir.job.entity.ResumeSkill;
import com.noir.job.mapper.ResumeMapper;
import com.noir.job.repository.ResumeSkillRepository;
import com.noir.job.service.ResumeService;
import com.noir.job.service.ResumeSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeSkillServiceImpl implements ResumeSkillService {

    private final ResumeSkillRepository skillRepository;
    private final ResumeService resumeService;

    @Override
    @Transactional
    public ResumeSkillResponse addSkill(Long resumeId, Long candidateId, AddResumeSkillRequest req)
            throws ResourceNotFoundException {
        Resume resume = resumeService.getResumeEntity(resumeId);
        assertOwner(resume, candidateId, resumeId);

        ResumeSkill skill = ResumeSkill.builder()
                .resume(resume)
                .skillName(req.getSkillName())
                .proficiencyLevel(req.getProficiencyLevel())
                .yearsOfExperience(req.getYearsOfExperience())
                .displayOrder(req.getDisplayOrder() != null ? req.getDisplayOrder() : 0)
                .build();

        return ResumeMapper.toSkillResponse(skillRepository.save(skill));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumeSkillResponse> getSkills(Long resumeId) throws ResourceNotFoundException {
        resumeService.getResumeEntity(resumeId);
        return skillRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream().map(ResumeMapper::toSkillResponse).toList();
    }

    @Override
    @Transactional
    public ResumeSkillResponse updateSkill(Long skillId, Long resumeId, Long candidateId,
            AddResumeSkillRequest req) throws ResourceNotFoundException {
        ResumeSkill skill = getSkillEntity(skillId, resumeId);
        assertOwner(skill.getResume(), candidateId, resumeId);

        skill.setSkillName(req.getSkillName());
        skill.setProficiencyLevel(req.getProficiencyLevel());
        skill.setYearsOfExperience(req.getYearsOfExperience());
        if (req.getDisplayOrder() != null) skill.setDisplayOrder(req.getDisplayOrder());

        return ResumeMapper.toSkillResponse(skillRepository.save(skill));
    }

    @Override
    @Transactional
    public void deleteSkill(Long skillId, Long resumeId, Long candidateId)
            throws ResourceNotFoundException {
        ResumeSkill skill = getSkillEntity(skillId, resumeId);
        assertOwner(skill.getResume(), candidateId, resumeId);
        skillRepository.delete(skill);
    }

    private ResumeSkill getSkillEntity(Long skillId, Long resumeId) throws ResourceNotFoundException {
        ResumeSkill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + skillId));
        if (!skill.getResume().getId().equals(resumeId)) {
            throw new ResourceNotFoundException("Skill not found with id: " + skillId);
        }
        return skill;
    }

    private void assertOwner(Resume resume, Long candidateId, Long resumeId)
            throws ResourceNotFoundException {
        if (!resume.getCandidateId().equals(candidateId)) {
            throw new ResourceNotFoundException("Resume not found with id: " + resumeId);
        }
    }
}
