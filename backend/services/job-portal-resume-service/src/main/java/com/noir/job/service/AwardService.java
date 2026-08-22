package com.noir.job.service;

import com.noir.job.common.dto.response.AwardResponse;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.AddAwardRequest;

import java.util.List;

public interface AwardService {

    AwardResponse addAward(Long resumeId, Long candidateId, AddAwardRequest req)
            throws ResourceNotFoundException;

    List<AwardResponse> getAwards(Long resumeId) throws ResourceNotFoundException;

    AwardResponse updateAward(Long awardId, Long resumeId, Long candidateId, AddAwardRequest req)
            throws ResourceNotFoundException;

    void deleteAward(Long awardId, Long resumeId, Long candidateId) throws ResourceNotFoundException;
}
