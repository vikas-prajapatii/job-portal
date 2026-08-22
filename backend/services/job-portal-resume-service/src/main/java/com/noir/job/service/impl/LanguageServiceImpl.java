package com.noir.job.service.impl;

import com.noir.job.common.dto.response.LanguageResponse;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.AddLanguageRequest;
import com.noir.job.entity.Language;
import com.noir.job.entity.Resume;
import com.noir.job.mapper.ResumeMapper;
import com.noir.job.repository.LanguageRepository;
import com.noir.job.service.LanguageService;
import com.noir.job.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final ResumeService resumeService;

    @Override
    @Transactional
    public LanguageResponse addLanguage(Long resumeId, Long candidateId, AddLanguageRequest req)
            throws ResourceNotFoundException {
        Resume resume = resumeService.getResumeEntity(resumeId);
        assertOwner(resume, candidateId, resumeId);

        Language lang = Language.builder()
                .resume(resume)
                .languageName(req.getLanguageName())
                .proficiency(req.getProficiency())
                .displayOrder(req.getDisplayOrder() != null ? req.getDisplayOrder() : 0)
                .build();

        return ResumeMapper.toLanguageResponse(languageRepository.save(lang));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LanguageResponse> getLanguages(Long resumeId) throws ResourceNotFoundException {
        resumeService.getResumeEntity(resumeId);
        return languageRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream().map(ResumeMapper::toLanguageResponse).toList();
    }

    @Override
    @Transactional
    public LanguageResponse updateLanguage(Long languageId, Long resumeId, Long candidateId,
            AddLanguageRequest req) throws ResourceNotFoundException {
        Language lang = getLanguageEntity(languageId, resumeId);
        assertOwner(lang.getResume(), candidateId, resumeId);

        lang.setLanguageName(req.getLanguageName());
        lang.setProficiency(req.getProficiency());
        if (req.getDisplayOrder() != null) lang.setDisplayOrder(req.getDisplayOrder());

        return ResumeMapper.toLanguageResponse(languageRepository.save(lang));
    }

    @Override
    @Transactional
    public void deleteLanguage(Long languageId, Long resumeId, Long candidateId)
            throws ResourceNotFoundException {
        Language lang = getLanguageEntity(languageId, resumeId);
        assertOwner(lang.getResume(), candidateId, resumeId);
        languageRepository.delete(lang);
    }

    private Language getLanguageEntity(Long languageId, Long resumeId) throws ResourceNotFoundException {
        Language lang = languageRepository.findById(languageId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Language not found with id: " + languageId));
        if (!lang.getResume().getId().equals(resumeId)) {
            throw new ResourceNotFoundException("Language not found with id: " + languageId);
        }
        return lang;
    }

    private void assertOwner(Resume resume, Long candidateId, Long resumeId)
            throws ResourceNotFoundException {
        if (!resume.getCandidateId().equals(candidateId)) {
            throw new ResourceNotFoundException("Resume not found with id: " + resumeId);
        }
    }
}
