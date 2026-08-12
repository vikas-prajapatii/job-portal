package com.noir.job.service.impl;

import com.noir.job.domain.ApplicationStatus;
import com.noir.job.dto.ApplicationResponse;
import com.noir.job.dto.CompanyResponse;
import com.noir.job.dto.CompanySummaryResponse;
import com.noir.job.dto.JobResponse;
import com.noir.job.dto.response.UserResponse;
import com.noir.job.mapper.ApplicationMapper;
import com.noir.job.model.Application;
import com.noir.job.model.ApplicationNote;
import com.noir.job.payload.CompanyApplicationFilterRequest;
import com.noir.job.payload.CreateApplicationRequest;
import com.noir.job.payload.UpdateApplicationStatusRequest;
import com.noir.job.payload.WithdrawApplicationRequest;
import com.noir.job.repository.ApplicationNoteRepository;
import com.noir.job.repository.ApplicationRepository;
import com.noir.job.repository.ApplicationSpecification;
import com.noir.job.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationNoteRepository applicationNoteRepository;

    @Override
    public ApplicationResponse createApplication(Long candidateId, CreateApplicationRequest req) throws Exception {
        if (applicationRepository.existsByCandidateIdAndJobId(candidateId, req.getJobId())) {
            throw new Exception("You have already applied for this job");
        }
         Long companyId = 1L;
        long employeeId = 1L;
        Application application = ApplicationMapper.toEntity(req, candidateId,
               companyId, employeeId );
        Application savedApplication = applicationRepository.save(application);

        return buildFullResponse(savedApplication);
    }

    @Override
    public ApplicationResponse getApplicationById(Long id) throws Exception {
        Application application = getApplicationEntity(id);
        return buildFullResponse(application);
    }

    @Override
    public List<ApplicationResponse> getMyApplications(Long candidateId) {
        return applicationRepository.findByCandidateId(candidateId).stream()
                .map(this::buildFullResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationResponse> getApplicationsForJob(Long jobId) {
        return applicationRepository.findByJobId(jobId).stream()
                .map(this::buildFullResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationResponse> getApplicationsForCompany(Long userId,
                                                               CompanyApplicationFilterRequest filter) {
        Long companyId = 1L;
        Sort sort = buildSort(filter.getSortBy());
        return applicationRepository.findAll(
                ApplicationSpecification.forCompanyFilters(
                        companyId,
                        filter.getJobId(),
                        filter.getStatus(),
                        filter.isStarred(),
                        filter.getAiShortlistStatus(),
                        filter.getMinAiScore()
                ),sort)
                .stream().map(
                        this::buildFullResponse
                ).toList();
    }


    @Override
    public ApplicationResponse updateStatus(Long applicationId, Long employerId, ApplicationStatus status) throws Exception {
        Application application = getApplicationEntity(applicationId);
        assertEmployer(application, employerId);

        if (application.getStatus() == ApplicationStatus.WITHDRAWN) {
            throw new Exception("Cannot update status of a withdrawn application");
        }

        application.setStatus(status);
        Application updatedApplication = applicationRepository.save(application);
        return buildFullResponse(updatedApplication);
    }

    @Override
    public ApplicationResponse withdraw(Long applicationId, Long candidateId, WithdrawApplicationRequest req) throws Exception {
        Application application = getApplicationEntity(applicationId);
        assertCandidate(application, candidateId);
        application.setStatus(ApplicationStatus.WITHDRAWN);
        application.setWithdrawnReason(req.getReason());
        Application savedApplication = applicationRepository.save(application);
        return buildFullResponse(savedApplication);
    }

    @Override
    public ApplicationResponse markAsRead(Long applicationId, Long employerId) throws Exception {
        Application application = getApplicationEntity(applicationId);
        assertEmployer(application, employerId);
        application.setIsRead(true);
        return buildFullResponse(applicationRepository.save(application));
    }

    @Override
    public ApplicationResponse toggleStar(Long applicationId, Long employerId) throws Exception {
        Application application = getApplicationEntity(applicationId);
        assertEmployer(application, employerId);
        application.setIsStarred(!application.getIsStarred());
        return buildFullResponse(applicationRepository.save(application));
    }

    @Override
    public void deleteApplication(Long applicationId, Long candidateId) throws Exception {
        Application application = getApplicationEntity(applicationId);
        assertCandidate(application, candidateId);
        applicationRepository.delete(application);
    }

    @Override
    public Application getApplicationEntity(Long id) throws Exception {
        return applicationRepository.findById(id).orElseThrow(
                ()-> new Exception("application not found")
        );
    }

    @Override
    public void markScreeningsStaleForJob(Long jobId) {

    }


    public ApplicationResponse buildFullResponse(Application application) {
        List<ApplicationNote> notes = applicationNoteRepository.findByApplicationIdOrderByCreatedAtDesc(application.getId());
        JobResponse job = JobResponse.builder().id(application.getId()).build();
        CompanyResponse company = CompanyResponse.builder().id(application.getCompanyId()).build();
        UserResponse candidate = UserResponse.builder().id(application.getCandidateId()).build();
        return ApplicationMapper.toResponse(application,notes,job,company,candidate);
    }
    private Sort buildSort(String sortBy) {
        if("AI_SCORE_DESC".equals(sortBy)){
            return Sort.by(Sort.Order.desc("aiScore").with(Sort.NullHandling.NULLS_LAST));
        }else if("AI_SCORE_ASC".equals(sortBy)){
            return Sort.by(Sort.Order.asc("aiScore").with(Sort.NullHandling.NULLS_LAST));
        }
        return Sort.by(Sort.Direction.DESC, "appliedAt");
    }
    private void assertEmployer(Application application, Long employerId) throws Exception {
        if (!application.getEmployerId().equals(employerId)) {
            throw new Exception("You are not the employer for this application");
        }
    }
    private void assertCandidate(Application application, Long candidateId) throws Exception {
        if (!application.getCandidateId().equals(candidateId)) {
            throw new Exception("You are not the owner of this application");
        }
    }

}
