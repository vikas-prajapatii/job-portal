package com.noir.job.service;

import com.noir.job.domain.ApplicationStatus;
import com.noir.job.dto.ApplicationResponse;
import com.noir.job.model.Application;
import com.noir.job.payload.CompanyApplicationFilterRequest;
import com.noir.job.payload.CreateApplicationRequest;
import com.noir.job.payload.UpdateApplicationStatusRequest;
import com.noir.job.payload.WithdrawApplicationRequest;

import java.util.List;

public interface ApplicationService {
    ApplicationResponse createApplication(Long candidateId,
                                          CreateApplicationRequest req) throws Exception;
    ApplicationResponse getApplicationById(Long id) throws Exception;

    List<ApplicationResponse> getMyApplications(Long candidateId);
    List<ApplicationResponse> getApplicationsForJob(Long jobId);
    List<ApplicationResponse> getApplicationsForCompany(Long userId, CompanyApplicationFilterRequest filterRequest);
    ApplicationResponse updateStatus(Long applicationId,
                                     Long employerId,
                                     ApplicationStatus status) throws Exception;
    ApplicationResponse withdraw(Long applicationId, Long candidateId,
                                 WithdrawApplicationRequest req) throws Exception;

    ApplicationResponse markAsRead(Long applicationId, Long employerId) throws Exception;
    ApplicationResponse toggleStar(Long applicationId, Long employerId) throws Exception;
    void deleteApplication(Long applicationId, Long candidateId) throws Exception;
    Application getApplicationEntity(Long id) throws Exception;
    void markScreeningsStaleForJob(Long jobId);

}
