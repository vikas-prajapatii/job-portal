package com.noir.job.service;

import com.noir.job.common.dto.response.ApplicationResponse;
import com.noir.job.common.exception.ApplicationException;
import com.noir.job.common.exception.ResourceNotFoundException;
import com.noir.job.dto.request.CompanyApplicationFilterRequest;
import com.noir.job.dto.request.CreateApplicationRequest;
import com.noir.job.dto.request.UpdateApplicationStatusRequest;
import com.noir.job.dto.request.WithdrawApplicationRequest;
import com.noir.job.entity.Application;

import java.util.List;

public interface ApplicationService {

    ApplicationResponse createApplication(Long candidateId,
                                          CreateApplicationRequest req)
            throws ApplicationException;

    ApplicationResponse getApplicationById(Long id) throws ResourceNotFoundException;

    List<ApplicationResponse> getMyApplications(Long candidateId);

    List<ApplicationResponse> getApplicationsForJob(Long jobId);

    List<ApplicationResponse> getApplicationsForCompany(Long companyId,
                                                         CompanyApplicationFilterRequest filter) throws ResourceNotFoundException;

    ApplicationResponse updateStatus(Long applicationId,
                                     Long employerId,
                                      UpdateApplicationStatusRequest req)
            throws ResourceNotFoundException, ApplicationException;

    ApplicationResponse withdraw(Long applicationId, Long candidateId,
                                  WithdrawApplicationRequest req)
            throws ResourceNotFoundException, ApplicationException;

    ApplicationResponse markAsRead(Long applicationId, Long employerId)
            throws ResourceNotFoundException, ApplicationException;

    ApplicationResponse toggleStar(Long applicationId, Long employerId)
            throws ResourceNotFoundException, ApplicationException;

    void deleteApplication(Long applicationId, Long candidateId)
            throws ResourceNotFoundException, ApplicationException;

    /** Used internally by interview/note services. */
    Application getApplicationEntity(Long id) throws ResourceNotFoundException;

    /** Called by job-service after a job's requirements are edited — marks all existing scores as stale. */
    void markScreeningsStaleForJob(Long jobId);
}
