package com.noir.job.model;

import com.noir.job.domain.AiShortlistStatus;
import com.noir.job.domain.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "applications")
@Builder
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Long candidateId;

    @Column(nullable = false)
    private Long jobId;
    @Column(nullable = false)
    private Long companyId;


    @Column(nullable = false)
    private Long employerId;

    @Column(nullable = false)
    private Long resumeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ApplicationStatus status = ApplicationStatus.PENDING;

    @Column(columnDefinition = "TEXT")
    private String coverLetter;

    private BigDecimal expectedSalary;

    private LocalDate availableFrom;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isRead = false;
    @Column(nullable = false)
    @Builder.Default
    private Boolean isStarred = false;

    @Column
    private Integer aiScore;

    @Enumerated(EnumType.STRING)
    @Column
    @Builder.Default
    private AiShortlistStatus aiShortlistStatus = AiShortlistStatus.NOT_SCREENED;

    private LocalDateTime withdrawnAt;

    @Column(columnDefinition = "TEXT")
    private String withdrawnReason;



    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime appliedAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;




}
