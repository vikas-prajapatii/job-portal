package com.noir.job.service;

import com.noir.job.common.dto.response.ResumeParseJobResponse;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.ParseResumeRequest;

import java.util.List;

public interface ResumeParseJobService {

    ResumeParseJobResponse submitParseJob(Long candidateId, ParseResumeRequest req);

    ResumeParseJobResponse getParseJob(Long jobId, Long candidateId) throws ResourceNotFoundException;

    List<ResumeParseJobResponse> getMyParseJobs(Long candidateId);
}
