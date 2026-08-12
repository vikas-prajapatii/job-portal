package com.noir.job.service;

import com.noir.job.dto.SavedJobResponse;
import com.noir.job.payload.SaveJobRequest;

import java.util.List;

public interface SavedJobService {
    SavedJobResponse saveJob(Long candidateId, SaveJobRequest req) ;
    void unsaveJob(Long candidateId, Long savedJobId) ;

    List<SavedJobResponse> getMySavedJobs(Long candidateId);

    boolean isSaved(Long candidateId, Long jobId);
}
