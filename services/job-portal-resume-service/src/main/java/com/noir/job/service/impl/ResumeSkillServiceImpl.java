package com.noir.job.service.impl;

import com.noir.job.dto.ResumeSkillResponse;
import com.noir.job.model.Resume;
import com.noir.job.model.ResumeSkill;
import com.noir.job.mapper.ResumeMapper;
import com.noir.job.payload.AddResumeSkillRequest;
import com.noir.job.repository.ResumeSkillRepository;
import com.noir.job.service.ResumeService;
import com.noir.job.service.ResumeSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ResumeSkillServiceImpl implements ResumeSkillService {

    private final ResumeSkillRepository skillRepository;
    private final ResumeService resumeService;
    @Override
    public ResumeSkillResponse addSkill(Long resumeId, Long candidateId, AddResumeSkillRequest req) throws Exception {
        Resume resume = resumeService.getResumeEntity(resumeId);
        assertOwner(resume, candidateId, resumeId);

        ResumeSkill skill = ResumeSkill.builder()
                .resume(resume)
                .skillName(req.getSkillName())
                .proficiencyLevel(req.getProficiencyLevel())
                .yearsOfExperience(req.getYearsOfExperience())
                .displayOrder(req.getDisplayOrder() != null ? req.getDisplayOrder() : 0)
                .build();
        ResumeSkill saveSkill = skillRepository.save(skill);

        return ResumeMapper.toSkillResponse(saveSkill);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumeSkillResponse> getSkills(Long resumeId) throws Exception {
        resumeService.getResumeEntity(resumeId);
        return skillRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream().map(ResumeMapper::toSkillResponse).toList();
    }

    @Override
    public ResumeSkillResponse updateSkill(Long skillId, Long resumeId, Long candidateId, AddResumeSkillRequest req) throws Exception {
            ResumeSkill skill = getSkillEntity(skillId, resumeId);
            assertOwner(skill.getResume(), candidateId, resumeId);

            skill.setSkillName(req.getSkillName());
            skill.setProficiencyLevel(req.getProficiencyLevel());
            skill.setYearsOfExperience(req.getYearsOfExperience());
            if (req.getDisplayOrder() != null) skill.setDisplayOrder(req.getDisplayOrder());

            return ResumeMapper.toSkillResponse(skillRepository.save(skill));
    }

    @Override
    public void deleteSkill(Long skillId, Long resumeId, Long candidateId) throws Exception {
        ResumeSkill skill = getSkillEntity(skillId, resumeId);
        assertOwner(skill.getResume(), candidateId, resumeId);
        skillRepository.delete(skill);

    }
    private void assertOwner(Resume resume, Long candidateId, Long resumeId)
            throws Exception {
        if (!resume.getCandidateId().equals(candidateId)) {
            throw new Exception("Resume not found with id: " + resumeId);
        }
    }
    private ResumeSkill getSkillEntity(Long skillId, Long resumeId) throws Exception {
        ResumeSkill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new Exception("Skill not found with id: " + skillId));
        if (!skill.getResume().getId().equals(resumeId)) {
            throw new Exception("Skill not found with id: " + skillId);
        }
        return skill;
    }
}
