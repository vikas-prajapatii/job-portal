package com.noir.job.service.impl;

import com.noir.job.common.dto.response.CertificationResponse;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.AddCertificationRequest;
import com.noir.job.entity.Certification;
import com.noir.job.entity.Resume;
import com.noir.job.mapper.ResumeMapper;
import com.noir.job.repository.CertificationRepository;
import com.noir.job.service.CertificationService;
import com.noir.job.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {

    private final CertificationRepository certificationRepository;
    private final ResumeService resumeService;

    @Override
    @Transactional
    public CertificationResponse addCertification(Long resumeId, Long candidateId,
            AddCertificationRequest req) throws ResourceNotFoundException {
        Resume resume = resumeService.getResumeEntity(resumeId);
        assertOwner(resume, candidateId, resumeId);

        Certification cert = Certification.builder()
                .resume(resume)
                .name(req.getName())
                .issuingOrganization(req.getIssuingOrganization())
                .issueDate(req.getIssueDate())
                .expiryDate(req.getExpiryDate())
                .credentialId(req.getCredentialId())
                .credentialUrl(req.getCredentialUrl())
                .displayOrder(req.getDisplayOrder() != null ? req.getDisplayOrder() : 0)
                .build();

        return ResumeMapper.toCertificationResponse(certificationRepository.save(cert));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificationResponse> getCertifications(Long resumeId) throws ResourceNotFoundException {
        resumeService.getResumeEntity(resumeId);
        return certificationRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream().map(ResumeMapper::toCertificationResponse).toList();
    }

    @Override
    @Transactional
    public CertificationResponse updateCertification(Long certificationId, Long resumeId,
            Long candidateId, AddCertificationRequest req) throws ResourceNotFoundException {
        Certification cert = getCertificationEntity(certificationId, resumeId);
        assertOwner(cert.getResume(), candidateId, resumeId);

        cert.setName(req.getName());
        cert.setIssuingOrganization(req.getIssuingOrganization());
        cert.setIssueDate(req.getIssueDate());
        cert.setExpiryDate(req.getExpiryDate());
        cert.setCredentialId(req.getCredentialId());
        cert.setCredentialUrl(req.getCredentialUrl());
        if (req.getDisplayOrder() != null) cert.setDisplayOrder(req.getDisplayOrder());

        return ResumeMapper.toCertificationResponse(certificationRepository.save(cert));
    }

    @Override
    @Transactional
    public void deleteCertification(Long certificationId, Long resumeId, Long candidateId)
            throws ResourceNotFoundException {
        Certification cert = getCertificationEntity(certificationId, resumeId);
        assertOwner(cert.getResume(), candidateId, resumeId);
        certificationRepository.delete(cert);
    }

    private Certification getCertificationEntity(Long certificationId, Long resumeId)
            throws ResourceNotFoundException {
        Certification cert = certificationRepository.findById(certificationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Certification not found with id: " + certificationId));
        if (!cert.getResume().getId().equals(resumeId)) {
            throw new ResourceNotFoundException("Certification not found with id: " + certificationId);
        }
        return cert;
    }

    private void assertOwner(Resume resume, Long candidateId, Long resumeId)
            throws ResourceNotFoundException {
        if (!resume.getCandidateId().equals(candidateId)) {
            throw new ResourceNotFoundException("Resume not found with id: " + resumeId);
        }
    }
}
