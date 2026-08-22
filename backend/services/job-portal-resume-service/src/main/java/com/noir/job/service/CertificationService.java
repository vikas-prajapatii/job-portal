package com.noir.job.service;

import com.noir.job.common.dto.response.CertificationResponse;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.AddCertificationRequest;

import java.util.List;

public interface CertificationService {

    CertificationResponse addCertification(Long resumeId, Long candidateId, AddCertificationRequest req)
            throws ResourceNotFoundException;

    List<CertificationResponse> getCertifications(Long resumeId) throws ResourceNotFoundException;

    CertificationResponse updateCertification(Long certificationId, Long resumeId, Long candidateId,
            AddCertificationRequest req) throws ResourceNotFoundException;

    void deleteCertification(Long certificationId, Long resumeId, Long candidateId)
            throws ResourceNotFoundException;
}
