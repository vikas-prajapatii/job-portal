package com.noir.job.mapper;

import com.noir.job.dto.*;
import com.noir.job.dto.response.UserResponse;
import com.noir.job.model.Application;
import com.noir.job.model.ApplicationNote;
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
                                                 JobResponse job,
                                                 CompanyResponse company,
                                                 UserResponse candidate) {
        return toResponse(application, null, job, company, candidate);
    }

    public static ApplicationResponse toResponse(Application application,
                                                 java.util.List<ApplicationNote> notes,
                                                 JobResponse job,
                                                 CompanyResponse company,
                                                 UserResponse candidate) {

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
                .isStarred(application.getIsStarred())
                .notes(notes != null ? notes.stream().map(ApplicationMapper::toNoteResponse).toList() : null)
                .withdrawnAt(application.getWithdrawnAt())
                .withdrawnReason(application.getWithdrawnReason())
                .appliedAt(application.getAppliedAt())
                .updatedAt(application.getUpdatedAt())
                .build();
    }

    public static ApplicationNoteResponse toNoteResponse(ApplicationNote note) {
        return ApplicationNoteResponse.builder()
                .id(note.getId())
                .addedByUserId(note.getAddedByUserId())
                .content(note.getContent())
                .createdAt(note.getCreatedAt())
                .build();
    }
}
