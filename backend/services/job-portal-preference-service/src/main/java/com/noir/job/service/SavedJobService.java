package com.noir.job.service;

import com.noir.job.common.dto.response.SavedJobResponse;
import com.noir.job.common.exception.ApplicationException;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.SaveJobRequest;

import java.util.List;

public interface SavedJobService {

    SavedJobResponse saveJob(Long candidateId, SaveJobRequest req) throws ApplicationException;

    void unsaveJob(Long candidateId, Long savedJobId) throws ResourceNotFoundException;

    List<SavedJobResponse> getMySavedJobs(Long candidateId);

    boolean isSaved(Long candidateId, Long jobId);
}
