package com.noir.job.model;

import com.noir.job.domain.ResumeTemplate;
import com.noir.job.domain.ResumeVisibility;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "resumes")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Long candidateId;
    @Column(nullable = false, length = 150)
    private String title;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ResumeTemplate template = ResumeTemplate.PROFESSIONAL;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ResumeVisibility visibility = ResumeVisibility.PRIVATE;
    @Column(nullable = false)
    @Builder.Default
    private Boolean isDefault = false;

    @Embedded
    private PersonalInfo personalInfo;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    private Boolean isActive = true;
    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private Integer completionScore = 0;

}
