package com.noir.job.service;

import com.noir.job.common.dto.response.JobAlertResponse;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.CreateJobAlertRequest;
import com.noir.job.dto.request.UpdateJobAlertRequest;

import java.util.List;

public interface JobAlertService {

    JobAlertResponse createAlert(Long candidateId, CreateJobAlertRequest req);

    JobAlertResponse getAlertById(Long alertId, Long candidateId) throws ResourceNotFoundException;

    List<JobAlertResponse> getMyAlerts(Long candidateId);

    JobAlertResponse updateAlert(Long alertId, Long candidateId, UpdateJobAlertRequest req)
            throws ResourceNotFoundException;

    void deleteAlert(Long alertId, Long candidateId) throws ResourceNotFoundException;

    JobAlertResponse toggleAlert(Long alertId, Long candidateId) throws ResourceNotFoundException;
}
