package com.noir.job.model;

import com.noir.job.domain.ExperienceLevel;
import com.noir.job.domain.JobStatus;
import com.noir.job.domain.JobType;
import com.noir.job.domain.SalaryPeriod;
import com.noir.job.domain.WorkMode;
import com.noir.job.model.embeddable.JobLocation;
import com.noir.job.model.embeddable.SalaryRange;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
   @Column(nullable = false)
    private String requirements;
   @Column(nullable = false)
   private Long employerId;
   @Column(nullable = false)
   private String responsibilities;
   private String benefits;
 @Builder.Default
 private Boolean active = true;
    private Long companyId;
    @Column(nullable = false)
    private Long categoryId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "job_skill_ids", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "skill_id")
    private Set<Long> skillIds;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "job_tag_ids", joinColumns = @JoinColumn(name = "job_id"))
    @Column(name = "tag_id")
    private Set<Long> tagIds;
    @Embedded
    private JobLocation location;
    @Embedded
    private SalaryRange salaryRange;
    private String currency;
    @Enumerated(EnumType.STRING)
    private SalaryPeriod salaryPeriod;
    private Boolean salaryNegotiable;
    private Boolean salaryDisclosed;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType jobType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkMode workMode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExperienceLevel experienceLevel;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private JobStatus status = JobStatus.PAUSED;

    @Builder.Default
    private Integer openings = 1;
    private LocalDate applicationDeadline;

    private LocalDate expiresAt;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime updatedAt;

    private LocalDate publishedAt;
    private LocalDate closedAt;


}
