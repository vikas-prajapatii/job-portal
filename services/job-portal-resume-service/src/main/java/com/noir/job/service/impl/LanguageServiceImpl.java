package com.noir.job.service.impl;

import com.noir.job.dto.LanguageResponse;
import com.noir.job.mapper.ResumeMapper;
import com.noir.job.model.Language;
import com.noir.job.model.Resume;
import com.noir.job.payload.AddLanguageRequest;
import com.noir.job.repository.LanguageRepository;
import com.noir.job.service.LanguageService;
import com.noir.job.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final ResumeService resumeService;
    @Override
    public LanguageResponse addLanguage(Long resumeId, Long candidateId, AddLanguageRequest req) throws Exception {
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
    public List<LanguageResponse> getLanguages(Long resumeId) throws Exception {
        resumeService.getResumeEntity(resumeId);
        return languageRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream().map(ResumeMapper::toLanguageResponse).toList();
    }

    @Override
    public LanguageResponse updateLanguage(Long languageId, Long resumeId, Long candidateId, AddLanguageRequest req) throws Exception {
        Language lang = getLanguageEntity(languageId, resumeId);
        assertOwner(lang.getResume(), candidateId, resumeId);

        lang.setLanguageName(req.getLanguageName());
        lang.setProficiency(req.getProficiency());
        if (req.getDisplayOrder() != null) lang.setDisplayOrder(req.getDisplayOrder());

        return ResumeMapper.toLanguageResponse(languageRepository.save(lang));
    }

    @Override
    public void deleteLanguage(Long languageId, Long resumeId, Long candidateId) throws Exception {
        Language lang = getLanguageEntity(languageId, resumeId);
        assertOwner(lang.getResume(), candidateId, resumeId);
        languageRepository.delete(lang);
    }
    private Language getLanguageEntity(Long languageId, Long resumeId) throws Exception {
        Language lang = languageRepository.findById(languageId)
                .orElseThrow(() -> new Exception(
                        "Language not found with id: " + languageId));
        if (!lang.getResume().getId().equals(resumeId)) {
            throw new Exception("Language not found with id: " + languageId);
        }
        return lang;
    }

    private void assertOwner(Resume resume, Long candidateId, Long resumeId)
            throws Exception {
        if (!resume.getCandidateId().equals(candidateId)) {
            throw new Exception("Resume not found with id: " + resumeId);
        }
    }

}
