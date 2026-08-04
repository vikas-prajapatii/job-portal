package com.noir.job.model;

import com.noir.job.domain.JobType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "work_experiences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    @Column(nullable = false, length = 150)
    private String companyName;
    private String companyLogoUrl;

    @Column(nullable = false, length = 150)
    private String jobTitle;
    @Enumerated(EnumType.STRING)
    private JobType employmentType;
    private String location;
    @Column(nullable = false)
    private LocalDate startDate;
    private LocalDate endDate;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isCurrentJob = false;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ElementCollection
    private List<String> technologies = new ArrayList<>();

    @Column(nullable = false)
    private Integer displayOrder = 0;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;



}
