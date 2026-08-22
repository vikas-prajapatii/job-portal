package com.noir.job.service.impl;

import com.noir.job.common.dto.response.AwardResponse;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.AddAwardRequest;
import com.noir.job.entity.Award;
import com.noir.job.entity.Resume;
import com.noir.job.mapper.ResumeMapper;
import com.noir.job.repository.AwardRepository;
import com.noir.job.service.AwardService;
import com.noir.job.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AwardServiceImpl implements AwardService {

    private final AwardRepository awardRepository;
    private final ResumeService resumeService;

    @Override
    @Transactional
    public AwardResponse addAward(Long resumeId, Long candidateId, AddAwardRequest req)
            throws ResourceNotFoundException {
        Resume resume = resumeService.getResumeEntity(resumeId);
        assertOwner(resume, candidateId, resumeId);

        Award award = Award.builder()
                .resume(resume)
                .title(req.getTitle())
                .issuedBy(req.getIssuedBy())
                .awardDate(req.getAwardDate())
                .description(req.getDescription())
                .displayOrder(req.getDisplayOrder() != null ? req.getDisplayOrder() : 0)
                .build();

        return ResumeMapper.toAwardResponse(awardRepository.save(award));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AwardResponse> getAwards(Long resumeId) throws ResourceNotFoundException {
        resumeService.getResumeEntity(resumeId);
        return awardRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream().map(ResumeMapper::toAwardResponse).toList();
    }

    @Override
    @Transactional
    public AwardResponse updateAward(Long awardId, Long resumeId, Long candidateId,
            AddAwardRequest req) throws ResourceNotFoundException {
        Award award = getAwardEntity(awardId, resumeId);
        assertOwner(award.getResume(), candidateId, resumeId);

        award.setTitle(req.getTitle());
        award.setIssuedBy(req.getIssuedBy());
        award.setAwardDate(req.getAwardDate());
        award.setDescription(req.getDescription());
        if (req.getDisplayOrder() != null) award.setDisplayOrder(req.getDisplayOrder());

        return ResumeMapper.toAwardResponse(awardRepository.save(award));
    }

    @Override
    @Transactional
    public void deleteAward(Long awardId, Long resumeId, Long candidateId)
            throws ResourceNotFoundException {
        Award award = getAwardEntity(awardId, resumeId);
        assertOwner(award.getResume(), candidateId, resumeId);
        awardRepository.delete(award);
    }

    private Award getAwardEntity(Long awardId, Long resumeId) throws ResourceNotFoundException {
        Award award = awardRepository.findById(awardId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Award not found with id: " + awardId));
        if (!award.getResume().getId().equals(resumeId)) {
            throw new ResourceNotFoundException("Award not found with id: " + awardId);
        }
        return award;
    }

    private void assertOwner(Resume resume, Long candidateId, Long resumeId)
            throws ResourceNotFoundException {
        if (!resume.getCandidateId().equals(candidateId)) {
            throw new ResourceNotFoundException("Resume not found with id: " + resumeId);
        }
    }
}
