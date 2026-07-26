package com.noir.job.model;

import com.noir.job.domain.SkillCategory;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "job_skills")
public class JobSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;

    @Column(unique = true)
    private String slug;

    private SkillCategory category;

    @Builder.Default
    private Boolean active = true;

    @Column(nullable = false, updatable = true)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;


}
