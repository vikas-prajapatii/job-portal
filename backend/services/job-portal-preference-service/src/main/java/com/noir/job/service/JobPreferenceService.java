package com.noir.job.service;

import com.noir.job.common.dto.response.JobPreferenceResponse;
import com.noir.job.dto.request.UpdateJobPreferenceRequest;

public interface JobPreferenceService {

    JobPreferenceResponse getOrCreatePreference(Long candidateId);

    JobPreferenceResponse updatePreference(Long candidateId, UpdateJobPreferenceRequest req);
}
