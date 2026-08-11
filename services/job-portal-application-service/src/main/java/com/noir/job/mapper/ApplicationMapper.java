package com.noir.job.mapper;

import com.noir.job.dto.ApplicationResponse;
import com.noir.job.dto.CompanyResponse;
import com.noir.job.dto.CompanySummaryResponse;
import com.noir.job.dto.JobResponse;
import com.noir.job.dto.response.UserResponse;
import com.noir.job.model.Application;
import com.noir.job.payload.CreateApplicationRequest;

public class ApplicationMapper {
    public static Application toEntity(CreateApplicationRequest req,
                                       Long candidateId,
                                       Long companyId,
                                       Long employerId) {


        return Application.builder()
                .candidateId(candidateId)
                .jobId(req.getJobId())
                .companyId(companyId)
                .employerId(employerId)
                .resumeId(req.getResumeId())
                .coverLetter(req.getCoverLetter())
                .expectedSalary(req.getExpectedSalary())
                .availableFrom(req.getAvailableFrom())
                .build();
    }
    public static ApplicationResponse toResponse(Application application,
//
                                                 JobResponse job,
                                                 CompanyResponse company,
                                                 UserResponse candidate)
                                                 {

        return ApplicationResponse.builder()
                .id(application.getId())
                .candidate(candidate)
                .employerId(application.getEmployerId())
                .job(job)
                .company(company)
                .status(application.getStatus())

                .resumeId(application.getResumeId())
                .coverLetter(application.getCoverLetter())

                .expectedSalary(application.getExpectedSalary())

                .availableFrom(application.getAvailableFrom())
//                .isRead(application.getIsRead())
                .isStarred(application.getIsStarred())
//                .statusHistory(toHistoryResponseList(history))

//                .notes(toNoteResponseList(notes))
                .withdrawnAt(application.getWithdrawnAt())
                .withdrawnReason(application.getWithdrawnReason())
                .appliedAt(application.getAppliedAt())
                .updatedAt(application.getUpdatedAt())
//                .screening(toScreeningResponse(screening))
                .build();
    }
}
